package com.javierarboleda.newyorktimesarticlesearch.activities;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.javierarboleda.newyorktimesarticlesearch.R;
import com.javierarboleda.newyorktimesarticlesearch.databinding.ActivitySettingsBinding;
import com.javierarboleda.newyorktimesarticlesearch.utils.AppConstants;
import com.javierarboleda.newyorktimesarticlesearch.utils.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener,
        AdapterView.OnItemSelectedListener {

    private final String TAG = SettingsActivity.class.getName();

    private ActivitySettingsBinding binding;

    private String mBeginDateQuery;
    private String mBeginDateLabel;
    private Set<String> mNewsDeskValues;
    private boolean mSortNewest;

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
        binding.spinner.setOnItemSelectedListener(this);

        loadFiltersFromSharedPreferences();
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear,
                          int dayOfMonth) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
        Date date = cal.getTime();

        mBeginDateQuery = Util.getFormattedDateForQuery(date);
        mBeginDateLabel = Util.getFormattedDate(date);

        binding.tvBeginDate.setText(mBeginDateLabel);
    }

    public void pickBeginDate(View view) {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(this);
        cdp.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mSortNewest = i == 0;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void saveFiltersToSharedPreferences(View view) {

        if (mNewsDeskValues != null) {
            if (binding.cbArts.isChecked()) {
                mNewsDeskValues.add(AppConstants.ARTS_VALUE);
            } else {
                mNewsDeskValues.remove(AppConstants.ARTS_VALUE);
            }

            if (binding.cbFashionAndStyle.isChecked()) {
                mNewsDeskValues.add(AppConstants.FASHION_AND_STYLE_VALUE);
            } else {
                mNewsDeskValues.remove(AppConstants.FASHION_AND_STYLE_VALUE);
            }

            if (binding.cbSports.isChecked()) {
                mNewsDeskValues.add(AppConstants.SPORTS_VALUE);
            } else {
                mNewsDeskValues.remove(AppConstants.SPORTS_VALUE);
            }
        }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(AppConstants.BEGIN_DATE_QUERY_KEY_NAME, mBeginDateQuery);
        editor.putString(AppConstants.BEGIN_DATE_LABEL_KEY_NAME, mBeginDateLabel);
        editor.putBoolean(AppConstants.SORT_NEWEST_KEY_NAME, mSortNewest);
        editor.putStringSet(AppConstants.NEWS_DESK_VALUES_KEY_NAME, mNewsDeskValues);
        editor.commit();
    }

    private void loadFiltersFromSharedPreferences() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        mBeginDateQuery = sharedPref.getString(AppConstants.BEGIN_DATE_QUERY_KEY_NAME, null);
        mBeginDateLabel = sharedPref.getString(AppConstants.BEGIN_DATE_LABEL_KEY_NAME, null);

        mSortNewest = sharedPref.getBoolean(AppConstants.SORT_NEWEST_KEY_NAME, true);

        Set<String> newsDeskSet = new HashSet<>();
        mNewsDeskValues = sharedPref.getStringSet(AppConstants.NEWS_DESK_VALUES_KEY_NAME,
                newsDeskSet);

        binding.spinner.setSelection(mSortNewest ? 0 : 1);

        if (mBeginDateLabel != null) {
            binding.tvBeginDate.setText(mBeginDateLabel);
        }

        // set up News Desk Values checkboxes
        if (mNewsDeskValues.contains(AppConstants.ARTS_VALUE)) {
            binding.cbArts.setChecked(true);
        }
        if (mNewsDeskValues.contains(AppConstants.FASHION_AND_STYLE_VALUE)) {
            binding.cbFashionAndStyle.setChecked(true);
        }
        if (mNewsDeskValues.contains(AppConstants.SPORTS_VALUE)) {
            binding.cbSports.setChecked(true);
        }
    }

    public void clearFilters(View view) {
        mBeginDateQuery = null;
        mBeginDateLabel = null;
        mNewsDeskValues = null;
        mSortNewest = true;

        binding.tvBeginDate.setText(getString(R.string.clickToAddDate));
        binding.cbArts.setChecked(false);
        binding.cbFashionAndStyle.setChecked(false);
        binding.cbSports.setChecked(false);

        saveFiltersToSharedPreferences(null);
        loadFiltersFromSharedPreferences();
    }
}
