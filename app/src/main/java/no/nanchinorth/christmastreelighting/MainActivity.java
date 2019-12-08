package no.nanchinorth.christmastreelighting;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwitchCompat.OnCheckedChangeListener{

    private ImageView imgChristmasTree;
    private TextView txtCountdown;
    private SwitchCompat switchCompat;
    private Button btnCountdown;

    private AnimatorSet aSetFadeIn;
    private AnimatorSet aSetFadeOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgChristmasTree = findViewById(R.id.imgChristmasTree);
        txtCountdown = findViewById(R.id.txtCountdown);
        switchCompat = findViewById(R.id.swLightSwitch);
        btnCountdown = findViewById(R.id.btnCountDown);

        btnCountdown.setOnClickListener(MainActivity.this);
        switchCompat.setOnCheckedChangeListener(MainActivity.this);

        aSetFadeIn = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.tween_in);
        aSetFadeIn.setTarget(imgChristmasTree);

        aSetFadeOut = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.tween_out);
        aSetFadeOut.setTarget(imgChristmasTree);

        imgChristmasTree.setAlpha(0f);




    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnCountdown.getId()){

            if(!switchCompat.isChecked()) {

                new CountDownTimer(4000, 1000) {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(millisUntilFinished % 4000 != 0) {
                            txtCountdown.setText(String.format("%d", millisUntilFinished / 1000));
                        }

                    }

                    @Override
                    public void onFinish() {

                        switchCompat.setChecked(true);
                        txtCountdown.setText("");
                        lightSwitch();
                    }
                }.start();

            }

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == switchCompat.getId()){
            lightSwitch();
        }
    }

    public void lightSwitch(){
        if(aSetFadeOut.isStarted()){
            aSetFadeOut.end();
        }

        if(switchCompat.isChecked()){
            aSetFadeIn.start();
            btnCountdown.animate().alpha(0f).setDuration(1000).start();
            btnCountdown.setEnabled(false);

        } else {
            aSetFadeIn.end();
            aSetFadeOut.start();
            btnCountdown.animate().alpha(1f).setDuration(1000).start();
            btnCountdown.setEnabled(true);

        }
    }
}
