package no.nanchinorth.christmastreelighting;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imgChristmasTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgChristmasTree = findViewById(R.id.imageView);

        Animation fadeAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tween);

        imgChristmasTree.startAnimation(fadeAnimation);


    }
}
