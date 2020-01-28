package com.ecut.roombasic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    List<Word> allWords = new ArrayList<>();

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_normal,parent,false);
//        return null;
        return  new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
       Word word = allWords.get(position);
       holder.textId.setText(String.valueOf(word.getId()));
       holder.textChinese.setText(word.getChineseMeaning());
       holder.textEnglish.setText(position);
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        private TextView textId,textChinese,textEnglish;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.textView);
            textEnglish = itemView.findViewById(R.id.textView2);
            textChinese = itemView.findViewById(R.id.textView3);

        }
    }

}
