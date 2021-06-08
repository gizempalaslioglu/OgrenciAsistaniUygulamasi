package com.project.ogrenciasistanidenemeprojesi.yoklama.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Student;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Teacher;

public class StartingActivity extends AppCompatActivity {

    private Button btn_studentLogin;
    private Button btn_teacherLogin;

    private ProgressBar progressBar;
    private TextView tv_login;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        init();

        if(firebaseUser != null) {
            //Eğer firebaseUser boş değilse kullanıcı giriş yapmış demektir, bu yüzden giriş butonlarını görünmez yapıyoruz ve login() methodu çalışıyor.
            btn_studentLogin.setVisibility(View.GONE);
            btn_teacherLogin.setVisibility(View.GONE);

            progressBar.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.VISIBLE);

            login();
        }
    }

    /* Firebase user (giriş yapmış kullanıcı)'nın idsiyle hem student hem de teacher referansındaki kullanıcıların id'sini kontrol ediyoruz,
     hangisi eşleşiyorsa direkt o kullanıcının ana sayfasına yönlendiriliyor olarak */
    private void login() {
        DatabaseReference studentReference = FirebaseDatabase.getInstance().getReference("Students");
        studentReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Student student = dataSnapshot.getValue(Student.class);

                    //id kontrolü
                    if(firebaseUser.getUid().equals(student.getId())) {
                        Intent intent = new Intent(StartingActivity.this, StudentsMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference teacherReference = FirebaseDatabase.getInstance().getReference("Teachers");
        teacherReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Teacher teacher = dataSnapshot.getValue(Teacher.class);

                    //id kontrolü
                    if(firebaseUser.getUid().equals(teacher.getId())) {
                        Intent intent = new Intent(StartingActivity.this, TeacherMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init() {
        progressBar = findViewById(R.id.progress_login);
        tv_login = findViewById(R.id.tv_login);

        btn_studentLogin = findViewById(R.id.btn_student_login);
        btn_studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingActivity.this, StudentLoginActivity.class);
                startActivity(intent);
            }
        });

        btn_teacherLogin = findViewById(R.id.btn_teacher_login);
        btn_teacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingActivity.this, TeacherLoginActivity.class);
                startActivity(intent);
            }
        });

        //Kullanıcı giriş yaptığında giriş yapmış kullanıcı kaydoluyor daha sonra kullanıcı giriş yapmış mı yapmamış mı kontrol edilecek
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
}