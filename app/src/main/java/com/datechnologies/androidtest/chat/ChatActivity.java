package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatApiInterface;
import com.datechnologies.androidtest.api.ChatLogMessage;
import com.datechnologies.androidtest.api.ChatLogMessageModel;
import com.datechnologies.androidtest.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Screen that displays a list of chats from a chat log.
 */
public class ChatActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    public List<ChatLogMessage> chatMessagesList;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.chat_button);


        chatAdapter = new ChatAdapter();

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));
        chatMessagesList = new ArrayList<>();
        getChatData();


        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
    }

    private void getChatData() {

        ChatApiInterface chatApiInterface = RetrofitClient.getRetrofitInstance().create(ChatApiInterface.class);
        Call<ChatLogMessageModel> call = chatApiInterface.getChatData();
        call.enqueue(new Callback<ChatLogMessageModel>() {
            @Override
            public void onResponse(Call<ChatLogMessageModel> call, Response<ChatLogMessageModel> response) {
                Log.i("onResponse", "onResponse");
                chatMessagesList = response.body().getData();
                chatAdapter.setChatLogMessageList(chatMessagesList);
            }

            @Override
            public void onFailure(Call<ChatLogMessageModel> call, Throwable throwable) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(ChatActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
