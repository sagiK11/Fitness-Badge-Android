package com.sagiKor1193.android.fitracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    static MyDBHandler dbHandler;
    private int dbVersion = 1;
    private final int MAX_PRECENTAGE = 100;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private int prog;
    ProgressBar pBar_1;
    ProgressBar pBar_2;
    TextView numberOfStudents;
    TextView numberOfStudentsFinished;
    TextView progBarPercentage;
    BottomNavigationView bottomNavView;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new MyDBHandler(this, null, null, dbVersion);
        linkObjects();
        bottomNavListener();
        calPercentage();

        //SHOWING IN MAIN ACTIVITY
        pBar_2.setProgress(prog);
        numberOfStudentsFinished.setText(String.valueOf(dbHandler.studentsCompleted()));
        progBarPercentage.setText(String.valueOf( prog ) + "%");
        numberOfStudents.setText(String.valueOf( dbHandler.getRowsNum() ));
    }

    private void calPercentage() {
        try {
            prog = dbHandler.studentsCompleted() * MAX_PRECENTAGE / dbHandler.getRowsNum();
        }catch (RuntimeException e){
            Log.v(TAG,"divide by zero - empty DB.");
            prog = 0;
        }

    }

    private void linkObjects() {
        pBar_1 = (ProgressBar) findViewById(R.id.pBar_1);
        pBar_2 =  (ProgressBar) findViewById(R.id.main_activity_prog_bar_id);
        numberOfStudents = (TextView) findViewById(R.id.totnumber_of_students_to_display_id);
        numberOfStudentsFinished = (TextView) findViewById(R.id.number_of_students_to_display_id);
        progBarPercentage = (TextView) findViewById(R.id.prog_bar_precentage);
        bottomNavView = (BottomNavigationView) findViewById(R.id.button_nav_id);
    }

    public void bottomNavListener(){
        //making the text always on.
        BottomNavigationViewHelper helper = new BottomNavigationViewHelper();
        helper.removeShiftMode(bottomNavView);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch ( item.getItemId() ){
                    case R.id.action_import : importData();
                        break;
                    case R.id.action_export :exportData();
                        break;
                    case R.id.action_view: watchData();
                        break;
                    case R.id.action_add : addStudent();
                        break;
                }
                return true;
            }
        });

    }

    public void addStudent() {
        startActivity( new Intent(this, AddStudentActivity.class));
        numberOfStudentsFinished.setText(String.valueOf( dbHandler.studentsCompleted()));
    }

    public static void printDatabase() {
        String dbString = dbHandler.databaseToString();
        System.out.println("\n" + dbString + "\n");
    }

    public void watchData() {
        startActivity(new Intent(this, ViewDataActivity.class));
    }

    public void exportData() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            MultiTasking task = new MultiTasking();
            task.execute();
        }

    }

    public void importData(){
        startActivity( new Intent(this,UploadExcelFileActivity.class));
    }

    public void setting(View view){
        startActivity(new Intent( this, Setting.class));
    }

    public static MyDBHandler getDbHandler() {
        return dbHandler;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class MultiTasking extends AsyncTask<String, String, String> {
        //NUMBER OF COLUMNS IN EXCEL-FILE
        private final int rLen = 13;

        @Override
        protected void onPreExecute() {
            pBar_1.setVisibility(View.VISIBLE);
            pBar_1.setProgress(0);
            pBar_1.setMax(100);
        }

        @Override
        protected void onPostExecute(String success) {
           super.onPostExecute(success);
            pBar_1.setVisibility(View.GONE);

           Context context = getApplicationContext();
           CharSequence text_1 = "Exporting to Excel file completed";
           CharSequence text_2 = "Opening email";
           Toast toast_1 = Toast.makeText(context , text_1, Toast.LENGTH_SHORT);
            toast_1.show();
            Toast toast_2 = Toast.makeText(context , text_2, Toast.LENGTH_SHORT);
            toast_2.show();


        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected String doInBackground(final String... strings) {
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file = new File(exportDir, "ExcelFile.csv");
            try {

                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

                //data

                ArrayList<String> studentsData= new ArrayList<>();
                for(int i = 0; i < MainActivity.getDbHandler().getRowsNum(); i++) {

                    studentsData.add(MainActivity.dbHandler.getCurrentRow(i).getSName());
                    studentsData.add(MainActivity.dbHandler.getCurrentRow(i).getSClass());

                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getAeroScore()) );
                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getAeroRes()) );

                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getAbsScore()) );
                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getAbsResult()) );

                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getHandsScore()) );
                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getHandsResult()) );

                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getCubesScore()) );
                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getCubesResult()) );

                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getJumpScore()) );
                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getJumpResult()) );

                    studentsData.add(String.valueOf( MainActivity.dbHandler.getCurrentRow(i).getTotalScore()) );
                    pBar_1.incrementProgressBy(1);
                }

                //Headers
                String header[] ={"שם", "כתה", "אירובי-תוצאה", "אירובי-ציון",
                        "בטן-תוצאה","בטן-ציון", "ידיים-תוצאה", "ידיים-ציון", "קוביות-תוצאה", "קוביות-ציון"
                        ,"קפיצה-תוצאה","קפיצה-ציון", "ציון סופי"};
                csvWrite.writeNext(header);
                for(int q = 0, w =1, e =2, r=3,t=4,y=5,u=6,i=7,o=8,p=9,a=10,s=11,d=12;
                    q < studentsData.size();
                    q += rLen, w += rLen, e += rLen, r += rLen, t += rLen, y += rLen, u += rLen,
                            i += rLen, o += rLen,p += rLen,a += rLen,s += rLen,d += rLen) {

                    String arrStr[] =
                            {studentsData.get(q), studentsData.get(w), studentsData.get(e),
                             studentsData.get(r), studentsData.get(t), studentsData.get(y),
                             studentsData.get(u), studentsData.get(i), studentsData.get(o),
                             studentsData.get(p), studentsData.get(a), studentsData.get(s),
                             studentsData.get(d)};
                    csvWrite.writeNext(arrStr);
                }


                csvWrite.close();
                //Email sending
                Intent sendToMail = new Intent(Intent.ACTION_SENDTO);
                sendToMail.setData(Uri.parse("mailto:"));

                sendToMail.putExtra(Intent.EXTRA_SUBJECT, "Students Test Grades");
                sendToMail.putExtra(Intent.EXTRA_STREAM , Uri.fromFile(file.getAbsoluteFile()));

                if(sendToMail.resolveActivity(getPackageManager()) != null)
                    startActivity(sendToMail);
                return null;
            }
            catch (IOException e){
                Log.e("MainActivity", e.getMessage(), e);
                return null;
            }



        }
    }
    // Making the text in the bottom navigation bar always on.
    private class BottomNavigationViewHelper {

        @SuppressLint("RestrictedApi")
        void removeShiftMode(BottomNavigationView view) {

            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);

            try {

                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);

                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }

            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }

        }
    }



}






