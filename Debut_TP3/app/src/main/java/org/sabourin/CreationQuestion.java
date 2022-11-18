package org.sabourin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.sabourin.databinding.CreationQuestionBinding;


public class CreationQuestion extends AppCompatActivity {
    private CreationQuestionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = CreationQuestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnSubmit.setOnClickListener(view1 -> {
        {
                Intent i = new Intent(CreationQuestion.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
