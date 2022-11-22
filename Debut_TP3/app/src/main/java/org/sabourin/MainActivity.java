package org.sabourin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import org.sabourin.bd.BD;
import org.sabourin.databinding.ActivityMainBinding;
import org.sabourin.exceptions.MauvaiseQuestion;
import org.sabourin.modele.VDQuestion;
import org.sabourin.service.Service;

public class MainActivity extends AppCompatActivity {
    private Service service;
    private BD maBD;
    private ActivityMainBinding binding;
    QuestionAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        service = new Service(maBD);
        //creerQuestion();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        this.initRecycler();
        binding.btnAjouter.setOnClickListener(view3 -> {
            {
                Intent i = new Intent(MainActivity.this,CreationQuestion.class);
                startActivity(i);
            }
        });

        for (VDQuestion q: service.toutesLesQuestions())
        {

            adapter.list.add(q);

        }

        adapter.notifyDataSetChanged();



    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new QuestionAdapter();
        recyclerView.setAdapter(adapter);
    }




}