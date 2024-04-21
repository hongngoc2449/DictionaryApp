package com.example.dictionaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dictionaryapp.databinding.ActivityDefinitionBinding;

public class DefinitionActivity extends AppCompatActivity {
    private ActivityDefinitionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_definition);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityDefinitionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        binding.tvWord.setText(word);

        DatabaseAccess dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();
        String definition = dbAccess.getDefinition(word);
        dbAccess.close();

        binding.tvDefinition.setText(Html.fromHtml(definition));
    }
}