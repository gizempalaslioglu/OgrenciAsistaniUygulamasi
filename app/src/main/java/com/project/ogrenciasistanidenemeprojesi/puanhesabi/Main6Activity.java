package com.project.ogrenciasistanidenemeprojesi.puanhesabi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.project.ogrenciasistanidenemeprojesi.R;

public class Main6Activity extends AppCompatActivity {
    EditText dersAdi,vizeNotu,vizeYuzdesi,finalNotu,finalYuzdesi,projeNotu,projeYuzdesi,toplamNot,harfNot;
    Button tamamButton,silButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        dersAdi=findViewById(R.id.dersAdi);
        vizeNotu=findViewById(R.id.vizePuan);
        vizeYuzdesi=findViewById(R.id.vizeYuzde);
        finalNotu=findViewById(R.id.finalPuan);
        finalYuzdesi=findViewById(R.id.finalYuzde);
        projeNotu=findViewById(R.id.projePuan);
        projeYuzdesi=findViewById(R.id.projeYuzde);
        toplamNot=findViewById(R.id.ortalamaPuan);
        harfNot=findViewById(R.id.harfNotu);

        tamamButton=findViewById(R.id.hesaplaButton);
        silButton=findViewById(R.id.silButton);

        tamamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markscal();
            }
        });

        silButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });


    }

    private void markscal() {
        double vizetotal,finaltotal,projetotal,toplam;

        double vizeYuzdeHesabi=Double.parseDouble(vizeYuzdesi.getText().toString())/100;
        int vizeHesabi=Integer.parseInt(vizeNotu.getText().toString());
        vizetotal= (vizeHesabi*vizeYuzdeHesabi);

        double finalYuzdeHesabi=Double.parseDouble(finalYuzdesi.getText().toString())/100;
        int finalHesabi=Integer.parseInt(finalNotu.getText().toString());
        finaltotal= (finalHesabi*finalYuzdeHesabi);

        double projeYuzdeHesabi=Double.parseDouble(projeYuzdesi.getText().toString())/100;
        int projeHesabi=Integer.parseInt(projeNotu.getText().toString());
        projetotal=(projeHesabi*projeYuzdeHesabi);

        toplam=vizetotal+finaltotal+projetotal;

        toplamNot.setText(String.valueOf(toplam));//ortalama puan

        if (toplam > 91) {
            harfNot.setText("AA-->MÜKEMMEL");
        } else if (toplam > 81) {
            harfNot.setText("BA-->ÇOK İYİ");
        } else if (toplam > 71) {
            harfNot.setText("BB-->İYİ");
        } else if (toplam > 61) {
            harfNot.setText("CB-->ORTA");
        } else if (toplam > 51) {
            harfNot.setText("CC-->ORTA");
        } else if (toplam > 41) {
            harfNot.setText("CD-->GEÇER");
        } else if (toplam > 36) {
            harfNot.setText("DD-->ŞARTLI BAŞARILI");
        }else if (toplam > 31) {
            harfNot.setText("DF-->ŞARTLI BAŞARILI");
        }else if (toplam >= 30) {
            harfNot.setText("FF");
        }
        else {
            harfNot.setText("FAIL");
        }

    }

    public void clear()
    {
        dersAdi.setText("");
        vizeNotu.setText("");
        vizeYuzdesi.setText("");
        finalNotu.setText("");
        finalYuzdesi.setText("");
        projeNotu.setText("");
        projeYuzdesi.setText("");
        toplamNot.setText("");
        harfNot.setText("");
        dersAdi.requestFocus();
    }

}