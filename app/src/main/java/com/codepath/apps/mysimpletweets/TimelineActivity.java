package com.codepath.apps.mysimpletweets;


import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    FragmentTweet fragmentTweet;
    FragmentManager fragmentManager;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4099ff")));

        fragmentManager = getFragmentManager();
        fragmentTweet = new FragmentTweet();
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

    lvTweets = (ListView) findViewById(R.id.lvTweets);
        // create the ArrayList (from data source)
        tweets = new ArrayList<>();
        // construct the adapter from data source
        aTweets = new TweetsArrayAdapter(this,tweets);
        // connect adapter to list view
        lvTweets.setAdapter(aTweets);
        // Get the Client
        client = TwitterApplication.getRestClient(); // singleton client

        populateTimeline(0);
        // Attach the listener to the AdapterView onCreate
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                //loadNextDataFromApi(page);
                // or loadNextDataFromApi(totalItemsCount);
                populateTimeline(page);
                //loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }

        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                loadNextDataFromApi(0);

            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }


    public void onTweet (View view){
        fragmentTweet.show(fragmentManager,"Tweet");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_menu,menu);
        return true;
    }

    public void onMenu(MenuItem item) {

        fragmentTweet.show(fragmentManager,"Tweet");

    }

    private void loadNextDataFromApi(int page) {
        client.onloadNext(page,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                aTweets.clear();

                Log.d("DEBUG",response.toString());
                // DESERIALIZE JSON
                // CREATE MODELS
                // LOAD THE MODEL DATA INTO LISTVIEW
                aTweets.addAll(Tweet.fromJSON(response));
                Log.d("DEBUG",aTweets.toString());

                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG",errorResponse.toString());
            }
        });

    }

    // Send an API request to get timeline json
    // Fill the listView by creating the tweet objects from json


    private void populateTimeline(int oldest) {
        client.geHomeTimeLine(oldest,new  JsonHttpResponseHandler(){
            //  SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                aTweets.clear();
                Log.d("DEBUG",response.toString());
                // DESERIALIZE JSON
                // CREATE MODELS
                // LOAD THE MODEL DATA INTO LISTVIEW
                aTweets.addAll(Tweet.fromJSON(response));
                Log.d("DEBUG",aTweets.toString());
                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG",errorResponse.toString());
            }
        });
    }



}
