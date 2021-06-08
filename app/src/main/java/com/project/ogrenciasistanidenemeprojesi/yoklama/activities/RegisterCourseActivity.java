package com.project.ogrenciasistanidenemeprojesi.yoklama.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.adapters.CourseRegisterAdapter;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Course;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Student;

import java.util.ArrayList;
import java.util.List;

public class RegisterCourseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //CourseRegisterAdapter classında kursa kayıt olma işlemi yapılıyor
    private CourseRegisterAdapter adapter;
    private FirebaseUser currentUser;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterCourseActivity.this, StudentsMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_course);
        init();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students").child(currentUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);

                if(student.getRegisteredCourses() != null) {
                    setCourseList(student);
                    findViewById(R.id.tv_you_not_registered).setVisibility(View.GONE);
                    findViewById(R.id.progress_bar_loading_courses).setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setCourseList(Student student) {
        //courses referansıyla dersler listeye eklenip ekranda gözüküyor.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Course> courseList = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Course course = dataSnapshot.getValue(Course.class);

                    if(!student.getRegisteredCourses().contains(dataSnapshot.getKey())) {
                        courseList.add(course);
                    }
                }

                adapter = new CourseRegisterAdapter(courseList);
                recyclerView.setAdapter(adapter);
                findViewById(R.id.tv_you_not_registered).setVisibility(View.GONE);
                findViewById(R.id.progress_bar_loading_courses).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void init() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recyclerview_courses_to_register);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setTitle("Derse Kayıt Ol");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}