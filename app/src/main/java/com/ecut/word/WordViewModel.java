package com.ecut.word;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    public LiveData<List<Word>> getAllWordLive() {
        return wordRepository.getAllWordsLive();
    }

    public WordRepository getWordRepository() {
        return wordRepository;
    }

    private WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
    }

    public void insertWords(Word... words) {
        wordRepository.insertWords(words);
    }

    void updateWords(Word... words) {
        wordRepository.updateWords(words);
    }

    public void clearWords() {
        wordRepository.clearWords();
    }

    public  void deleteWord(Word... words) {
        wordRepository.deleteWords(words);
    }
    public LiveData<List<Word>> searchByKeyWord(String pattern){
     return wordRepository.searchByKeyWord(pattern);
    }

}
