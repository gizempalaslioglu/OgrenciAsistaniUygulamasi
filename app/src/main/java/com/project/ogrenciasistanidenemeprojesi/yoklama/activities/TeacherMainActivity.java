package com.project.ogrenciasistanidenemeprojesi.yoklama.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.adapters.CourseTeacherAdapter;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Course;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    //Bu CourseTeacherAdapter class'ında ders detaylarına gitmek için ve dersi aktif hale getirmek için methodlar var
    private CourseTeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        init();
        setTeacherName();

        buildRecyclerView();

        // Sağ alttaki butonla AddCourseActivity'i ders eklemek için acıyoruz
        FloatingActionButton btn_addCourse = findViewById(R.id.btn_add_course);
        btn_addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this, AddCourseActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void buildRecyclerView() {
        //Eklenen kursları listelemek için Courses referansına erişiliyor
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Course> courseList = new ArrayList<>();

                //Eğer Courses referansı boş değilse ArrayListe tüm course objeleri sırayla listeye ekleniyor daha sonra recyclerview'de gösteriliyor
                if(snapshot.getChildrenCount() != 0) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Course course = dataSnapshot.getValue(Course.class);

                        courseList.add(course);
                    }
                }


                adapter = new CourseTeacherAdapter(courseList, TeacherMainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TeacherMainActivity.this, StartingActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setTeacherName() {
        //Öğretmen adını yukarıdaki bara yazıyoruz
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teachers").child(currentUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher = snapshot.getValue(Teacher.class);

                getSupportActionBar().setTitle(teacher.getName() + " " + teacher.getSurname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerview_teacher);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}