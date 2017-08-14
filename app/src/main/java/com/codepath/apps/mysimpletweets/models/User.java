package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Owner on 8/3/2017.
 */

public class User {

    // list arrtibutes
    private  String name;
    private  String screenName;
    private  String profileImageUrl;
    private  long uid;
    private  int followersCount;
    private  int followingsCount;
    private  String tagline;



    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public long getUid() {
        return uid;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return followingsCount;
    }

    public String getTagline() {
        return tagline;
    }

    // deserialize the user json => User
    public  static  User fromJSON(JSONObject jsonObject){
        User user =  new User();

        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.tagline = jsonObject.getString("description");
            user.followersCount = jsonObject.getInt("followers_count");
            user.followingsCount = jsonObject.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // return a user
        return user;

    }


}
