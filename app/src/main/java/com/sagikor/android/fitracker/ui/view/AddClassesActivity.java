package com.sagikor.android.fitracker.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.data.model.UserClass;
import com.sagikor.android.fitracker.ui.contracts.AddClassesActivityContract;
import com.sagikor.android.fitracker.ui.presenter.AddClassesActivityPresenter;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AddClassesActivity extends AppCompatActivity implements AddClassesActivityContract.View {
    private static AddClassesActivityContract.Presenter presenter;
    private RecyclerView listView;
    private Button btnAddClass;
    private EditText etClass;
    private ClassAdapter classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classes);
        bindObjects();
        generateDataList();

    }

    private void bindObjects() {
        listView = findViewById(R.id.saved_classes_list_view);
        btnAddClass = findViewById(R.id.btn_add_class);
        etClass = findViewById(R.id.input_class_to_teach);
        btnAddClass.setOnClickListener(e -> presenter.onAddClassClick());
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new AddClassesActivityPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public String getClassToTeach() {
        return etClass.getText().toString().trim();
    }

    @Override
    public void popMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateList() {
        classAdapter.updateList(presenter.getClassesUserTeaches());
    }

    private void generateDataList() {
        presenter = new AddClassesActivityPresenter();
        presenter.bind(this);
        classAdapter = new ClassAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AddClassesActivity.this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(classAdapter);
    }

    private static class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.CustomViewHolder> {
        private static final String TAG = "ClassAdapter";
        private List<UserClass> list = presenter.getClassesUserTeaches();

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.class_list_item, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            UserClass classToTeach = list.get(position);
            holder.tvClassToTeach.setText(classToTeach.getClassName());
            setClassToTeachOnClickListener(holder, position);

        }

        private void setClassToTeachOnClickListener(CustomViewHolder holder, int position) {
            UserClass classToTeach = list.get(position);
            Context context = holder.tvClassToTeach.getContext();
            final String YES = context.getString(R.string.yes);
            final String NO = context.getResources().getString(R.string.no);
            final String DELETE_STUDENT_QUESTION = context.getResources().getString(R.string.delete_class_question);

            //long click
            holder.itemView.setOnLongClickListener(e -> {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(DELETE_STUDENT_QUESTION)
                        .setConfirmText(YES)
                        .setConfirmClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            presenter.onDeleteClassToTeach(classToTeach);
                            deleteItem(position);
                        })
                        .setCancelButton(NO,
                                SweetAlertDialog::dismissWithAnimation)
                        .show();
                return true;
            });

        }

        private void deleteItem(int position) {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size());
        }

        public void updateList(List<UserClass> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private static class CustomViewHolder extends RecyclerView.ViewHolder {
            public final View view;
            private final TextView tvClassToTeach;


            public CustomViewHolder(View view) {
                super(view);
                this.view = view;
                tvClassToTeach = view.findViewById(R.id.class_to_teach);
            }
        }
    }
}
