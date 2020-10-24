package com.example.test.Items;

import com.example.test.Model.NewsItems;

import java.util.ArrayList;

public interface NewsDAO {

    public long insert(NewsItems news);

    public ArrayList<NewsItems> getAll();
    ArrayList<NewsItems> getLocations();
}
