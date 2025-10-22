package com.example.a25782__midterm.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    
    private static final String PREF_NAME = "AcademicManagementPrefs";
    private static final String KEY_LAST_SCREEN = "last_screen";
    private static final String KEY_THEME_DARK = "theme_dark";
    private static final String KEY_SHOW_REMINDERS = "show_reminders";
    private static final String KEY_FIRST_LAUNCH = "first_launch";
    private static final String KEY_LAST_UPDATE = "last_update";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save the last opened screen
    public void saveLastScreen(String screenName) {
        editor.putString(KEY_LAST_SCREEN, screenName);
        editor.apply();
    }

    // Get the last opened screen
    public String getLastScreen() {
        return sharedPreferences.getString(KEY_LAST_SCREEN, "MainActivity");
    }

    // Save theme preference
    public void setDarkTheme(boolean isDark) {
        editor.putBoolean(KEY_THEME_DARK, isDark);
        editor.apply();
    }

    // Get theme preference
    public boolean isDarkTheme() {
        return sharedPreferences.getBoolean(KEY_THEME_DARK, false);
    }

    // Save reminders preference
    public void setShowReminders(boolean show) {
        editor.putBoolean(KEY_SHOW_REMINDERS, show);
        editor.apply();
    }

    // Get reminders preference
    public boolean shouldShowReminders() {
        return sharedPreferences.getBoolean(KEY_SHOW_REMINDERS, true);
    }

    // Check if this is the first launch
    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true);
    }

    // Mark that the app has been launched
    public void setFirstLaunchComplete() {
        editor.putBoolean(KEY_FIRST_LAUNCH, false);
        editor.apply();
    }

    // Save last update timestamp
    public void saveLastUpdate(long timestamp) {
        editor.putLong(KEY_LAST_UPDATE, timestamp);
        editor.apply();
    }

    // Get last update timestamp
    public long getLastUpdate() {
        return sharedPreferences.getLong(KEY_LAST_UPDATE, 0);
    }

    // Clear all preferences
    public void clearAllPreferences() {
        editor.clear();
        editor.apply();
    }

    // Save a custom string value
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    // Get a custom string value
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // Save a custom boolean value
    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Get a custom boolean value
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Save a custom integer value
    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    // Get a custom integer value
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }
}


