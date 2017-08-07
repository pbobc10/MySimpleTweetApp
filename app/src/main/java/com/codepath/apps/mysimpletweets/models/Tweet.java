package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Owner on 8/3/2017.
 */




//Parse the JSON + Store the data, encapsulate logic or display logic
public class Tweet implements Serializable {
    // list out the attributes
    private String body;
    private long uid; // unique id for the tweet
    private String createAt;
    private User user;


    public ArrayList<String> getMediaUrl() {
        return mediaUrl;
    }

    private ArrayList<String> mediaUrl;



    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreateAt() {
        return createAt;
    }



    // Deserialize the JSON and build com.codepath.apps.mysimpletweets.models.Tweet objects
    // com.codepath.apps.mysimpletweets.models.Tweet.fromJSON  {"{}"} => <com.codepath.apps.mysimpletweets.models.Tweet>
    public  static Tweet fromJSON (JSONObject jsonObject){
        Tweet tweet = new Tweet();
        // Extract the values from json, store then
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid =  jsonObject.getLong("id");
            tweet.createAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.mediaUrl = new ArrayList<>();

            if (!jsonObject.isNull("extended_entities")) {
                JSONArray multimedia = jsonObject.getJSONObject("extended_entities").getJSONArray("media");
                if(multimedia.length()>0){
                    for(int i = 0; i< multimedia.length(); i++) {
                        tweet.mediaUrl.add(multimedia.optJSONObject(i).getString("media_url"));
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Return the tweet object
        return tweet;

    }
    // Tweet.fromJASON ({... },{...}) => List<Tweet>
    public static ArrayList<Tweet> fromJSON(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<>();
        // Iterate the json and create tweets
        for (int i=0; i< jsonArray.length();i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        // Return the finished list
        return  tweets;



    }

}
