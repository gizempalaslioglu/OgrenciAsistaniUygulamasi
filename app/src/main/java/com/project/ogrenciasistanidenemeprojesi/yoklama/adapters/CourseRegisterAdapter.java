package com.project.ogrenciasistanidenemeprojesi.yoklama.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Course;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Student;

import java.util.HashMap;
import java.util.List;

public class CourseRegisterAdapter extends RecyclerView.Adapter<CourseRegisterAdapter.CourseViewHolder> {

    private static final String TAG = "CourseRegisterAdapter";

    private List<Course> courses;

    public CourseRegisterAdapter(List<Course> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_course_item_register, parent, false);

        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentCourse = courses.get(position);

        holder.tv_course_name.setText(currentCourse.getCourseName());
        holder.tv_course_teacher.setText(currentCourse.getCourseTeacher());

        //Kayıt ol butonuna basınca register() methodu çalışıyor burada dersi kayıt edicez
        holder.button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(currentCourse, holder, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return courses.size();
    }


    private void register(Course currentCourse, CourseViewHolder holder, int position) {
        //Courses referansına erişiyoruz
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Course course = dataSnapshot.getValue(Course.class);

                    //Course referansı üzerinde geziniyoruz ve eğer tıkladığımız ders referanstaki o anki Courses referansındaki kayıtlı öğrenci sayısını artırıyoruz dersi kaydediyoruz
                    if(currentCourse.getCourseId().equals(course.getCourseId())) {
                        int registeredStudents = course.getRegisteredStudents() + 1;

                        //Registered students
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("registeredStudents", registeredStudents);
                        reference.child(dataSnapshot.getKey()).updateChildren(hashMap);
                        //Kayıtlı kullanıcı sayısını güncelliyoruz

                        //Ders kaydolduğu için ekrandaki listeden dersi kaldırıyoruz
                        courses.remove(position);
                        notifyDataSetChanged();

                        //Bu methodla öğrencinin bilgilerine dersin Id'sini kaydediyoruz
                        addRegisteredCourse(dataSnapshot);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addRegisteredCourse(DataSnapshot dataSnapshot) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //Şu anki kullanıcının referansına erişiyoruz
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students").child(currentUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);

                //Öğrencinin kayıtlı derslerini alıyoruz ve şu anki dersi de o listeye ekleyip güncelliyoruz
                List<String> registeredCourses = student.getRegisteredCourses();
                registeredCourses.add(dataSnapshot.getKey());

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("registeredCourses", registeredCourses);
                reference.updateChildren(hashMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_course_name;
        private TextView tv_course_teacher;

        private Button button_register;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_course_name = itemView.findViewById(R.id.tv_course_name_register);
            tv_course_teacher = itemView.findViewById(R.id.tv_course_teacher_register);
            button_register = itemView.findViewById(R.id.button_register);
        }
    }


}
