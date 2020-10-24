package com.example.test.Model;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.test.Items.Constants.NEWS_ID;
import static com.example.test.Items.Constants.NEWS_IMAGE;
import static com.example.test.Items.Constants.NEWS_TITLE;
import static com.example.test.Items.Constants.UID;
import static com.example.test.Items.Constants.UP_LOAD_DATE;
import static com.example.test.Items.Constants.USER_IMAGE;

public class NewsItems {
    int NewsId;
    String UserImage;
    String Image;
    String UserName;
    String NewsDate;
    String NewsTitle;
    String NewsDescription;
    int UserID;
    double Longitude;
    double Latitude;
    public NewsItems(){}
    public NewsItems(String newsTitle,double longitude, double latitude){
        NewsTitle=newsTitle;
        Longitude = longitude;
        Latitude = latitude;

    }
      public NewsItems(int newsId,int userID,String userName, String userImage, String newsDate,String newsTitle,String newsDescription,String image,double longi,double lati)
    {
        NewsId=newsId;
        UserImage = userImage;
        Image = image;
        UserID=userID;
        UserName = userName;
        NewsDate = newsDate;
        NewsTitle = newsTitle;
        NewsDescription = newsDescription;
        Longitude=longi;
        Latitude=lati;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }
    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getNewsId() {
        return NewsId;
    }

    public void setNewsId(int newsId) {
        NewsId = newsId;
    }
    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNewsDate() {
        return NewsDate;
    }

    public void setNewsDate(String newsDate) {
        NewsDate = newsDate;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return NewsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        NewsDescription = newsDescription;
    }

}
