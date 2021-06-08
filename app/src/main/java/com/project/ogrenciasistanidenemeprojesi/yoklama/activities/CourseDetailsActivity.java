package com.project.ogrenciasistanidenemeprojesi.yoklama.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.adapters.CourseAttendanceAdapter;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.AttendanceListModel;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Course;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "CourseDetailsActivity";

    private Course selectedCourse;
    private DatabaseReference courseReference;

    private RecyclerView recyclerView_attendance;
    private CourseAttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        init();

        //Spinner'a 14 haftayı isim olarak ekliyoruz
        Spinner spinner = findViewById(R.id.spinner_weeks);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weeks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.tv_no_attendance_records).setVisibility(View.VISIBLE);

        spinner.setOnItemSelectedListener(this);
    }

    //Burada spinner üzerindeki seçilen haftanın pozisyonunu da göndererek yoklamayı göstermek için setAttendence() methodu çalışıyor
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int weekPos = position + 1;

        recyclerView_attendance.setVisibility(View.VISIBLE);

        setAttendance(weekPos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setAttendance(int weekPos) {
        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Course currentCourse = dataSnapshot.getValue(Course.class);

                        //Seçilen dersi eşleştirmek için Courses referansı üzerinde döngü yapıyoruz, eğer seçilen ders döngüdeki şu anlık derse eşitse bu if bloğu çalışıyor
                        if (currentCourse.getCourseId().equals(selectedCourse.getCourseId())) {

                            // Daha sonra getAttendance() != null bloğuyla dersin yoklama verisi boş değilse bu blok çalışıyor.
                            if (currentCourse.getAttendance() != null) {

                                /*Yoklama boş değilse bu HashMap'e dersin yoklama listesini atıyoruz. Hashmap<> içindeki 1. String hafta adını anahtar olarak temsil ediyor,
                                 2. List<String> de o haftadaki öğrenci listesini temsil ediyor. Yani HashMap<Hafta Sayısı, Öğrenci Id Listesi>. */
                                HashMap<String, List<String>> attendanceWeeks = currentCourse.getAttendance();

                                //attendanceWeeks HashMapinin listeden seçilen pozisyondaki haftası boş değilse yani herhangi bir öğrenci derse katılmışsa  bu if bloğu çalışıyor
                                if (attendanceWeeks.get("hafta" + weekPos) != null) {

                                    //attendanceList adında öğrenci id'lerinin olduğu liste oluşturuyoruz ve HashMap'teki o haftaya ait listeye eşleştiriyoruz
                                    List<String> attendanceList = attendanceWeeks.get("hafta" + weekPos);

                                    //Daha sonra Student referansı alıp attendanceList içindeki öğrencileri listeliyoruz
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            List<AttendanceListModel> attendanceWeeks = new ArrayList<>();
                                            for (DataSnapshot studentSnapshot : snapshot.getChildren()) {
                                                Student student = studentSnapshot.getValue(Student.class);

                                                //For döngüsü içinde Student referansındaki öğrencileri üzerinde geziniyoruz, eğer attendanceList içinde referanstaki öğrenci mevcutsa listeye ekliyoruz
                                                if (attendanceList.contains(student.getId())) {
                                                    AttendanceListModel attendance = new AttendanceListModel(
                                                            student,
                                                            weekPos + ". Hafta",
                                                            currentCourse.getCourseName(),
                                                            "Başarılı"
                                                    );

                                                    attendanceWeeks.add(attendance);
                                                }
                                            }

                                            //En son attendanceWeeks listesini recyclerView'e atıyoruz ve ekranda listeleniyor
                                            adapter = new CourseAttendanceAdapter(attendanceWeeks);
                                            recyclerView_attendance.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();

                                            findViewById(R.id.tv_no_attendance_records).setVisibility(View.GONE);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                                //Eğer o hafta boşsa ekrandaki listeyi görünmez yapıyoruz ve "Bu haftada kayıt yok" yazısını görünür yapıyoruz
                                else {
                                    recyclerView_attendance.setVisibility(View.GONE);
                                    findViewById(R.id.tv_no_attendance_records).setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }


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
        //Intent getExtra kullanarak bir önceki ekrandan tıkladığımız Course objesini selectedCourse'a atıyoruz
        Intent intent = getIntent();
        selectedCourse = (Course) intent.getSerializableExtra("Course");

        courseReference = FirebaseDatabase.getInstance().getReference("Courses");

        recyclerView_attendance = findViewById(R.id.recyclerview_attendance);
        recyclerView_attendance.setHasFixedSize(true);

        recyclerView_attendance.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setTitle("Yoklama");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}