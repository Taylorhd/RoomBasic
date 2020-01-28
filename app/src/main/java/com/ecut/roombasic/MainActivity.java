package com.ecut.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    WordDataBase wordDataBase;
    WordDao wordDao;
    TextView textView ;
    Button btInsert ,btUpdate,btClear,btDelete;

    WordViewModel wordViewModel;
    RecyclerView recyclerView ;
    MyAdapter myAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        wordDao = wordDataBase.getWordDao();

//        textView = findViewById(R.id.textView);
        btInsert = findViewById(R.id.btInsert);
        btUpdate = findViewById(R.id.btUpdate);
        btClear = findViewById(R.id.btClear);
        btDelete = findViewById(R.id.btDelete);
        recyclerView = findViewById(R.id.recycler_view);
        wordViewModel =  ViewModelProviders.of(this).get(WordViewModel.class);



        myAdapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        wordViewModel.getAllWordLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                myAdapter.setAllWords(words);
                myAdapter.notifyDataSetChanged();
            }
        });

        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word wordhello = new Word("hello","你好");
                Word wordWorld = new Word("world","世界");
//                wordDao.insertWotds(wordhello,wordWorld);
            wordViewModel.insertWords(wordhello,wordWorld);

//                updateView();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("material","材料");
                word.setId(5);
                wordViewModel.updateWords(word);
            }

        });
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              wordDao.deleteAllWords();
//              updateView();
                wordViewModel.clearWords();
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("hi","mni");
                word.setId(90);
                wordViewModel.deleteWord(word);
            }
        });

    }




}