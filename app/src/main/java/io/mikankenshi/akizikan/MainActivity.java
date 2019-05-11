package io.mikankenshi.akizikan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends FragmentActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    static final int num = 1;
    /*private TextView[] textDate = new TextView[num];
    private TextView[] textTime = new TextView[num];
    */
    private TextView textView1, textView2,free_time;
    String text1, text2;
    SharedPreferences pref;
    private boolean isFirst = true;

    String str1= "";
    String str2 = "";
   int hour1 = 0;
   int hour2 = 0;
   int minute1 = 0;
   int minute2 = 0;
   int hour_sum =0;
   int minute_sum =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        free_time = findViewById(R.id.free_time);

        pref = getSharedPreferences("pref_date", MODE_PRIVATE);
        textView1.setText(pref.getString("key_date", ""));
        textView2.setText(pref.getString("key_time", ""));
        //free_time.setText(pref.getString("key_free",""));

        /*  for (int i = 0; i < num; i++) {
            textDate[i] = findViewById(R.id.textDate + i);
            textTime[i] = findViewById(R.id.(textView + i));
        }*/
    }

    //Date Picker
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        SharedPreferences.Editor editor = pref.edit();
//日の保存、表示
        String str = String.format(Locale.US, "%d/%d/%d", year, monthOfYear + 1, dayOfMonth);

        editor.putString("key_date", str);
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
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        int[][] timec = new int[num][2];

        if (isFirst) {
            SharedPreferences.Editor editor = pref.edit();

           // str1 = String.format(Locale.US, "%02d:%02d", hourOfDay, minute); //format intからStringにしている
            hour1 = hourOfDay;
            minute1 = minute;
            editor.putString("key_time", str1);
            editor.commit();


            DialogFragment newFragment = new TimePick();
            newFragment.show(getSupportFragmentManager(), "timePicker");


            isFirst = false;
        } else {
            hour2 = hourOfDay;
            minute2 = minute;

            int hourc = hour2-hour1;
            int minutec = minute2-minute1;

            if(minutec <0){
                minutec=minutec+60;
                hourc = hourc - 1;
            }else if (minutec >= 60) {
                minutec = minutec - 60;
                hourc = hourc + 1;
            }
            hour_sum += hourc;
            minute_sum +=  minutec;

            isFirst = true;
        }

        String str = String.valueOf(String.format("%02d", hour_sum)) + ":" + String.valueOf(String.format("%02d", minute_sum));

        free_time.setText(str);

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

}


//データを保存する  done!
//日付と時間を結びつける->時間を取ったら日に返してあげればいいのでは
//時間1個目を選択したら、時間2個目選択画面へ遷移。backボタンも欲しい。
//addボタンでその日の時間を加える
//okを押したとの動きをカスタムしたい。