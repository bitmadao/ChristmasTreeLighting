package no.nanchinorth.christmastreelighting;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    private String BOOLEAN_KEY = "BOOLEAN";
    private boolean mOutStateBoolean;

    private ImageView mImgChristmasTree;
    private TextView mTxtCountdown;
    private SwitchCompat mSwitchCompat;
    private Button mBtnCountdown;

    private AnimatorSet mASetFadeIn;
    private AnimatorSet mASetFadeOut;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        if(savedInstanceState != null){
            mOutStateBoolean = savedInstanceState.getBoolean(BOOLEAN_KEY);

        } else {
            mOutStateBoolean = false ;
        }

        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(FullscreenActivity.this);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

        mImgChristmasTree = findViewById(R.id.imgChristmasTree);
        mTxtCountdown = findViewById(R.id.txtCountdown);
        mSwitchCompat = findViewById(R.id.swLightSwitch);
        mBtnCountdown = findViewById(R.id.btnCountDown);

        mASetFadeIn = (AnimatorSet) AnimatorInflater.loadAnimator(FullscreenActivity.this, R.animator.tween_in);
        mASetFadeIn.setTarget(mImgChristmasTree);

        mASetFadeOut = (AnimatorSet) AnimatorInflater.loadAnimator(FullscreenActivity.this, R.animator.tween_out);
        mASetFadeOut.setTarget(mImgChristmasTree);

        mImgChristmasTree.setAlpha(0f);

        mBtnCountdown.setOnClickListener(FullscreenActivity.this);
        mSwitchCompat.setOnCheckedChangeListener(FullscreenActivity.this);

        mSwitchCompat.setChecked(mOutStateBoolean);

        if(mSwitchCompat.isChecked()){
            lightSwitch();
        }




    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(BOOLEAN_KEY, mSwitchCompat.isChecked());
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnCountDown:
                lightingCountdown();
                break;

            case R.id.fullscreen_content:
                toggle();
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == mSwitchCompat.getId()){
            lightSwitch();
        }
    }

    public void lightSwitch(){
        if(mASetFadeOut.isStarted()){
            mASetFadeOut.end();
        }

        if(mSwitchCompat.isChecked()){
            mASetFadeIn.start();
            mBtnCountdown.animate().alpha(0f).setDuration(1000).start();
            mBtnCountdown.setEnabled(false);

        } else {
            mASetFadeIn.end();
            mASetFadeOut.start();
            mBtnCountdown.animate().alpha(1f).setDuration(1000).start();
            mBtnCountdown.setEnabled(true);

        }
    }

    public void lightingCountdown(){
        if(!mSwitchCompat.isChecked()) {

            new CountDownTimer(4000, 1000) {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    if(millisUntilFinished % 4000 != 0) {
                        mTxtCountdown.setText(String.format("%d", millisUntilFinished / 1000));
                    }

                }

                @Override
                public void onFinish() {

                    mSwitchCompat.setChecked(true);
                    mTxtCountdown.setText("");
                    lightSwitch();
                }
            }.start();

        }
    }
}
