package com.javierarboleda.newyorktimesarticlesearch.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.javierarboleda.newyorktimesarticlesearch.R;
import com.javierarboleda.newyorktimesarticlesearch.databinding.ActivitySettingsBinding;
import com.javierarboleda.newyorktimesarticlesearch.utils.Util;

import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener{

    private final String TAG = SettingsActivity.class.getName();

    private ActivitySettingsBinding binding;

    private String mBeginDate;
    private String mSortOrder;
    private String[] mNewsDeskValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        setSupportActionBar(binding.toolbar);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sortArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.spinner.setAdapter(adapter);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear,
                          int dayOfMonth) {

        // 20160112 yyyymmdd

        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
        Date date = cal.getTime();

        binding.tvBeginDate.setText(Util.getFormattedDate(date));

    }

    public void pickBeginDate(View view) {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(this);
        cdp.show(getSupportFragmentManager(), TAG);
    }
}
