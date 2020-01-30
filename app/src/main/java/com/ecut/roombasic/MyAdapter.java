package com.ecut.roombasic;

import android.content.Intent;
import android.net.Uri;
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
    public MyAdapter(boolean cardFlag, WordViewModel viewModel) {
        this.cardFlag = cardFlag;
        this.viewModel = viewModel;
    }

    List<Word> allWords = new ArrayList<>();

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (cardFlag){
            // 使用cardview
             itemView = layoutInflater.inflate(R.layout.cell_card_2, parent, false);

        }else {
             itemView = layoutInflater.inflate(R.layout.cell_normal_2, parent, false);
        }

//        return null;
        return  new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
       final Word word = allWords.get(position);
       holder.textId.setText(String.valueOf(position));
       holder.textChinese.setText(word.getChineseMeaning());
       holder.textEnglish.setText(word.getWord());
       holder.switchChineseInvisable.setOnCheckedChangeListener(null);
        if (word.isFoo()){
            holder.textChinese.setVisibility(View.GONE);
            holder.switchChineseInvisable.setChecked(true);
        }else {
            holder.textChinese.setVisibility(View.VISIBLE);
            holder.switchChineseInvisable.setChecked(false);

        }

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Uri uri =Uri.parse("https://m.youdao.com/dict?q="+word.getWord()) ;
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(uri);
               holder.itemView.getContext().startActivity(intent);
           }
       });

       holder.switchChineseInvisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
