package no.nanchinorth.christmastreelighting;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private Animation fadeAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgChristmasTree = findViewById(R.id.imgChristmasTree);
        txtCountdown = findViewById(R.id.txtSwitchLabel);
        switchCompat = findViewById(R.id.switch1);
        btnCountdown = findViewById(R.id.btnCountDown);
        btnCountdown.setOnClickListener(MainActivity.this);

        imgChristmasTree.setVisibility(View.INVISIBLE);
        txtCountdown.setVisibility(View.INVISIBLE);
        fadeAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tween);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnCountdown.getId()){

            if(!switchCompat.isChecked()) {
                switchCompat.setChecked(true);
                imgChristmasTree.startAnimation(fadeAnimation);
                imgChristmasTree.setVisibility(View.VISIBLE);
            } else {
                switchCompat.setChecked(false);
                imgChristmasTree.clearAnimation();
                imgChristmasTree.setVisibility(View.INVISIBLE);
            }



        }
    }
}
