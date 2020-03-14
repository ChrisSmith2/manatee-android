package com.manateams.android.manateams.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.manateams.android.manateams.R;
import com.manateams.android.manateams.util.DataManager;
import com.manateams.scraper.data.Course;


public class NicknameFragment extends PreferenceFragmentCompat {
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.nickname_settings);

        PreferenceScreen prefScreen = getPreferenceScreen();
        Context context = prefScreen.getContext();

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.length() >= 13 && key.substring(0, 13).equals("pref_nickname")) {
                    EditTextPreference pref = (EditTextPreference) findPreference(key);
                    pref.setSummary(prefs.getString(key, (String) pref.getSummary()));
                }
            }
        };
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);

        DataManager dataManager = new DataManager(getActivity());
        Course[] courses = dataManager.getCourseGrades();

        for (int i = 0; i < courses.length; i++) {
            String title = courses[i].title;
            EditTextPreference nicknamePref = new EditTextPreference(context);
            nicknamePref.setTitle(title);
            String nickname = dataManager.getCourseName(courses[i]);
            if (!nickname.equals(title))
                nicknamePref.setSummary(nickname);
            nicknamePref.setKey("pref_nickname_" + title);
            prefScreen.addPreference(nicknamePref);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(listener);
    }
}
