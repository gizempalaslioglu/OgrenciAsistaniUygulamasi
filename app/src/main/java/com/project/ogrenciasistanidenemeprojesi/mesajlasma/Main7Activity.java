package com.project.ogrenciasistanidenemeprojesi.mesajlasma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.project.bitirmeprojedenemetopluluklar.MainActivity;
import com.project.ogrenciasistanidenemeprojesi.R;

public class Main7Activity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtEmail;
    private Button btnSubmit;
    private TextView txtLoginInfo;

    private boolean isSigningUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);

        btnSubmit = findViewById(R.id.btnSubmit);

        txtLoginInfo = findViewById(R.id.txtLoginInfo);

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){ //kullanıcı çoktan giriş yapmışsa da Friends Activity e gitsin
            startActivity(new Intent(Main7Activity.this,FriendsActivity.class));
            finish();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {//kayıt ol butonuna tıklandığında
            @Override
            public void onClick(View view) {

                if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()){
                    if (isSigningUp && edtUsername.getText().toString().isEmpty()){
                        Toast.makeText(Main7Activity.this, "Geçersiz giriş", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (isSigningUp){
                    handleSignUp();
                }else {
                    handleLogin();
                }
            }
        });

        txtLoginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSigningUp){ //kayıt olmuşsa(giriş yapacak)
                    isSigningUp = false;
                    edtUsername.setVisibility(View.GONE);//kullanıcı adı edittext görünürlüğü gitsin
                    btnSubmit.setText("Giriş Yap");
                    txtLoginInfo.setText("Hesabınız yok mu?Kayıt olun");
                }else {
                    isSigningUp = true;
                    edtUsername.setVisibility(View.VISIBLE);
                    btnSubmit.setText("Kayıt ol");
                    txtLoginInfo.setText("Hesap oluşturdunuz mu?Giriş yapın");
                }
            }
        });


    }
    //kayıt ve giriş metotları
    private void handleSignUp(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //database e erişiyoruz
                    FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User(edtUsername.getText().toString(),edtEmail.getText().toString(),""));
                    startActivity(new Intent(Main7Activity.this,FriendsActivity.class));
                    Toast.makeText(Main7Activity.this, "Başarıyla kayıt olundu", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Main7Activity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleLogin(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){//giriş yapılınca Friends Activity'e gitsin
                    startActivity(new Intent(Main7Activity.this,FriendsActivity.class));
                    Toast.makeText(Main7Activity.this, "Başarıyla giriş yapıldı", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Main7Activity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}