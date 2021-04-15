package com.sagikor.android.fitracker.ui.presenter;


import com.google.firebase.auth.FirebaseAuth;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.UpdateStudentActivityContract;
import com.sagikor.android.fitracker.utils.Utility;

import static com.sagikor.android.fitracker.utils.Utility.MISSING_INPUT;

public class UpdateStudentActivityPresenter extends StudentActivityPresenter implements
        UpdateStudentActivityContract.Presenter {

    private static final String TAG = "UpdateStudentActPre";
    private UpdateStudentActivityContract.View view;
    private Student currentStudent;

    @Override
    public void onSaveButtonClick() {
        updateStudent();
    }

    @Override
    public Student getCachedObject() {
        return (Student) databaseHandler.getCachedObject();
    }

    @Override
    public void bind(UpdateStudentActivityContract.View view) {
        super.bind(view);
        this.view = view;
        currentStudent = getCachedObject();
        updateViewFields();
    }

    @Override
    public void unbind() {
        super.unbind();
        view = null;
    }

    private void updateViewFields() {
        view.setStudentName(currentStudent.getName());
        view.setStudentClass(currentStudent.getStudentClass());
        view.setStudentGender(currentStudent.getGender());
        if (currentStudent.getAerobicScore() != MISSING_INPUT)
            view.setAerobicScore(String.valueOf(currentStudent.getAerobicScore()));
        if (currentStudent.getCubesScore() != MISSING_INPUT)
            view.setCubesScore(String.valueOf(currentStudent.getCubesScore()));
        if (currentStudent.getHandsScore() != MISSING_INPUT)
            view.setHandsScore(String.valueOf(currentStudent.getHandsScore()));
        if (currentStudent.getAbsScore() != MISSING_INPUT)
            view.setAbsScore(String.valueOf(currentStudent.getAbsScore()));
        if (currentStudent.getJumpScore() != MISSING_INPUT)
            view.setJumpScore(String.valueOf(currentStudent.getJumpScore()));
        if (currentStudent.getPhoneNumber() != null)
            view.setStudentPhoneNo(currentStudent.getPhoneNumber());
        view.updateTotalScore(currentStudent.getTotalScore());
    }

    private void updateStudent() {
        Student updatedStudent = new Student.Builder(view.getStudentName())
                .studentClass(view.getStudentClass())
                .phoneNumber(view.getStudentPhoneNo())
                .key(currentStudent.getKey())
                .userId(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .studentGender(view.getStudentGender())
                .aerobicScore(parse(view.getAerobicScore()))
                .cubesScore(parse(view.getCubesScore()))
                .absScore(parse(view.getAbsSore()))
                .jumpScore(parse(view.getJumpScore()))
                .handsScore(parse(view.getHandsScore()))
                .absResult(parse(view.getAbsGrade()))
                .aerobicResult(parse(view.getAerobicGrade()))
                .jumpResult(parse(view.getJumpGrade()))
                .handsResult(parse(view.getHandsGrade()))
                .cubesResult(parse(view.getCubesGrade()))
                .totalScore(super.getAverage())
                .updatedDate(Utility.getTodayDate())
                .build();

        databaseHandler.updateStudent(this, updatedStudent);
    }

    @Override
    public void onUpdateStudentSuccess(Student student) {
        view.updateTotalScore(student.getTotalScore());
        view.popSuccessWindow();
        view.askForSendingSMS(student);
    }

    @Override
    public void onUpdateStudentFailed() {
        view.popFailWindow(Utility.GENERIC_ERROR_MESSAGE);
    }
}
