package com.manateams.android.manateams.util;

public class Constants {
    public static final String[] COLORS = {"#e91e63", "#e51c23", "#ff9800", "#8bc34a", "#259b24", "#009688", "#5677fc", "#3f61b5", "#607d8b"};
    public static final String EXTRA_GRADES = "grades";
    public static final String EXTRA_COURSEINDEX = "courseIndex";
    public static final String EXTRA_COURSETITLE = "courseTitle";
    public static final String EXTRA_COURSEID = "courseID";
    public static final String FILE_COURSES = "courses";
    public static final String FILE_BASE_CLASSGRADES = "classgrades";
    public static final String FILE_BASE_DATAPOINTS = "datapoints";
    public static final String PREFERENCE_LASTUPDATED = "lastupdated";
    // Time Configuration
    public static final long INTERVAL_UPDATE_FULL = 24*60*60*1000; //1 day
    public static final long INTERVAL_EXPIRE_COOKIE = 25*60*1000; //1 day
    public static final long INTERVAL_MOBILE_UPDATE_ASSIGNMENT = 2*60*60*1000 ; //2 hours
    public static final long INTERVAL_MOBILE_UPDATE_AVERAGE = 1*60*60*1000 ; //1 hour
    public static final long INTERVAL_GRADE_SCRAPE = 20 * 1000 * 60; // 20 minutes

}
