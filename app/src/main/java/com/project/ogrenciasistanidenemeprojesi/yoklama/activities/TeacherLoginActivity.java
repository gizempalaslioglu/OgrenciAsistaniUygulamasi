package com.project.ogrenciasistanidenemeprojesi.yoklama.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Teacher;

public class TeacherLoginActivity extends AppCompatActivity {

    private EditText et_mail, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        init();

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_mail.getText().toString();
                String password = et_password.getText().toString();

                /* Text alanları kontrol ediliyor, boşsa kullanıcıya hata veriliyor. */
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(TeacherLoginActivity.this, "Tüm bilgiler doldurulmalı", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Öğretmen bilgilerini kontrol etmek için "Teachers" referansıyla öğretmen tablosuna erişiliyor
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teachers");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Teacher teacher = dataSnapshot.getValue(Teacher.class);

                                int count = 0;

                                //Eğer öğretmen e posta ve şifresi eşleşiyorsa, "signInWithEmailAndPassword" methoduyla giriş yapılıyor
                                if (email.equals(teacher.getMail()) && password.equals(teacher.getPassword())) {
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    auth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(TeacherLoginActivity.this, "Başarıyla giriş yapıldı.", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(TeacherLoginActivity.this, TeacherMainActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(TeacherLoginActivity.this, "Girdiğiniz bilgiler yanlış.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                                //Değilse kullanıcıya hata veriliyor
                                else if(!email.equals(teacher.getMail()) || !password.equals(teacher.getPassword()) ) {

                                    if(count != 1) {
                                        Toast.makeText(TeacherLoginActivity.this, "Girdiğiniz bilgiler yanlış", Toast.LENGTH_SHORT).show();
                                        count = 1;
                                    }
                                }


                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }

    private void init() {
        et_mail = findViewById(R.id.et_mail);
        et_password = findViewById(R.id.et_password);
    }
}