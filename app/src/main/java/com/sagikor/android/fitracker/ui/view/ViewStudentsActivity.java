package com.sagikor.android.fitracker.ui.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;
import com.sagikor.android.fitracker.ui.presenter.ViewStudentsActivityPresenter;
import com.sagikor.android.fitracker.utils.StudentAdapter;

public class ViewStudentsActivity extends AppCompatActivity implements ViewStudentsActivityContract.View {
    private static final String TAG = "ViewDataActivity";
    private StudentAdapter adapter;
    private RecyclerView listView;
    private EditText inputSearch;
    private ProgressBar progressBar;
    private ViewStudentsActivityContract.Presenter presenter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        bindViews();
        generateDataList();
        addInputSearch();
    }

    private void generateDataList() {
        presenter = new ViewStudentsActivityPresenter();
        adapter = new StudentAdapter(presenter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewStudentsActivity.this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new ViewStudentsActivityPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }


    private void bindViews() {
        listView = findViewById(R.id.list_view);
        inputSearch = findViewById(R.id.search_input);
        progressBar = findViewById(R.id.view_students_progress_bar);
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void navToStudentUpdate() {
        startActivity(new Intent(this, UpdateStudentActivity.class));
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void notifyAdapterDataChanged() {
        adapter.notifyDataSetChanged();
    }

    private void addInputSearch() {
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //AUTO-GENERATED
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.updateList(presenter.getFilteredList(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //AUTO-GENERATE
            }
        });
    }
}
