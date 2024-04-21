package com.example.dictionaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dictionaryapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayAdapter<String> adapter;
    private List<String> anhViet;
    private DatabaseAccess databaseAccess;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        anhViet = new ArrayList<>();


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, anhViet);
        this.binding.listView.setAdapter(adapter);
        loadMoreData();

        this.binding.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (binding.listView.getLastVisiblePosition() >= binding.listView.getCount() - 1 - 13) {
                        loadMoreData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, anhViet);
        binding.tvSearch.setAdapter(autoCompleteAdapter);
        binding.tvSearch.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedWord = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, DefinitionActivity.class);
            intent.putExtra("word",selectedWord);
            startActivity(intent);
        });
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
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.envn_item){
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.vnen_item) {
                    Intent intent = new Intent(MainActivity.this,VietAnh.class);
                    startActivity(intent);
                }
                binding.main.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DefinitionActivity.class);
                Object word = binding.listView.getItemAtPosition(position);
                intent.putExtra("word",(String) word);
                startActivity(intent);
            }
        });
    }
    private void loadMoreData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        List<String> moreData = databaseAccess.getWords();
        anhViet.addAll(moreData);
        adapter.notifyDataSetChanged();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
            }
        }, 3000);
    }
}
//            case R.id.eng_eng_item:
//                // Xử lý sự kiện khi mục "English - English" được chọn
//                return true;
//            case R.id.vnen_item:
//                // Xử lý sự kiện khi mục "Vietnamese - English" được chọn
//                return true;
//            case R.id.fav_item:
//                // Xử lý sự kiện khi mục "Favourites" được chọn
//                return true;
//            case R.id.your_word_item:
//                // Xử lý sự kiện khi mục "Your words" được chọn
//                return true;
//            case R.id.share_item:
//                // Xử lý sự kiện khi mục "Share" được chọn
//                return true;
//            case R.id.help_item:
//                // Xử lý sự kiện khi mục "Help" được chọn
//                return true;

