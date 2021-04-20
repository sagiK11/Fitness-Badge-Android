package com.sagikor.android.fitracker.ui.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;
import com.sagikor.android.fitracker.ui.presenter.ViewStudentsActivityPresenter;
import com.sagikor.android.fitracker.utils.StudentAdapter;

public class ViewStudentsActivity extends AppCompatActivity implements
        ViewStudentsActivityContract.View {
    private static final String TAG = "ViewDataActivity";
    private StudentAdapter adapter;
    private RecyclerView listView;
    private EditText inputSearch;
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
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(),
                DividerItemDecoration.VERTICAL));
        listView.requestFocus();
    }

    @Override
    public void navToStudentUpdate() {
        startActivity(new Intent(this, UpdateStudentActivity.class));
    }

    @Override
    public void popDeleteStudentSuccess() {
        popMessage(getString(R.string.student_delete_success), msgType.success);
    }

    @Override
    public void popDeleteStudentFail() {
        popMessage(getString(R.string.student_delete_fail), msgType.fail);
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

    @Override
    public void popMessage(String message, msgType type) {
        int backgroundColor;
        switch (type) {
            case success:
                backgroundColor = getColor(R.color.colorPrimary);
                break;
            case alert:
                backgroundColor = getColor(R.color.alert);
                break;
            case dangerous:
                backgroundColor = getColor(R.color.red);
                break;
            default:
                backgroundColor = getColor(R.color.black);
        }
        View contextView = findViewById(R.id.view_students_root);
        Snackbar.make(contextView, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(backgroundColor)
                .show();
    }
}
