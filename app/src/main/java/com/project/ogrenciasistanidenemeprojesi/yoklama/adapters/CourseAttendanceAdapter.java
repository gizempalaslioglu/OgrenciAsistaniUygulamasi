package com.project.ogrenciasistanidenemeprojesi.yoklama.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.AttendanceListModel;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Student;

import java.util.List;


public class CourseAttendanceAdapter extends RecyclerView.Adapter<CourseAttendanceAdapter.CourseViewHolder> {

    private static final String TAG = "CourseRegisterAdapter";

    private List<AttendanceListModel> attendanceList;

    public CourseAttendanceAdapter(List<AttendanceListModel> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_student_attendance, parent, false);

        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        AttendanceListModel attendance = attendanceList.get(position);
        Student currentStudent = attendance.getStudent();

        holder.tv_student_name.setText(currentStudent.getNumber() + " " + currentStudent.getName() + " " + currentStudent.getSurname());
        holder.tv_course_name.setText(attendance.getCourseName());
        holder.tv_week.setText(attendance.getWeekPos());
        holder.tv_if_success.setText(attendance.getIfSuccess());
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }


    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_course_name;
        private TextView tv_student_name;
        private TextView tv_week;

        private TextView tv_if_success;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_course_name = itemView.findViewById(R.id.tv_course_name_attendance);
            tv_student_name = itemView.findViewById(R.id.tv_student_id_name_attendance);
            tv_week = itemView.findViewById(R.id.tv_attendance_week);
            tv_if_success = itemView.findViewById(R.id.tv_if_success);
        }
    }



}
