package org.sabourin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import org.sabourin.bd.BD;
import org.sabourin.databinding.CreationQuestionBinding;
import org.sabourin.exceptions.MauvaiseQuestion;
import org.sabourin.modele.VDQuestion;
import org.sabourin.service.Service;


public class CreationQuestion extends AppCompatActivity {
    private CreationQuestionBinding binding;
    private  BD maBd;
    private Service service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = CreationQuestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        maBd =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        service = new Service(maBd);
        binding.btnSubmit.setOnClickListener(view1 -> {
        {
                Intent i = new Intent(CreationQuestion.this,MainActivity.class);
                startActivity(i);
            }

                VDQuestion maQuestion = new VDQuestion();
                maQuestion.texteQuestion = binding.editTextTextPersonName.getText().toString();
            try {
                service.creerQuestion(maQuestion);
            } catch (MauvaiseQuestion e) {
                e.printStackTrace();
            }

        });



    }
}
