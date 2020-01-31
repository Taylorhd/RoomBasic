package com.ecut.word.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.ecut.word.R;
import com.ecut.word.Word;
import com.ecut.word.WordViewModel;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddWordFragment extends Fragment {
    private Button submitButton;
    private EditText editTextChinese;
    private EditText editTextEnglish;
    private WordViewModel wordViewModel;

    public AddWordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_word, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordViewModel = ViewModelProviders.of(requireActivity()).get(WordViewModel.class);
        submitButton =requireActivity().findViewById(R.id.buttonAdd);
        editTextChinese = requireActivity().findViewById(R.id.editTextChinese);
        editTextEnglish =requireActivity().findViewById(R.id.editTextEnglish);
        submitButton.setEnabled(false);
        editTextEnglish.requestFocus();
        InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextEnglish,0);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String englishWord = editTextEnglish.getText().toString().trim();
                    String chineseWord = editTextChinese.getText().toString().trim();
                    if (!englishWord.isEmpty()&& !chineseWord.isEmpty()){
                        submitButton.setEnabled(true);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        editTextChinese.addTextChangedListener(textWatcher);
        editTextEnglish.addTextChangedListener(textWatcher);


        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String englishWord = editTextEnglish.getText().toString().trim();
                String chineseWord = editTextChinese.getText().toString().trim();
                Word word = new Word(englishWord,chineseWord);
                wordViewModel.insertWords(word);
                NavController navController = Navigation.findNavController(v);
                navController.navigateUp();
            }
        });
    }
}
