package com.project.ogrenciasistanidenemeprojesi.yoklama.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisteredCoursesAdapter extends RecyclerView.Adapter<RegisteredCoursesAdapter.CourseViewHolder> {

    private static final String TAG = "CourseRegisterAdapter";

    private List<Course> courses;
    private Context context;

    public RegisteredCoursesAdapter(List<Course> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_registered_courses, parent, false);

        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentCourse = courses.get(position);

        holder.tv_course_name.setText(currentCourse.getCourseName());
        holder.tv_course_teacher.setText(currentCourse.getCourseTeacher());

        //Katıl butonuyla derse katılacak
        holder.button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinCourse(currentCourse);
            }
        });

    }

    private void joinCourse(Course currentCourse) {
        //Course referansını alıyoruz
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Course course = dataSnapshot.getValue(Course.class);

                    //Tıklanan dersi alıyoruz
                    if (currentCourse.getCourseId().equals(course.getCourseId())) {

                        //Eğer ders aktifse bu blok çalışıyor
                        if (course.isActive()) {

                            //Katılımcı listesine kullanıcının id'sini ekliyoruz
                            List<String> attendanceList = new ArrayList<>();
                            attendanceList.add(currentUser.getUid());

                            HashMap<String, List<String>> attendanceMap;

                            //Eğer o dersin yoklama hashmapi boş değilse bu if bloğu çalışıyor
                            if(course.getAttendance() != null) {
                                //Dersin yoklama hashmapini yukarıdaki hashmap'e atıyoruz
                                attendanceMap = course.getAttendance();

                                //Eğer bu yoklama hashmapinde, tıklanan dersin haftası boş değilse  bu if bloğu çalışıyor
                                if(attendanceMap.get("hafta" + course.getWeek()) != null)  {
                                    //ve ve bu yoklama listesinde, tıklanan haftada tıklayan kullanıcının idsi bulunmuyorsa bu if bloğu çalışıyor
                                    if(!attendanceMap.get("hafta" + course.getWeek()).contains(currentUser.getUid())){
                                        List<String> list = attendanceMap.get("hafta" + course.getWeek());
                                        list.add(currentUser.getUid());
                                        attendanceMap.put("hafta" + course.getWeek(), list);
                                        //kullanıcın idsini o haftaya ekliyoruz
                                    }
                                    //Eğer zaten ekliyse uyarı veriliyor
                                    else {
                                        Toast.makeText(context, "Zaten bu derse katıldınız.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //Eğer o haftadaki liste boşsa, direkt olarak yukarıda attendanceList'e kullanıcı id'sini eklediğimiz için bu listeyi hashmapin ilgili haftasına koyuyoruz
                                else {
                                    attendanceMap.put("hafta" + course.getWeek(), attendanceList);
                                }

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("attendance", attendanceMap);
                                reference.child(dataSnapshot.getKey()).updateChildren(hashMap);
                                //En sonda da o haftanın yoklama bilgisini güncelliyoruz

                                Toast.makeText(context, course.getCourseName() + " dersinin " + course.getWeek() + ". haftasına katıldınız.", Toast.LENGTH_SHORT).show();
                            }

                            //Eğer seçilen dersin yoklaması boşsa direkt yeni bir hashmap oluşturup aşağıdaki gibi, haftanın adıyla birlikte kullanıcın id'si olduğu listeyi ekliyoruz
                            else {
                                attendanceMap = new HashMap<>();
                                if(attendanceMap.get("hafta" + course.getWeek()) != null)  {
                                    if(!attendanceMap.get("hafta" + course.getWeek()).contains(currentUser.getUid())){
                                        List<String> list = attendanceMap.get("hafta" + course.getWeek());
                                        list.add(currentUser.getUid());
                                        attendanceMap.put("hafta" + course.getWeek(), list);

                                    }
                                    else {
                                        Toast.makeText(context, "Zaten bu derse katıldınız.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    attendanceMap.put("hafta" + course.getWeek(), attendanceList);
                                }

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("attendance", attendanceMap);
                                reference.child(dataSnapshot.getKey()).updateChildren(hashMap);
                                //Aynı şekilde o haftanın yoklama bilgisini güncelliyoruz

                                Toast.makeText(context, course.getCourseName() + " dersinin " + course.getWeek() + ". haftasına katıldınız.", Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {
                            Toast.makeText(context, "Bu ders aktif değildir.", Toast.LENGTH_SHORT).show();
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
    public int getItemCount() {
        return courses.size();
    }


    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_course_name;
        private TextView tv_course_teacher;

        private Button button_join;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_course_name = itemView.findViewById(R.id.tv_course_name_registered);
            tv_course_teacher = itemView.findViewById(R.id.tv_course_teacher_registered);
            button_join = itemView.findViewById(R.id.button_join);
        }
    }



}
