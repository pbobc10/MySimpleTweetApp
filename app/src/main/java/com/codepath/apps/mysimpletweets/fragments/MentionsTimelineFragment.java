package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Owner on 8/10/2017.
 */

public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient client;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the Client
        client = TwitterApplication.getRestClient(); // singleton client
        populateTimeline();
    }

    // Send an API request to get timeline json
    // Fill the listView by creating the tweet objects from json


    public void populateTimeline() {
        //Checking the Internet is Connected
        if (isOnline()) {

            client.getMentionsTimeLine(new JsonHttpResponseHandler() {
                //  SUCCESS

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    aTweets.clear();
                    Log.d("DEBUG", response.toString());
                    // DESERIALIZE JSON
                    // CREATE MODELS
                    // LOAD THE MODEL DATA INTO LISTVIEW
                    addAll(Tweet.fromJSON(response));
                    swipeContainer.setRefreshing(false);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d("DEBUG", errorResponse.toString());
                }
            });
        }else {
            Toast.makeText(getContext(),"No Internet Connection !!!",Toast.LENGTH_LONG).show();
        }
    }
}
