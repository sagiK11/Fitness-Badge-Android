package com.sagikor.android.fitracker.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;
import com.sagikor.android.fitracker.ui.presenter.ViewStudentsActivityPresenter;
import com.sagikor.android.fitracker.utils.StudentAdapter;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewStudentsActivity extends AppCompatActivity implements ViewStudentsActivityContract.View {
    private static final String TAG = "ViewDataActivity";
    StudentAdapter adapter;
    private ListView listView;
    private EditText inputSearch;
    private List<Student> studentsList;
    private ViewStudentsActivityContract.Presenter presenter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        bindViews();
        addInputSearch();

        addListViewItemListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        if (presenter == null)
            presenter = new ViewStudentsActivityPresenter();
        presenter.bind(this);
        studentsList = presenter.getStudentsList();
        adapter = new StudentAdapter(this,studentsList);
        listView.setAdapter(adapter);
        presenter.getStudentsList();
        refreshList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }


    private void bindViews() {
        listView = findViewById(R.id.list_view);
        inputSearch = findViewById(R.id.search_input);
    }

    @Override
    public void navToStudentUpdate() {
        startActivity(new Intent(this, UpdateStudentActivity.class));
    }


    private void addListViewItemListener() {
        listView.setOnItemClickListener((parent, view, position, id) ->
                presenter.onStudentClick(parent.getItemAtPosition(position)));

        final String YES = getResources().getString(R.string.yes);
        final String NO = getResources().getString(R.string.no);
        final String DELETE_STUDENT_QUESTION = getResources().getString(R.string.delete_student_question);

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            final Student currentStudent = (Student) parent.getItemAtPosition(position);
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(DELETE_STUDENT_QUESTION)
                    .setConfirmText(YES)
                    .setConfirmClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                        presenter.deleteStudent(currentStudent);
                    })
                    .setCancelButton(NO,
                            SweetAlertDialog::dismissWithAnimation)
                    .show();
            return true;

        });

    }

    @Override
    public void refreshList() {
        //TODO I delete a piece of code here. watch out.
        adapter.notifyDataSetChanged();
        onRestart();
    }

    private void addInputSearch() {

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //AUTO-GENERATED
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ViewStudentsActivity.this.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //AUTO-GENERATED
            }
        });

    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
}
