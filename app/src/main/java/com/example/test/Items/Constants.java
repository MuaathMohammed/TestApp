package com.example.test.Items;

public class Constants {
    public static final String DATABASE_NAME="News.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE_NAME="news";
    public static final String COLUMN_KEY_ID = "_id", NEWS_ID ="NewsId",UID="UID";
    public static final String NEWS_TITLE = "NewsTitle";
    public static final String NEWS_DESCRIPTION = "NewsDescription";
    public static final String USER_NAME = "UName";
    public static final String USER_IMAGE = "UImage";
    public static final String NEWS_IMAGE = "NewsImage";
    public static final String UP_LOAD_DATE = "UploadDate";
    public static final String LONGITUDE = "Longitude";
    public static final String LATITUDE = "Latitude";
    public static final String CREATE_TABLE_NEWS ="CREATE TABLE "+DATABASE_TABLE_NAME+"( "+ NEWS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            UID+" INTEGER,"+ NEWS_TITLE +" VARCHAR(255),"+ USER_NAME +" VARCHAR(255),"+ NEWS_DESCRIPTION
            +" VARCHAR(255),"+ NEWS_IMAGE +" VARCHAR(255),"+ USER_IMAGE +" VARCHAR(255),"+ UP_LOAD_DATE+" VARCHAR(255),"+ LONGITUDE
            +" VARCHAR(255),"+ LATITUDE+" VARCHAR(255));";
    public static final String DROP_TABLE_NEWS = "DROP TABLE IF EXISTS "+DATABASE_TABLE_NAME;

}
