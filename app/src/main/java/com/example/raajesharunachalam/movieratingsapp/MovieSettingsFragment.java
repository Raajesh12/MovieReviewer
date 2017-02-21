package com.example.raajesharunachalam.movieratingsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by raajesharunachalam on 1/13/17.
 */

public class MovieSettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public void setPreferenceSummary(Preference preference, String stringValue){
        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setSharedPreferencesName(getContext().getString(R.string.sharedPreferencesFile));
        preferenceManager.setSharedPreferencesMode(Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.movie_settings_fragment);

        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int numberOfPreferences = prefScreen.getPreferenceCount();
        for(int i = 0; i < numberOfPreferences; i++){
            Preference currentPref = prefScreen.getPreference(i);
            if(!(currentPref instanceof CheckBoxPreference)){
                String value = sharedPreferences.getString(currentPref.getKey(), "");
                setPreferenceSummary(currentPref, value);
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(getString(R.string.colorPreference))){
            Preference preference = findPreference(key);
            String value = sharedPreferences.getString(key, "");
            setPreferenceSummary(preference, value);
        }

        else if(key.equals(getString(R.string.fontSizePreference))){
            Preference preference = findPreference(key);
            String value = sharedPreferences.getString(key, "");
            setPreferenceSummary(preference, value);
        }

    }
}
