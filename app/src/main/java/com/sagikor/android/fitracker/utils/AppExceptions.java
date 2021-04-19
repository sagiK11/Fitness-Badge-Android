package com.sagikor.android.fitracker.utils;


public class AppExceptions {
    public static final String STUDENT_EXISTS = "student already exists";
    public static final String STUDENT_NOT_FOUND = "student not found";
    public static final String SELECT_NAME = "please select name";
    public static final String SELECT_CLASS = "please select class";
    public static final String SELECT_GENDER = "please select gender";
    public static final String INVALID_PHONE = "invalid phone number";
    public static final String MISSING_CLASSES = "add classes in settings";

    public static class Input extends Exception {

        public Input(String message) {
            super(message);
        }
    }

    public static class StudentExistsAlready extends Exception {

        public StudentExistsAlready() {
            super(STUDENT_EXISTS);
        }
    }

    public static class StudentNotFound extends RuntimeException {

        public StudentNotFound() {
            super(STUDENT_NOT_FOUND);
        }
    }

}
