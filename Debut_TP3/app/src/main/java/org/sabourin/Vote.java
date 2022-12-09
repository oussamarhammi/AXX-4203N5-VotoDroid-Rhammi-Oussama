package org.sabourin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import org.sabourin.bd.BD;
import org.sabourin.databinding.VoteBinding;
import org.sabourin.exceptions.MauvaisVote;
import org.sabourin.exceptions.MauvaiseQuestion;
import org.sabourin.modele.VDQuestion;
import org.sabourin.modele.VDVote;
import org.sabourin.service.Service;


public class Vote extends AppCompatActivity {
    private VoteBinding binding;
    private Service service;
    private BD maBD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);

        binding = VoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.textView2.setText(getIntent().getStringExtra("texte"));

        binding.button.setOnClickListener(View1 -> {
            {
                Intent i = new Intent(Vote.this, MainActivity.class);


                VDVote monVote = new VDVote();
                monVote.nom = binding.editTextTextPersonName2.getText().toString();
                monVote.barVote = binding.ratingBar.getRating();
                long monId = getIntent().getLongExtra("idtexte",-1);
                monVote.idQuestion = monId;
                startActivity(i);

                try {
                    service.creerVote(monVote);
                } catch (MauvaisVote e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

}
