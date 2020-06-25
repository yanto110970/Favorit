package com.dicoding.favorit;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.favorit.broadreceiver.JobReceiver;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String NAME;
    private String EMAIL;
    private String USER;
    private String TOKEN;
    private String JOB ;
    private final String DEFAULT_VALUE = "Tidak Ada";

    private EditTextPreference namePreference;
    private EditTextPreference emailPreference;
    private EditTextPreference userPreference;
    private EditTextPreference tokenPreference;
    private CheckBoxPreference jobPreference;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.setting);
        init();
        setSummaries();
    }

    /*
    Inisiasi preferences
     */
    private void init() {

        NAME = getResources().getString(R.string.key_name);
        EMAIL = getResources().getString(R.string.key_email);
        USER = getResources().getString(R.string.key_githubuser);
        TOKEN = getResources().getString(R.string.key_token);
        JOB = getResources().getString(R.string.key_job);

        namePreference = (EditTextPreference) findPreference(NAME);
        emailPreference = (EditTextPreference) findPreference(EMAIL);
        userPreference = (EditTextPreference) findPreference(USER);
        tokenPreference = (EditTextPreference) findPreference(TOKEN);
        jobPreference = (CheckBoxPreference) findPreference(JOB);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(NAME)) {
            namePreference.setSummary(sharedPreferences.getString(NAME, DEFAULT_VALUE));
        }

        if (key.equals(EMAIL)) {
            emailPreference.setSummary(sharedPreferences.getString(EMAIL, DEFAULT_VALUE));
        }

        if (key.equals(USER)) {
            userPreference.setSummary(sharedPreferences.getString(USER, DEFAULT_VALUE));
        }

        if (key.equals(TOKEN)) {
            tokenPreference.setSummary(sharedPreferences.getString(TOKEN, DEFAULT_VALUE));
        }

        if (key.equals(JOB)) {
            jobPreference.setChecked(sharedPreferences.getBoolean(JOB, false));
            SharedPreferences sh = getPreferenceManager().getSharedPreferences();
            if (sh.getBoolean(JOB, false)) {
                String repeatTime = "09:00";
                String repeatMessage = "Lets find user on Githhub";
                JobReceiver.setRepeatingAlarm(getActivity(), JobReceiver.TYPE_REPEATING,
                        repeatTime, repeatMessage);
            }else{
                JobReceiver.cancelAlarm(getActivity(), JobReceiver.TYPE_REPEATING);
            }
        }
    }

    private void setSummaries() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        namePreference.setSummary(sh.getString(NAME, DEFAULT_VALUE));
        emailPreference.setSummary(sh.getString(EMAIL, DEFAULT_VALUE));
        userPreference.setSummary(sh.getString(USER, DEFAULT_VALUE));
        tokenPreference.setSummary(sh.getString(TOKEN, DEFAULT_VALUE));
        jobPreference.setChecked(sh.getBoolean(JOB, false));
    }
}
