package com.sagiKor1193.android.fitracker;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewDataActivity extends AppCompatActivity {
    private final String TAG = "ViewDataActivity";
    StudentAdapter adapter;
    private ListView listView;
    private EditText inputSearch;
    private Context mCtx;
    private final int PHONE_LEN = 10;
    private final int NO = 0;
    private final int YES = 1;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        checkFilePermissions();
        linkObjects();
        addInputSearch();

        final ArrayList<Student> studentsList = new ArrayList<>();
        refreshList( studentsList );
        adapter = new StudentAdapter(this , studentsList);

        listenToUser();
        listView.setAdapter(adapter);

    }

    private void linkObjects() {
        mCtx = this;
        listView = findViewById(R.id.list_view);
        inputSearch = findViewById(R.id.search_input);
    }

    private void listenToUser() {

        //Update this student
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent dataEnter = new Intent(mCtx, AddStudentFromResultsActivity.class);
                dataEnter.putExtra("student",  (Student) parent.getItemAtPosition(position));
                startActivity(dataEnter);

            }
        });

        //Send SMS to this student
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Student curr = (Student) parent.getItemAtPosition(position);

                //In case we don't have student phone number.
                Log.d(TAG, "phone: " + "\'"+curr.getPhoneNumber() + "\'" );
                Log.d(TAG, "phone length: " + curr.getPhoneNumber().length());
                if( curr.getPhoneNumber() == null || curr.getPhoneNumber().length() <= PHONE_LEN ) {
                    popToast("Student doesn't have phone number");
                    return true;
                }


                //else continue with the sending.
                //ask user which total score he would like to send
                final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Send SMS with aerobic result?");
                String[] answers =  {"No", "Yes"};
                builder.setItems(answers, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case NO: sendTotScoreWithOutAerobic();
                                break;
                            case YES: sendTotScore();
                                break;
                        }
                    }
                    //SENDING WITHOUT AEROBIC
                    private void sendTotScoreWithOutAerobic() {
                        Uri uri = Uri.parse("smsto:" +curr.getPhoneNumber());
                        Intent smsIntent = new Intent(Intent.ACTION_SEND, uri);
                        smsIntent.setType("text/plain");
                        smsIntent.putExtra("address",curr.getPhoneNumber());

                        String txt = "שלום, הציון שלך במבחן אות הכושר ללא אירובי הוא: " +
                                curr.getTotScoreWithoutAerobic()+" (זהו לא ציון סופי)";
                        smsIntent.putExtra(Intent.EXTRA_TEXT,txt);

                        Log.d(TAG,"Long Click " + smsIntent.toString());
                        try {
                            startActivity(smsIntent);
                        }catch (ActivityNotFoundException e){
                            Log.d(TAG, e.getMessage());
                        }
                    }
                    //SENDING WITH AEROBIC
                    private void sendTotScore() {
                        Uri uri = Uri.parse("smsto:" +curr.getPhoneNumber());
                        Intent smsIntent = new Intent(Intent.ACTION_SEND, uri);
                        smsIntent.setType("text/plain");
                        smsIntent.putExtra("address",curr.getPhoneNumber());

                        String txt = "שלום, הציון שלך במבחן אות הכושר הוא: " +
                                curr.getTotalScore()+" (זהו לא ציון סופי)";
                        smsIntent.putExtra(Intent.EXTRA_TEXT,txt);

                        Log.d(TAG,"Long Click " + smsIntent.toString());
                        try {
                            startActivity(smsIntent);
                        }catch (ActivityNotFoundException e){
                            Log.d(TAG, e.getMessage());
                        }
                    }
                });
                builder.show();

                return false;

            }
        });

    }

    public void refreshList(ArrayList<Student> studentsList) {
        int length = MainActivity.dbHandler.getRowsNum();
        for(int i = 0; i < length;  i++){
            studentsList.add( MainActivity.dbHandler.getCurrentRow(i) );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.SEND_SMS");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
    private void addInputSearch() {

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //AUTO-GENERATED
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ViewDataActivity.this.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //AUTO-GENERATED
            }
        });

    }

    private void popToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
