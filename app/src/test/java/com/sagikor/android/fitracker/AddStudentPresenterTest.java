package com.sagikor.android.fitracker;
import com.sagikor.android.fitracker.ui.contracts.AddStudentActivityContract;
import com.sagikor.android.fitracker.ui.presenter.AddStudentActivityPresenter;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class AddStudentPresenterTest {

    @Test
    public void testNameInput(){
        AddStudentActivityContract.Presenter presenter =new AddStudentActivityPresenter();
        Set<String> validMap = getValidINameInput();
        Set<String> invalidMap = getInvalidNameInput();
        //valid names testing
        for(String str : validMap){
            Assert.assertTrue("'"+str+"'",presenter.isValidName(str));
        }
        //invalid name testing
        for(String str : invalidMap){
            Assert.assertFalse("'"+str+"'",presenter.isValidName(str));
        }

    }

    @Test
    public void testPhoneNoInput(){
        AddStudentActivityContract.Presenter presenter =new AddStudentActivityPresenter();
        Set<String> validMap = getValidPhoneInput();
        Set<String> invalidMap = getInvalidPhoneInput();
        //valid phone testing
        for(String str : validMap){
            Assert.assertTrue("'"+str+"'",presenter.isValidPhoneNo(str));
        }
        //invalid phone testing
        for(String str : invalidMap){
            Assert.assertFalse("'"+str+"'",presenter.isValidPhoneNo(str));
        }
    }



    private Set<String> getValidPhoneInput() {
        Set<String> set = new HashSet<>();
        set.add("1234567899");
        set.add("0501234567");
        set.add("0109874565");
        set.add("0011992284");
        set.add("2058475928");
        return set;
    }

    private Set<String> getInvalidPhoneInput() {
        Set<String> set = new HashSet<>();
        set.add("Andre Beckman");
        set.add("0");
        set.add("01");
        set.add("012");
        set.add("012 Maybee");
        set.add("0123");
        set.add("01234");
        set.add("012345");
        set.add("0123456");
        set.add("01234567");
        set.add("01234568");
        set.add("0123456h");
        set.add("20123456h");
        set.add("!20123456h");
        return set;
    }

    private Set<String> getValidINameInput() {
        Set<String> set = new HashSet<>();
        set.add("Andre Beckman");
        set.add("Lura Talamantes");
        set.add("Vida Suddeth");
        set.add("Gil Brackins");
        set.add("Nancey Maybee");
        set.add("Bethanie Kost");
        set.add("Rhoda Helget");
        set.add("Adrian Walt");
        set.add("Alane Eleby");
        set.add("Earnest Shor");
        set.add("Sheilah Chatterton");
        set.add("Beula Buczek");
        set.add("Darryl Faust");
        set.add("Rosalyn Gayden");
        set.add("Dusty Primavera");
        set.add("Towanda Maynard");
        set.add("Gabriele Stanford");
        set.add("Filomena Lumsden");
        set.add(" Florrie Rosch");
        set.add("Erik Pinkson");
        set.add("Savanna Whidbee");
        set.add("Jeffie Prevo");
        set.add("Mavis Roosevelt");
        set.add("Joie Pawlak");
        set.add("שלום שלום");
        set.add("זוהר אביב");
        set.add("עדן שמואל");
        set.add("עידן ארונוביץ'");
        set.add("בן שלח");
        //set.add("אור כהן");
        set.add("רינת בר אור");
        set.add("שלומית ישככר");
        set.add("שושנה הדס");
        set.add("אחינועם אורן");
        set.add("נעה שלם");
        set.add("אורנית שדה");
        return set;
    }

    private Set<String> getInvalidNameInput() {
        Set<String> set = new HashSet<>();
        set.add("!Andre Beckman");
        set.add("Lura !Talamantes");
        set.add("Vida Suddeth!");
        set.add("1Gil Brackins");
        set.add("Nancey1 Maybee");
        set.add("Bethanie Kost1");
        set.add(".Rhoda Helget");
        set.add("Adrian. Walt");
        set.add("Alane Eleby.");
        set.add("#Earnest Shor");
        set.add("Sheilah# Chatterton");
        set.add("Beula Buczek#");
        set.add("[Darryl Faust");
        set.add("Rosalyn [Gayden");
        set.add("Dusty Primavera[");
        set.add("`Towanda Maynard");
        set.add("Gabriele @Stanford");
        set.add("Filomena <>Lumsden");
        set.add("<Florrie Rosch");
        set.add("Erik >Pinkston");
        set.add("?Savanna Whidbee");
        set.add("Jeffie% Prevo");
        set.add("Mavis* Roosevelt");
        set.add("very very very very very very very very very very very very long name");
        set.add("שם מאד מאד מאד מאד מאד מאד מאד מאד מאד מאד מאד מאד מאד ארוך");
        set.add("");
        set.add(null);
        set.add("שלום שלום@");
        set.add("זוהר! אביב");
        set.add("עדן3 שמואל");
        set.add("עידן$ ארונוביץ'");
        set.add("בן >שלח");
        set.add("אור} כהן");
        set.add("רינת בר{ אור");
        set.add("שלומית- ישככר");
        set.add("שושנה+ הדס");
        set.add("אחינועם^ אורן");
        set.add("נעה ,שלם");
        set.add("אורנית #שדה");
        return set;
    }
}
