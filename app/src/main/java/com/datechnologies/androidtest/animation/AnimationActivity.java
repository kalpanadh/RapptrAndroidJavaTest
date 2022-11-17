package com.datechnologies.androidtest.animation;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 */

public class AnimationActivity extends AppCompatActivity {

    private ImageView imageLogo;
    RelativeLayout animationView;
    float xDown = 0, yDown = 0;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.animation_button);
        imageLogo = findViewById(R.id.img_logo);
        animationView = findViewById(R.id.animationView);

        final int windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        final int windowHeight = getWindowManager().getDefaultDisplay().getHeight();


        imageLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float movedX, movedY;
                        movedX = event.getX();
                        movedY = event.getY();
                        if (movedX > windowWidth) {
                            movedX = windowWidth;
                        }
                        if (movedY > windowHeight) {
                            movedY = windowHeight;
                        }
                        float distanceX = movedX - xDown;
                        float distanceY = movedY - yDown;
                        imageLogo.setX(imageLogo.getX() + distanceX);
                        imageLogo.setY(imageLogo.getY() + distanceY);
                        break;
                }
                return true;
            }
        });

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void animation(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setBackgroundColor(getResources().getColor(R.color.button_blue));
        imageLogo.startAnimation(alphaAnimation);


    }


}
