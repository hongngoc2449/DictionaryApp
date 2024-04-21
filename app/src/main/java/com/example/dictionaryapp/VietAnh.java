package com.example.dictionaryapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dictionaryapp.databinding.ActivityMainBinding;
import com.example.dictionaryapp.databinding.ActivityVietAnhBinding;

import java.util.List;

public class VietAnh extends AppCompatActivity {
    private ActivityVietAnhBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viet_anh);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityVietAnhBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> vietAnh = databaseAccess.getWords();
        databaseAccess.close();

        binding.btOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.main.isDrawerOpen(GravityCompat.START)) {
                    binding.main.closeDrawer(GravityCompat.START);
                } else {
                    binding.main.openDrawer(GravityCompat.START);
                }
            }
        });
    }
}