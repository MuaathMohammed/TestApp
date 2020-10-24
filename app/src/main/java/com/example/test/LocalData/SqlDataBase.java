package com.example.test.LocalData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.test.Items.Constants;
import com.example.test.Items.NewsDAO;
import com.example.test.Model.NewsItems;

import java.util.ArrayList;

public class SqlDataBase implements NewsDAO {

    private DBHelper databaseHelper;

    public SqlDataBase(Context context){
        databaseHelper = new DBHelper(context);
    }

    @Override
    public long insert(NewsItems news) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NEWS_ID, news.getNewsId());
        contentValues.put(Constants.UID, news.getUserID());
        contentValues.put(Constants.USER_NAME, news.getUserName());
        contentValues.put(Constants.USER_IMAGE, news.getUserImage());
        contentValues.put(Constants.UP_LOAD_DATE, news.getNewsDate());
        contentValues.put(Constants.NEWS_TITLE, news.getNewsTitle());
        contentValues.put(Constants.NEWS_DESCRIPTION, news.getNewsDescription());
        contentValues.put(Constants.NEWS_IMAGE, news.getImage());
        contentValues.put(Constants.LONGITUDE, news.getLongitude());
        contentValues.put(Constants.LATITUDE, news.getLatitude());
        return db.insertWithOnConflict(Constants.DATABASE_TABLE_NAME, null, contentValues,SQLiteDatabase.CONFLICT_REPLACE);

    }

    @Override
    public ArrayList<NewsItems> getAll() {
        ArrayList<NewsItems> newsList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {Constants.NEWS_ID, Constants.UID,Constants.USER_NAME,Constants.USER_IMAGE,Constants.UP_LOAD_DATE
                ,Constants.NEWS_TITLE,Constants.NEWS_DESCRIPTION,Constants.NEWS_IMAGE,Constants.LONGITUDE,Constants.LATITUDE};

        Cursor cursors = db.query(Constants.DATABASE_TABLE_NAME, columns, null, null, null, null, null);
        while(cursors.moveToNext()){
            int userId = cursors.getInt(cursors.getColumnIndex(Constants.UID));
            int  newsId = cursors.getInt(cursors.getColumnIndex(Constants.NEWS_ID));
            String uName = cursors.getString(cursors.getColumnIndex(Constants.USER_NAME));
            String uImage = cursors.getString(cursors.getColumnIndex(Constants.USER_IMAGE));
            String upLoadDate = cursors.getString(cursors.getColumnIndex(Constants.UP_LOAD_DATE));
            String newsTitle = cursors.getString(cursors.getColumnIndex(Constants.NEWS_TITLE));
            String newsDescription = cursors.getString(cursors.getColumnIndex(Constants.NEWS_DESCRIPTION));
            String newsImage = cursors.getString(cursors.getColumnIndex(Constants.NEWS_IMAGE));
            double longitude = Double.parseDouble(cursors.getString(cursors.getColumnIndex(Constants.LONGITUDE)));
            double latitude = Double.parseDouble(cursors.getString(cursors.getColumnIndex(Constants.LATITUDE)));

           NewsItems greeting = new NewsItems(userId, newsId, uName,uImage,upLoadDate,newsTitle,newsDescription,newsImage,longitude,latitude);
            newsList.add(greeting);
        }
        return newsList;
    }
    @Override
    public ArrayList<NewsItems> getLocations() {
        ArrayList<NewsItems> newsList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {Constants.NEWS_TITLE,Constants.LONGITUDE,Constants.LATITUDE};

        Cursor cursors = db.query(Constants.DATABASE_TABLE_NAME, columns, null, null, null, null, null);
        while(cursors.moveToNext()){
            String newsTitle = cursors.getString(cursors.getColumnIndex(Constants.NEWS_TITLE));
            double longitude = Double.parseDouble(cursors.getString(cursors.getColumnIndex(Constants.LONGITUDE)));
            double latitude = Double.parseDouble(cursors.getString(cursors.getColumnIndex(Constants.LATITUDE)));
            NewsItems greeting = new NewsItems(newsTitle,longitude,latitude);
            newsList.add(greeting);
        }
        return newsList;
    }
}
