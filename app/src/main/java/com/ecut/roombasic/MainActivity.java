package com.ecut.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        wordDao = wordDataBase.getWordDao();

        textView = findViewById(R.id.textView);
        btInsert = findViewById(R.id.btInsert);
        btUpdate = findViewById(R.id.btUpdate);
        btClear = findViewById(R.id.btClear);
        btDelete = findViewById(R.id.btDelete);
//        allWordsLive = wordDao.getAllWordsLive();
        wordViewModel =  ViewModelProviders.of(this).get(WordViewModel.class);
        wordViewModel.getAllWordLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder text = new StringBuilder();
                for (Word item : words) {
                    text.append(item.getId()).append(item.getWord()).append(item.getChineseMeaning()).append("\n");
                }
                textView.setText(text.toString());
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
//                wordDao.updateWords(word);
//                updateView();
//                new UpdateAsyncTask(wordDao).execute(word);
                wordViewModel.updateWords(word);
            }

        });
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              wordDao.deleteAllWords();
//              updateView();
//                new ClearAsyncTask(wordDao).execute();
                wordViewModel.clearWords();
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("hi","mni");
                word.setId(90);
//                wordDao.deleteWords(word);
//                updateView();
//                new DeleteAsyncTask(wordDao).doInBackground(word);
//                new DeleteAsyncTask(wordDao).execute(word);
                wordViewModel.deleteWord(word);
            }
        });

    }

//    private void updateView() {
//        String text = "";
//        List<Word> words = wordDao.getAllWords();
//        for (Word item:words) {
//            text  = text + item.getId()+item.getWord()+"   "+item.getChineseMeaning()+"\n";
//        }
//        textView.setText(text);
//
//    }


}
