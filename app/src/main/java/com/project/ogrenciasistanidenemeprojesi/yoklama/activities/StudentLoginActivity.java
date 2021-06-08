package com.project.ogrenciasistanidenemeprojesi.yoklama.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.ogrenciasistanidenemeprojesi.R;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText et_mail, et_password;
    private TextView tv_register;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        init();

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = et_mail.getText().toString();
                final String password = et_password.getText().toString();

                /* Text alanlarına girdiğin bilgileri kontrol ediyor, herhangi biri boşsa kullanıcıya uyarı veriliyor */
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(StudentLoginActivity.this, "E-posta ya da şifre boş olamaz.", Toast.LENGTH_SHORT).show();
                }
                else {
                    /* Firebase Authentication'un "signInWithEmailAndPassword" methoduyla email ve password kontrol ediliyor ve eğer doğruysa öğrenci ana sayfasına atılıyor */
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(StudentLoginActivity.this, "Başarıyla giriş yapıldı.", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(StudentLoginActivity.this, StudentsMainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(StudentLoginActivity.this, "Yanlış e-posta veya şifre.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });
    }

    private void init() {
        et_mail = findViewById(R.id.et_mail);
        et_password = findViewById(R.id.et_password);

        tv_register = findViewById(R.id.tv_student_register);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLoginActivity.this, StudentRegisterActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
    }
}