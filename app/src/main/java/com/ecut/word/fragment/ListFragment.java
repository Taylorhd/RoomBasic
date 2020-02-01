package com.ecut.word.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecut.word.MyAdapter;
import com.ecut.word.R;
import com.ecut.word.Word;
import com.ecut.word.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * @author Cora
 */
public class ListFragment extends Fragment {
    private static final String CARD_VIEW_STATE = "card_view_state";

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private WordViewModel wordViewModel;
    private FloatingActionButton floatingActionButton;
    private LiveData<List<Word>> filterWords;
    private List<Word> allWords;
    private boolean undoFlag;

    public ListFragment() {
        setHasOptionsMenu(true);

        // Required empty public constructor
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.clearView:

                // 弹出确认对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle(R.string.dialog_title);
                builder.setPositiveButton(R.string.dialog_postive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 清除视图
                        wordViewModel.clearWords();
                    }
                });
                builder.setNegativeButton(R.string.dialog_negtive, null);
                builder.create();
                builder.show();
                break;
            case R.id.switchView:
                // 切换视图


                SharedPreferences shp = requireActivity().getPreferences(Context.MODE_PRIVATE);
                boolean currentView = shp.getBoolean(CARD_VIEW_STATE, false);
                SharedPreferences.Editor editor = shp.edit();
                editor.putBoolean(CARD_VIEW_STATE, !currentView);
                editor.apply();
                adapter.setCardFlag(!currentView);
                recyclerView.setAdapter(adapter);


                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.word_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 查询
                filterWords.removeObservers(requireActivity());
                filterWords = wordViewModel.searchByKeyWord(newText.toString().trim());
                filterWords.observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
                    @Override
                    public void onChanged(List<Word> words) {
                        int temp = adapter.getItemCount();
//                        adapter.setAllWords(words);
                        allWords = words;
                        if (temp != words.size()) {
                            adapter.submitList(words);
//                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = requireActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        SharedPreferences shp = requireActivity().getPreferences(Context.MODE_PRIVATE);
        boolean currentView = shp.getBoolean(CARD_VIEW_STATE, false);
        adapter = new MyAdapter(currentView, wordViewModel);
             //回调，刷新数据的id
        recyclerView.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                super.onAnimationFinished(viewHolder);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null) {
                    int firstPostion = linearLayoutManager.findFirstVisibleItemPosition();
                    int lastPostion = linearLayoutManager.findLastVisibleItemPosition();
                    for (int i = firstPostion; i <= lastPostion; i++) {
                        MyAdapter.MyHolder holder = (MyAdapter.MyHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (holder != null) {
                            holder.textId.setText(String.valueOf(i + 1));
                        }
                    }
                }
            }
        });

        recyclerView.setAdapter(adapter);
        filterWords = wordViewModel.getAllWordLive();
        filterWords.observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                allWords =  words;
                int temp = adapter.getItemCount();
                if (temp != words.size()) {
                    if (temp<words.size() &&!undoFlag) {
                        recyclerView.smoothScrollBy(0, -250);
                    }
                    undoFlag = false;
                    adapter.submitList(words);
                }
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Word wordToDelete = allWords.get(viewHolder.getAdapterPosition());
                wordViewModel.deleteWord(wordToDelete);
                Snackbar.make(requireActivity().findViewById(R.id.list_fragment),"删除当前词条",Snackbar.LENGTH_LONG).setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wordViewModel.insertWords(wordToDelete);
                        undoFlag = true;
                    }
                }).show();


            }
        }).attachToRecyclerView(recyclerView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_listFragment_to_addWordFragment);
            }
        });


    }
}
