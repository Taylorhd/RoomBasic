package com.ecut.word;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private boolean cardFlag;
    private WordViewModel viewModel;
    List<Word> allWords = new ArrayList<>();

    public MyAdapter(boolean cardFlag, WordViewModel viewModel) {
        this.cardFlag = cardFlag;
        this.viewModel = viewModel;
    }

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        final MyHolder holder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (cardFlag){
            // 使用cardview
             itemView = layoutInflater.inflate(R.layout.cell_card_2, parent, false);

        }else {
             itemView = layoutInflater.inflate(R.layout.cell_normal_2, parent, false);
        }

        holder = new MyHolder(itemView);
//       final   Word word = (Word)holder.itemView.getTag(R.id.word_for_view_holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Word word = (Word)holder.itemView.getTag(R.id.word_for_view_holder);
                Uri uri =Uri.parse("https://m.youdao.com/dict?q="+word.getWord()) ;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);

            }
        });

        holder.switchChineseInvisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Word word = (Word)holder.itemView.getTag(R.id.word_for_view_holder);
                if (isChecked){
                    holder.textChinese.setVisibility(View.GONE);
                    word.setFoo(true);
                    viewModel.updateWords(word);

                }else{


                    holder.textChinese.setVisibility(View.VISIBLE);
                    // 设置为0
                    word.setFoo(false);
                    viewModel.updateWords(word);

                }

            }
        });

//        return null;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
       final Word word = allWords.get(position);
       holder.itemView.setTag(R.id.word_for_view_holder,word);
       holder.textId.setText(String.valueOf(position));
       holder.textChinese.setText(word.getChineseMeaning());
       holder.textEnglish.setText(word.getWord());

        if (word.isFoo()){
            holder.textChinese.setVisibility(View.GONE);
            holder.switchChineseInvisable.setChecked(true);
        }else {
            holder.textChinese.setVisibility(View.VISIBLE);
            holder.switchChineseInvisable.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    public void setCardFlag(boolean cardFlag) {
        this.cardFlag = cardFlag;
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        private TextView textId,textChinese,textEnglish;
        private Switch switchChineseInvisable;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            switchChineseInvisable = itemView.findViewById(R.id.switch_chinese);
            textId = itemView.findViewById(R.id.textView);
            textEnglish = itemView.findViewById(R.id.textView2);
            textChinese = itemView.findViewById(R.id.textView3);
        }
    }

}
