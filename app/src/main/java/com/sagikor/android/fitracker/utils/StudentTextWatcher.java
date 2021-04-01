package com.sagikor.android.fitracker.utils;

import android.text.Editable;
import android.text.TextWatcher;

public interface StudentTextWatcher extends TextWatcher {
    @Override
    default void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    default void afterTextChanged(Editable editable) {

    }
}
