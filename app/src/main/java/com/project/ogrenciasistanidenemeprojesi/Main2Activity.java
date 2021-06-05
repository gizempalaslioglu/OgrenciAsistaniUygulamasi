package com.project.ogrenciasistanidenemeprojesi;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.project.bitirmeprojedenemetopluluklar.MainActivity;
import com.project.ogrenciasistanidenemeprojesi.dersprogrami.Main3Activity;
import com.project.ogrenciasistanidenemeprojesi.notlar.Main4Activity;
import com.project.ogrenciasistanidenemeprojesi.zamanlayici.Main5Activity;

//anasayfa
public class Main2Activity extends AppCompatActivity {
    public ImageButton gorevButton,zamanlayiciButton,dersProgramiButton;
    public ImageButton mailButton,yoklamaButton,duyurularButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        gorevButton = (ImageButton) findViewById(R.id.gorevButton);
        mailButton = (ImageButton) findViewById(R.id.mailButton);
        yoklamaButton=(ImageButton) findViewById(R.id.yoklamaButton);
        dersProgramiButton=(ImageButton)findViewById(R.id.dersProgramiButton);
        duyurularButton=(ImageButton)findViewById(R.id.duyurularButton); //topluluklar
        gorevButton=(ImageButton)findViewById(R.id.gorevButton);
        zamanlayiciButton=(ImageButton)findViewById(R.id.zamanlayiciButton);
        mailButton=(ImageButton)findViewById(R.id.mailButton);


        tiklanmalar();

    }


    public void tiklanmalar() {
        duyurularButton.setOnClickListener(new View.OnClickListener() { //topluluklar tıklanması
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dersProgramiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
            }
        });

        gorevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
                startActivity(intent);
            }
        });

        zamanlayiciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Main5Activity.class);
                startActivity(intent);
            }
        });


    }
}

