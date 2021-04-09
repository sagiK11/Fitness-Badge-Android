package com.sagikor.android.fitracker.utils;

import android.widget.EditText;

public class AppExceptions {
    public static class Input extends Exception {

        public Input(String message) {
            super(message);
        }
    }

    public static class StudentExistsAlready extends Exception {

        public StudentExistsAlready() {
            super("student already exists");
        }
    }

    public static class StudentNotFound extends RuntimeException {

        public StudentNotFound() {
            super("student not found");
        }
    }

}
