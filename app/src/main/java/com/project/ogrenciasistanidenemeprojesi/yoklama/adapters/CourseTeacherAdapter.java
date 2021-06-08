package com.project.ogrenciasistanidenemeprojesi.yoklama.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.activities.CourseDetailsActivity;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Course;

import java.util.HashMap;
import java.util.List;

public class CourseTeacherAdapter extends RecyclerView.Adapter<CourseTeacherAdapter.CourseViewHolder> {

    private List<Course> courses; //database'e eklenen derslere erişmek için dersleri bulunduran liste
    private Context context;

    public CourseTeacherAdapter(List<Course> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    @NonNull
    @Override
    // Her bir satır için temsil edilecek olan arayüz belirlenir.
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_course_item_teacher, parent, false);

        return new CourseViewHolder(v);
    }

    @Override
    // Her bir satırın içeriği belirlenir.
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentCourse = courses.get(position);

        //Burada her bir kurs için bilgiler ayrı ayrı gerekli alanlara yazdırıyoruz
        holder.tv_course_name.setText(currentCourse.getCourseName());
        holder.tv_course_teacher.setText(currentCourse.getCourseTeacher());
        holder.tv_registered_students.setText("Kayıtlı Öğrenci: " + currentCourse.getRegisteredStudents());
        holder.tv_week_count.setText("Hafta Sayısı: " + currentCourse.getWeek());

        //Kurs aktifse butona durdur, değilse başlat yazdırıyoruz
        if (currentCourse.isActive()) holder.button_active_deactive.setText("Durdur");
        else holder.button_active_deactive.setText("Başlat");

        //Başlat veya durdur butonuna basılınca setCourseActive methodu çalışacak
        holder.button_active_deactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCourseActive(currentCourse, holder);
            }
        });

        //root_layout her bir derse ait referans, buradan derse tıklandığında putExtra ile tıklanan dersi de göndererek CourseDetailsActivity'i açıyoruz
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailsActivity.class);
                intent.putExtra("Course", currentCourse);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_course_name;
        private TextView tv_course_teacher;
        private TextView tv_registered_students;
        private TextView tv_week_count;

        private Button button_active_deactive;

        private CardView root_layout;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            tv_course_teacher = itemView.findViewById(R.id.tv_course_teacher);
            tv_registered_students = itemView.findViewById(R.id.tv_registered_students);
            tv_week_count = itemView.findViewById(R.id.tv_week_count);
            button_active_deactive = itemView.findViewById(R.id.button_active_deactive);

            root_layout = itemView.findViewById(R.id.cardview_root_course_item);
        }
    }

    private void setCourseActive(Course currentCourse, CourseViewHolder holder) {
        //Derslere erişmek  için Courses referansını alıyoruz
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Course course = dataSnapshot.getValue(Course.class);

                    //Eğer tıklanan ders (currentCourse) database'deki derse eşitse database'i güncelliyoruz
                    if (currentCourse.getCourseId().equals(course.getCourseId())) {

                        /*Eğer ders aktifse ders aktiflik bilgisini false yapıp hashMap'e kaydediyoruz,
                         daha sonra course referansındaki child(courseId) ile şu anki dersi alıp reference.updateChildren ile aktiflik bilgisini güncelliyoruz*/
                        if (course.isActive()) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("active", false);
                            reference.child(dataSnapshot.getKey()).updateChildren(hashMap);
                            //dataSnapshot.getKey() = ders Id'si

                            //ayrıca butonda yazan bilgiyi de buradan güncelliyoruz
                            holder.button_active_deactive.setText("Başlat");
                            holder.tv_week_count.setText("Hafta Sayısı: " + course.getWeek());
                        }
                        /*Eğer ders aktif değilse ders aktiflik bilgisini true yapıyoruz,
                         ayrıca şimdiki dersin haftasını alıp 1 arttırarak hem aktifliği hem de hafta sayısını hashMap'e kaydediyoruz
                         daha sonra course referansındaki child(courseId) ile şu anki dersi alıp reference.updateChildren ile aktiflik bilgisini güncelliyoruz*/
                        else {
                            int week = course.getWeek() + 1;

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("active", true);
                            hashMap.put("week", week);
                            reference.child(dataSnapshot.getKey()).updateChildren(hashMap);

                            holder.button_active_deactive.setText("Durdur");
                            holder.tv_week_count.setText("Hafta Sayısı: " + week);
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
