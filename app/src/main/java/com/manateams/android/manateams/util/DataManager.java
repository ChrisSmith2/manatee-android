package com.manateams.android.manateams.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manateams.scraper.data.ClassGrades;
import com.manateams.scraper.data.Course;
import com.manateams.scraper.data.GradeValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    private Context context;
    public DataManager(Context context) {
        this.context = context;
    }

    public void setCredentials(String username, String password, String TEAMSuser, String TEAMSpass) {
        setUsername(username);
        setPassword(password);
        setTEAMSuser(TEAMSuser);
        setTEAMSpass(TEAMSpass);
    }

    public String[] getCredentials() {
        return new String[] { getUsername(), getPassword(), getStudentId(),getTEAMSuser(),getTEAMSpass() };
    }

    public boolean isFirstTimeViewingGPA() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("firstTimeGPA", true);
    }

    public boolean isFirstTimeViewingGraphs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("firstTimeGraphs", true);
    }

    public boolean isFirstTimeViewingGrades() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("firstTimeGrades", true);
    }
    public void setFirstTimeViewingGPA(boolean firstTime) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("firstTimeGPA", firstTime);
        edit.commit();
    }

    public void setFirstTimeViewingGraphs(boolean firstTime) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("firstTimeGraphs", firstTime);
        edit.commit();
    }
    public void setFirstTimeViewingGrades(boolean firstTime) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("firstTimeGrades", firstTime);
        edit.commit();
    }

    public void setUsername(String username) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("username", username);
        edit.commit();
    }

    public String getUsername() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("username", null);
    }

    public void setUserIdentification(String userIdentification) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("userIdentification", userIdentification);
        edit.commit();
    }

    public String getUserIdentification() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("userIdentification", null);
    }

    public void setTEAMSuser(String TEAMSUsername) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("TEAMSuser", TEAMSUsername);
        edit.commit();
    }

    public String getTEAMSuser() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("TEAMSuser", null);
    }
    public void setTEAMSpass(String TEAMSpass) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("TEAMSpass", TEAMSpass);
        edit.commit();
    }

    public String getTEAMSpass() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("TEAMSpass", null);
    }


    public void setPassword(String password) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("password", password);
        edit.commit();

    }

    public String getPassword() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("password", null);
    }

    public void setStudentId(String studentId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("studentId", studentId);
        edit.commit();
    }

    public String getStudentId() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("studentId", null);
    }


    public void setAverageHtml(String averageHtml) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("averagehtml", averageHtml);
        edit.commit();
    }
    public String getAverageHtml() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("averagehtml", null);
    }
    public long getOverallGradesLastUpdated() {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCE_LASTUPDATED, Context.MODE_PRIVATE);
        return preferences.getLong("overall", -1);
    }

    public void setOverallGradesLastUpdated() {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCE_LASTUPDATED, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong("overall", System.currentTimeMillis());
        edit.apply();
    }

    public long getLastFullUpdate() {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCE_LASTUPDATED, Context.MODE_PRIVATE);
        return preferences.getLong("lastfullupdate", -1);
    }

    public void setLastFullUpdate() {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCE_LASTUPDATED, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong("lastfullupdate", System.currentTimeMillis());
        edit.apply();
    }

    public long getClassGradesLastUpdated(String courseId) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCE_LASTUPDATED, Context.MODE_PRIVATE);
        return preferences.getLong(courseId, -1);
    }
    public void setCookie(String cookie){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("cookie",cookie);
        edit.putLong("cookieLastUpdated", System.currentTimeMillis());
        edit.commit();
    }
    public void invalidateCookie(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong("cookieLastUpdated", -1);
        edit.commit();
    }
    public long getCookieLastUpdated (){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getLong("cookieLastUpdated",-1);
    }
    public String getCookie(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("cookie",null);
    }

    public void setClassGradesLastUpdated(String courseId) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCE_LASTUPDATED, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong(courseId, System.currentTimeMillis());
        edit.apply();
    }

    public void setCourseGrades(Course[] courses) {
        writeToFile(Constants.FILE_COURSES, new Gson().toJson(courses));
    }

    public Course[] getCourseGrades() {
        String data = readFromFile(Constants.FILE_COURSES);
        return new Gson().fromJson(data, new TypeToken<Course[]>() {
        }.getType());
    }

    public void addCourseDatapoint(GradeValue g,String courseID){
        ArrayList<DataPoint> currentValues = getCourseDatapoints(courseID);
        if(currentValues == null){
            currentValues = new ArrayList<DataPoint>();
        }
        currentValues.add(new DataPoint(g,System.currentTimeMillis()));
        currentValues = colapseValues(currentValues);
        writeToFile(Constants.FILE_BASE_DATAPOINTS + courseID ,new Gson().toJson(currentValues));
    }
    public void deleteDatapoints(String courseID){
        writeToFile(Constants.FILE_BASE_DATAPOINTS + courseID ,new Gson().toJson(new ArrayList<DataPoint>()));
    }

    private ArrayList<DataPoint> colapseValues(ArrayList<DataPoint> grades) {
        ArrayList<DataPoint> out = new ArrayList<DataPoint>();
        Map<String,List<DataPoint>> sortedGrades = new HashMap<String,List<DataPoint>>();
        //Add each value to a map based on date
        for(DataPoint d: grades){
            Date date = new Date(d.time);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = format1.format(date);
            if(!sortedGrades.containsKey(formattedDate)){
                sortedGrades.put(formattedDate, new ArrayList<DataPoint>());
            }
            sortedGrades.get(formattedDate).add(d);
        }
        //Choose the last value from each day to display
        for (Map.Entry<String, List<DataPoint>> entry : sortedGrades.entrySet()) {
            String key = entry.getKey();
            List<DataPoint> value = entry.getValue();
            out.add(value.get(value.size()-1));
        }
        return out;
    }
    public ArrayList<DataPoint> getCourseDatapoints(String courseID){
        String data = readFromFile(Constants.FILE_BASE_DATAPOINTS + courseID);
        return new Gson().fromJson(data, new TypeToken<ArrayList<DataPoint>>(){}.getType());
    }

    public void setClassGrades(ClassGrades[] grades, String courseID) {
        writeToFile(Constants.FILE_BASE_CLASSGRADES + courseID, new Gson().toJson(grades));
    }

    public ClassGrades[] getClassGrades(String courseID) {
        String data = readFromFile(Constants.FILE_BASE_CLASSGRADES + courseID);
        return new Gson().fromJson(data, new TypeToken<ClassGrades[]>(){}.getType());
    }

    // Returns course nickname if one exists, otherwise returns course title
    public String getCourseName(Course course) {
        if (course != null) {
            String title = course.title;
            SharedPreferences defaultPrefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            String nickname = defaultPrefs.getString("pref_nickname_" + title, title);
            if (!nickname.trim().isEmpty())
                return nickname;
            return title;
        }
        return null;
    }

    private String readFromFile(String name) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(name);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            //Log.e("DataManager", "File not found: " + e.toString());
        } catch (IOException e) {
            //Log.e("DataManager", "Can not read file: " + e.toString());
        }
        return ret;
    }

    private void writeToFile(String name, String dataString) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(name, Context.MODE_PRIVATE));
            outputStreamWriter.write(dataString);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("DataManager", "File write failed: " + e.toString());
        }
    }

}
