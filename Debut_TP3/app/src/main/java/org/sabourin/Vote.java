package org.sabourin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.sabourin.databinding.VoteBinding;
import org.sabourin.exceptions.MauvaisVote;
import org.sabourin.exceptions.MauvaiseQuestion;
import org.sabourin.modele.VDQuestion;
import org.sabourin.modele.VDVote;
import org.sabourin.service.Service;


public class Vote extends AppCompatActivity {
    private VoteBinding binding;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = VoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.button.setOnClickListener(View1 ->{
            {
            Intent i = new Intent(Vote.this,MainActivity.class);
            startActivity(i);
        }
        VDVote monVote = new VDVote();
        try {
            service.creerVote(monVote);
        } catch (MauvaisVote e) {
            Log.e("CREERVOTE", "Impossible de créer le vote : " + e.getMessage());
        }
        });
    }

}
