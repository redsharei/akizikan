package io.mikankenshi.akizikan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;


public class MainActivity extends FragmentActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    static final int num = 1;
    /*private TextView[] textDate = new TextView[num];
    private TextView[] textTime = new TextView[num];
    */
    private TextView textView1, textView2;
    String text1,text2;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        pref = getSharedPreferences("pref_date", MODE_PRIVATE);
        textView1.setText(pref.getString("key_date", ""));
        textView2.setText(pref.getString("key_time", ""));
      /*  for (int i = 0; i < num; i++) {
            textDate[i] = findViewById(R.id.textDate + i);
            textTime[i] = findViewById(R.id.(textView + i));
        }*/
    }

    //Date Picker
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        SharedPreferences.Editor editor = pref.edit();

        String str = String.format(Locale.US, "%d/%d/%d", year, monthOfYear + 1, dayOfMonth);

        editor.putString("key_date",str);
        editor.commit();

        textView1.setText(str);

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePick();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    //Time Picker
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SharedPreferences.Editor editor = pref.edit();

        String str = String.format(Locale.US, "%d:%d", hourOfDay, minute);

        editor.putString("key_time",str);
        editor.commit();

        textView2.setText(str);

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }
}

//データを保存する