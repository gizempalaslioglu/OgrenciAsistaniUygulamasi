package com.project.ogrenciasistanidenemeprojesi.yoklama.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.adapters.RegisteredCoursesAdapter;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Course;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Student;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentsMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    //RegisteredCoursesAdapter classı içinde yoklama işlemi yapılıyor
    private RegisteredCoursesAdapter adapter;

    private TextView tv_studentNameToolbar;
    private CircleImageView img_studentImageToolbar;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_main);
        init();

        Toolbar toolbar = findViewById(R.id.toolbar_student);
        setSupportActionBar(toolbar);

        //Şimdiki kullanıcının referansını kayıtlı dersleri listelemek için alıyoruz
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students").child(currentUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);

                tv_studentNameToolbar.setText(student.getName() + " " + student.getSurname() + "\n" + student.getNumber());

                //Öğrecinin kayıtlı derslerini alıyoruz
                List<String> registeredCourses = student.getRegisteredCourses();

                //Daha sonra course referansını alıp yukarıdaki kayıtlı dersler listesine ekliyoruz
                DatabaseReference courseReference = FirebaseDatabase.getInstance().getReference("Courses");
                courseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Course> courses = new ArrayList<>();

                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Course course = dataSnapshot.getValue(Course.class);

                            for(String registeredCourse : registeredCourses) {
                                if(registeredCourse.equals(dataSnapshot.getKey())) {
                                    courses.add(course);
                                }
                            }
                        }

                        adapter = new RegisteredCoursesAdapter(courses, StudentsMainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                Glide.with(StudentsMainActivity.this).load(student.getImageURL()).into(img_studentImageToolbar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void init() {
        recyclerView = findViewById(R.id.recyclerview_registered_courses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        tv_studentNameToolbar = findViewById(R.id.tv_student_name_toolbar);
        img_studentImageToolbar = findViewById(R.id.img_toolbar_student_picture);

        FloatingActionButton floatingActionButton = findViewById(R.id.btn_register_course);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sağ alttaki + butonuyla derse kayıt olmak için RegisterCourseActivity açılıyor
                Intent intent = new Intent(StudentsMainActivity.this, RegisterCourseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //sol alttaki çıkış butonuyla çıkış yapılıyor
        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentsMainActivity.this, StartingActivity.class));
                finish();
            }
        });
    }
}