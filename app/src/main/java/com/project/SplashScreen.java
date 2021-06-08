package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.project.bitirmeprojedenemetopluluklar.MainActivity;
import com.project.ogrenciasistanidenemeprojesi.Main2Activity;
import com.project.ogrenciasistanidenemeprojesi.R;

public class SplashScreen extends AppCompatActivity {
ImageButton girisButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        girisButton=findViewById(R.id.girisButton);

        girisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreen.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}