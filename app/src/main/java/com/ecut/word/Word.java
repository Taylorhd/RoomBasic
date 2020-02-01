package com.ecut.word;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "foo")
    private boolean foo;

    boolean isFoo() {
        return foo;
    }

    void setFoo(boolean foo) {
        this.foo = foo;
    }

    public int getId() {
        return id;
    }

    @ColumnInfo(name = "english_word")
    private String word;
    @ColumnInfo(name = "chineseMeaning")
    private String chineseMeaning;

    public Word(String word, String chineseMeaning) {
        this.word = word;
        this.chineseMeaning = chineseMeaning;
    }

    public void setWord(String word) {
        this.word = word;
    }

    String getChineseMeaning() {
        return chineseMeaning;
    }

//    public void setChineseMeaning(String chineseMeaning) {
//        this.chineseMeaning = chineseMeaning;
//    }

    public String getWord() {
        return word;
    }
}
