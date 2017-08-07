package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Owner on 8/3/2017.
 */

public class User {

    // list arrtibutes
    private  String name;
    private  String screeName;
    private  String profileImageUrl;
    private  long uid;

    public String getName() {
        return name;
    }

    public String getScreeName() {
        return screeName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public long getUid() {
        return uid;
    }

    // deserialize the user json => User
    public  static  User fromJSON(JSONObject jsonObject){
        User user =  new User();

        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screeName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // return a user
        return user;

    }


}
