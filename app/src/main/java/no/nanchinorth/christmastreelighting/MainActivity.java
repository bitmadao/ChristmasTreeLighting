package no.nanchinorth.christmastreelighting;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
        txtCountdown = findViewById(R.id.txtSwitchLabel);
        switchCompat = findViewById(R.id.switch1);
        btnCountdown = findViewById(R.id.btnCountDown);
        btnCountdown.setOnClickListener(MainActivity.this);

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
                if(aSetFadeOut.isStarted()){
                    aSetFadeOut.end();
                }

                new CountDownTimer(4000, 1000) {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        txtCountdown.setText(String.format("%d",millisUntilFinished/1000));

                    }

                    @Override
                    public void onFinish() {

                        switchCompat.setChecked(true);
                        txtCountdown.setText("");
                        aSetFadeIn.start();
                    }
                }.start();


            } else {
                switchCompat.setChecked(false);
                aSetFadeIn.end();
                aSetFadeOut.start();
            }



        }
    }
}
