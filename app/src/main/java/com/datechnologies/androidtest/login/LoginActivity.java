package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.LoginApiInterface;
import com.datechnologies.androidtest.api.LoginModel;
import com.datechnologies.androidtest.api.RetrofitClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);

    }

    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    LinearLayout loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.login_button);

        edtEmail = findViewById(R.id.edtEmailField);
        edtPassword = findViewById(R.id.edtPwdField);
        btnLogin = findViewById(R.id.btnLoginField);
        loginLayout = findViewById(R.id.login_layout);


        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.


    }

    private boolean validateEmail() {
        String emailText = edtEmail.getText().toString();

        if (!emailText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return true;
        } else {
            Toast.makeText(this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private boolean validatePassword() {
        String passwordText = edtPassword.getText().toString();
        if (passwordText.isEmpty()) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();

    }

    public void hideKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

    }

    public void Login(View view) {
        if (validateEmail() && validatePassword()) {
            hideKeyBoard(view);
            LoginServiceCall();

        }

    }

    private void LoginServiceCall() {
        LoginApiInterface loginApiInterface = RetrofitClient.getRetrofitInstance().create(LoginApiInterface.class);
        Call<LoginModel> call = loginApiInterface.loginVerify(edtEmail.getText().toString(), edtPassword.getText().toString());

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                long responseTime = response.raw().receivedResponseAtMillis() - response.raw().sentRequestAtMillis();
                if (response.isSuccessful()) {
                    LoginModel res = response.body();
                    showInfoDialog(res.getCode(), res.getMessage(), responseTime);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        showInfoDialog(jObjError.getString("code"), jObjError.getString("message"), responseTime);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void showInfoDialog(final String code, String message, long responseTime) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(code);
        builder.setMessage(message + "\n" + responseTime + " ms \n");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (code.equalsIgnoreCase("SUCCESS")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();

    }

}
