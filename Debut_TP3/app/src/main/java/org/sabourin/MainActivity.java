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


    private void remplirRecycler() {
        for (int i = 0 ; i < 20 ; i++) {

            String [] questions= {"aimes tu les pommes?","aimes tu les bananes?",
                    "aimes tu la pastèque? ","aimes tu les abricots?","aimes tu les pêche?","aimes tu les poires?" ,"aimes tu les mangues?","aimes tu les ananas?"
                    ,"aimes tu les kiwis?","aimes tu les figues? ","aimes tu les framboises?","aimes tu les myrtilles?","aimes tu les melons?","aimes tu les raisons?"
                    ,"aimes tu les mûres?","aimes tu les fraises?","aimes tu les cassis?","aimes tu les groseilles?","aimes tu les prunes?","aimes tu les cerises ?",
                    "aimes tu les pamplemousses?","aimes tu les clémentines?"

            };

            Question question =  new Question();
            question.question = questions[i];
            adapter.list.add(question);

        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);
        creerQuestion();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.btnAjouter.setOnClickListener(view3 -> {
            {
                Intent i = new Intent(MainActivity.this,CreationQuestion.class);
                startActivity(i);
            }
        });
        // initialisation du recycler
        this.initRecycler();
        this.remplirRecycler();
    }

    private void creerQuestion (){
        try{
            VDQuestion maQuestion = new VDQuestion();
            maQuestion.texteQuestion = "As-tu hâte au nouveau film The Matrix Resurrections?";
            service.creerQuestion(maQuestion);
        }catch (MauvaiseQuestion m){
            Log.e("CREERQUESTION", "Impossible de créer la question : " + m.getMessage());
        }
    }
}