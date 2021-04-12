package com.sagikor.android.fitracker;


import com.sagikor.android.fitracker.ui.contracts.AddClassesActivityContract;
import com.sagikor.android.fitracker.ui.presenter.AddClassesActivityPresenter;
import com.sagikor.android.fitracker.utils.AppExceptions;


import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class AddClassesPresenterTest {

    @Test
    public void testClassInput() {
        AddClassesActivityContract.Presenter presenter = new AddClassesActivityPresenter();
        Set<String> validMap = getValidClassInput();
        Set<String> invalidMap = getInvalidClassInput();
        //valid names testing
        for (String str : validMap) {
            try {
                presenter.checkValidInput(str);
            } catch (AppExceptions.Input e) {
                System.out.printf("exception thrown for: '%s'%n", str);
            }

        }
        //invalid name testing
        for (String str : invalidMap) {
            boolean isExceptionThrown = false;
            try {
                presenter.checkValidInput(str);
            } catch (AppExceptions.Input e) {
                isExceptionThrown = true;
            }
            if (!isExceptionThrown) {
                System.out.printf("exception wasn't not thrown for: '%s'%n", str);
            }
        }
    }

    private Set<String> getInvalidClassInput() {
        Set<String> set = new HashSet<>();
        set.add("!יב");
        set.add("");
        set.add(null);
        set.add("very very very long name for class");
        set.add(".יב");
        set.add("(r");
        set.add("+2f");
        set.add("?A2");
        set.add("%222222");
        set.add("2");
        set.add("#21sd");
        set.add("\"");
        return set;
    }


    private Set<String> getValidClassInput() {
        Set<String> set = new HashSet<>();
        set.add("י\"א 1");
        set.add("י\"א 2");
        set.add("י\"ב 1");
        set.add("י\"ב 2");
        set.add("י\"ב1");
        set.add("י 1");
        set.add("י 2");
        set.add("י\"א ");
        set.add("A 3");
        set.add("A3");
        set.add("א 4");
        set.add("ב 4");
        set.add("ג 4");
        set.add("ד 4");
        set.add("ו 4");
        set.add("ז 4");
        set.add("ח 4");
        set.add("ט 4");
        set.add("י 4");
        set.add("כ 4");
        set.add("ל 4");
        set.add("מ 4");
        set.add("נ 4");
        set.add("ס 4");
        set.add("ע 4");
        set.add("פ 4");
        set.add("צ 4");
        set.add("ק 4");
        set.add("ר 4");
        set.add("ש 4");
        set.add("יא 4");
        set.add("יב 4");
        set.add("יג 4");
        set.add("ט4");
        set.add("י4");
        set.add("יא4");
        set.add("יב4");
        return set;
    }
}
