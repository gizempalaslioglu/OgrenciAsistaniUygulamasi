package com.project.ogrenciasistanidenemeprojesi.yoklama.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Course;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity {

    private EditText et_courseName, et_courseId;
    private FloatingActionButton btn_save;

    private FirebaseUser currentUser;
    private DatabaseReference teacherReference;

    @Override
    public void onBackPressed() { //geri tuşuna basıldığında
        Intent intent = new Intent(this, TeacherMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        init();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = et_courseName.getText().toString();
                String courseId = et_courseId.getText().toString();

                //Eğer ders adı ve idsi boşsa kullanıcıya uyarı veriliyor, değilse saveCourse methoduyla ders database'e kaydediliyor.
                if (TextUtils.isEmpty(courseName) || TextUtils.isEmpty(courseId)) {
                    Toast.makeText(AddCourseActivity.this, "Tüm alanlar doldurulmalı.", Toast.LENGTH_SHORT).show();
                } else {
                    saveCourse(courseName, courseId);
                }
            }
        });
    }

    private void saveCourse(String courseName, String courseId) {
        //Dersi database'e kaydetmek için Courses referansını alıyoruz
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Eğer courses referansı boş değilse idList listesine tüm ders Id'lerini ekliyoruz, eğer aynı id ile bir ders varsa daha sonra öğretmene uyarı verilecek
                if (snapshot.getChildrenCount() != 0) {
                    List<String> idList = new ArrayList<>();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Course course = dataSnapshot.getValue(Course.class);

                        idList.add(course.getCourseId()); //tüm ders id lerini ekledik
                    }

                    //Eğer idList, öğretmenin girdiği id course referansındaki kursların herhangi bir id'siyle aynı değilse
                    if (!idList.contains(courseId)) {
                        teacherReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Teacher teacher = snapshot.getValue(Teacher.class);

                                List<String> attendanceList = new ArrayList<>();
                                attendanceList.add("Test");

                                HashMap<String, List<String>> attendanceMap = new HashMap<>();

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("courseName", courseName);
                                hashMap.put("courseId", courseId);
                                hashMap.put("courseTeacher", teacher.getName() + " " + teacher.getSurname());
                                hashMap.put("registeredStudents", 0);
                                hashMap.put("active", false);
                                hashMap.put("attendance", attendanceMap);
                                hashMap.put("week", 0);
                                //Tüm bilgileri hashMap'te key ile saklayıp daha sonra Course referansına reference.push().setValue methoduyla database'e ekliyoruz
                                //buradaki push methodu yeni bir id'li liste oluşturmasını sağlıyor, push kullanılmazsa önceki dersler silinir bu yeni değerler set edilir.
                                reference.push().setValue(hashMap);

                                Intent intent = new Intent(AddCourseActivity.this, TeacherMainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                    //Eğer öğretmenin girdiği id , idList'te varsa kullanıcıya uyarı
                    else {
                        Toast.makeText(AddCourseActivity.this, "Bu ID ile bir ders bulunuyor.", Toast.LENGTH_SHORT).show();
                    }
                }
                //Eğer Course referansı boşsa (yukarıdaki "snapshot.getChildrenCount() != 0" if bloğuyla kontrol ettik) zaten hiç ders olmadığı için id'ler çakışamaz direkt ders database'e kaydolur
                else {
                    teacherReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Teacher teacher = snapshot.getValue(Teacher.class);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("courseName", courseName);
                            hashMap.put("courseId", courseId);
                            hashMap.put("courseTeacher", teacher.getName() + " " + teacher.getSurname());
                            hashMap.put("registeredStudents", 0);
                            hashMap.put("active", false);
                            hashMap.put("week", 0);
                            reference.push().setValue(hashMap);

                            Intent intent = new Intent(AddCourseActivity.this, TeacherMainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void init() {
        getSupportActionBar().setTitle("Ders Ekle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        et_courseName = findViewById(R.id.et_course_name);
        et_courseId = findViewById(R.id.et_course_id);

        btn_save = findViewById(R.id.btn_save_course);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        teacherReference = FirebaseDatabase.getInstance().getReference("Teachers").child(currentUser.getUid());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //çıkış yap kısmı
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}