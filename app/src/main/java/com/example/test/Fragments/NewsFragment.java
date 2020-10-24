package com.example.test.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Adapter.NewsAdapter;
import com.example.test.Items.NewsDAO;
import com.example.test.Model.NewsItems;
import com.example.test.LocalData.SqlDataBase;
import com.example.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
    NewsDAO newsDAO;
    public static ArrayList<NewsItems> newsItems;
    public static  RecyclerView RcVData;
    NewsAdapter newsAdapter;
    private Context mContext;
    private static final String JSON_URL = "https://api.jsonbin.io/b/5f91f05fbd69750f00c25591/4";
    public NewsFragment(){}
    ProgressBar progressBar;
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        view=inflater.inflate(R.layout.news_fragment, container, false);

         progressBar = (ProgressBar) view.findViewById(R.id.pb);

        newsDAO=new SqlDataBase(getActivity());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        RcVData=view.findViewById(R.id.RcVNews);
        RcVData.setLayoutManager(staggeredGridLayoutManager);
        RcVData.setHasFixedSize(true);
        RcVData.setNestedScrollingEnabled(false);
        RcVData.setItemAnimator(new DefaultItemAnimator());
        newsItems= new ArrayList<NewsItems>();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        getActivity().registerReceiver(receiver, filter);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    //------------To Check if The Network State While the App is running !!!! need some enhancement----//
    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            Log.v(LOG_TAG, "Receieved notification about network status");
            isNetworkAvailable(context);
        }
        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if(!isConnected){
                                Log.v(LOG_TAG, "Now you are connected to Internet!");
                                isConnected = true;
                                new JSONParseTask().execute();
                            }
                            return true;
                        }
                    }
                }
            }
            Log.v(LOG_TAG, "You are not connected to Internet!");
            new JSONParseTask().execute();
            isConnected = false;
            return false;
        }
    }
    public class JSONParseTask extends AsyncTask<String, Void, String> {
        String ReturnResult;
        NewsItems news;
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... args) {
            try{
                StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray newsArray = obj.getJSONArray("news");
                                    if (newsArray == null) {
                                        ReturnResult = "Check Your Internet Connection";
                                    } else {
                                        for (int i = 0; i < newsArray.length(); i++) {
                                            JSONObject newsObj = newsArray.getJSONObject(i);
                                            int userId = newsObj.getInt("UID");
                                            int  newsId = newsObj.getInt("NewsId");
                                            String uName = newsObj.getString("UName");
                                            String uImage = newsObj.getString("UImage");
                                            String upLoadDate = newsObj.getString("UploadDate");
                                            String newsTitle = newsObj.getString("NewsTitle");
                                            String newsDescription = newsObj.getString("NewsDescription");
                                            String newsImage = newsObj.getString("NewsImage");
                                            double longitude = newsObj.getDouble("Longitude");
                                            double latitude = newsObj.getDouble("Latitude");
                                            news = new NewsItems(newsId,userId,uName,uImage,upLoadDate,newsTitle,newsDescription,newsImage,longitude,latitude);
                                            if(   newsDAO.insert(news)>=1){
                                                Toast.makeText(getContext(), "data inserted", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.INVISIBLE);
                                                newsAdapter = new NewsAdapter(newsDAO.getAll(), getContext());
                                                RcVData.setAdapter(newsAdapter);
                                            }
                                            else {
                                                Toast.makeText(getContext(), "data Not inserted Check YouInternet Connection", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getActivity(), "Please Connect To The Internet To Fetch Data", Toast.LENGTH_SHORT).show();
                            }
                        });
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }catch (Exception e) { ReturnResult = "\nERROR: " + e.getMessage(); }
            return ReturnResult;
        }
        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            newsAdapter = new NewsAdapter(newsDAO.getAll(), getContext());
            RcVData.setAdapter(newsAdapter);
        }
    }
}


