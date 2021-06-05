package com.project.ogrenciasistanidenemeprojesi.notlar;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.notlar.adapter.OnTodoClickListener;
import com.project.ogrenciasistanidenemeprojesi.notlar.adapter.RecyclerViewAdapter;
import com.project.ogrenciasistanidenemeprojesi.notlar.model.SharedViewModel;
import com.project.ogrenciasistanidenemeprojesi.notlar.model.Task;
import com.project.ogrenciasistanidenemeprojesi.notlar.model.TaskViewModel;

public class Main4Activity extends AppCompatActivity implements OnTodoClickListener {
    private static final String TAG = "ITEM" ;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TaskViewModel taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                com.project.ogrenciasistanidenemeprojesi.notlar.Main4Activity.this.getApplication())
                .create(TaskViewModel.class);

        sharedViewModel = new ViewModelProvider(this)
                .get(SharedViewModel.class);





        taskViewModel.getAllTasks().observe(this, tasks -> {
              recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
              recyclerView.setAdapter(recyclerViewAdapter);


        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showBottomSheetDialog());
    }

    private void showBottomSheetDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }


    @Override
    public void onTodoClick(Task task) {
        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);
        //Log.d("Click", "onTodoClick: " + task.getTask());
        showBottomSheetDialog();


    }

    @Override
    public void onTodoRadioButtonClick(Task task) {
        Log.d("Click", "onRadioButton: " + task.getTask());
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}