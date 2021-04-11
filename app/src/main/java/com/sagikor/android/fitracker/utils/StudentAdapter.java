package com.sagikor.android.fitracker.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StudentAdapter extends  RecyclerView.Adapter<StudentAdapter.CustomViewHolder> {
    private static final String TAG = "StudentAdapter";
    private List<Student> list;
    private final ViewStudentsActivityContract.Presenter presenter;

    public StudentAdapter( ViewStudentsActivityContract.Presenter presenter){
        this.list = presenter.getStudentsList();
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_list_item,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Student student = list.get(position);
        holder.tvName.setText(student.getName());
        holder.tvClass.setText(student.getStudentClass());
        String female =  holder.ivAvatar.getContext().getResources().getString(R.string.girl);
        if(list.get(position).getGender().equals(female))
            holder.ivAvatar.setImageResource(R.mipmap.female_athlete_avatar);
        else
            holder.ivAvatar.setImageResource(R.mipmap.male_avatar);
        setStudentOnClickListener(holder,position);

    }

    private void setStudentOnClickListener(CustomViewHolder holder,int position) {
        Student student = list.get(position);
        Context context = holder.ivAvatar.getContext();
        final String YES = context.getString(R.string.yes);
        final String NO = context.getResources().getString(R.string.no);
        final String DELETE_STUDENT_QUESTION = context.getResources().getString(R.string.delete_student_question);
        //short click
        holder.itemView.setOnClickListener(e ->
            presenter.onStudentClick(student.getKey())
        );
        //long click
        holder.itemView.setOnLongClickListener(e->{
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(DELETE_STUDENT_QUESTION)
                    .setConfirmText(YES)
                    .setConfirmClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                        presenter.deleteStudent(student);
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

    public void updateList(List<Student> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder{
        public final View view;

        private final TextView tvName;
        private final TextView tvClass;
        private final ImageView ivAvatar;

        public CustomViewHolder(View view){
            super(view);
            this.view = view;

            tvName = view.findViewById(R.id.student_name_view_data_id);
            tvClass = view.findViewById(R.id.student_class_view_data_id);
            ivAvatar = view.findViewById(R.id.avatar_image_view);
        }
    }
}
