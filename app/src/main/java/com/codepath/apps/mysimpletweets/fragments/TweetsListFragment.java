package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.FragmentTweet;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 8/8/2017.
 */

public class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    public TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    protected SwipeRefreshLayout swipeContainer;

    // inflation logic

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        // connect adapter to list view
        lvTweets.setAdapter(aTweets);

        // Attach the listener to the AdapterView onCreate
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                //loadNextDataFromApi(page);
                // or loadNextDataFromApi(totalItemsCount);
                //HomeTimelineFragment homeTimelineFragment = new HomeTimelineFragment();
                //swipeContainer.setRefreshing(false);
                //  homeTimelineFragment.populateTimeline(page);
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

                if (TweetsListFragment.this instanceof HomeTimelineFragment) {
                    ((HomeTimelineFragment) TweetsListFragment.this).populateTimeline(0);
                } else if (TweetsListFragment.this instanceof MentionsTimelineFragment) {
                    ((MentionsTimelineFragment) TweetsListFragment.this).populateTimeline();
                }else if(TweetsListFragment.this instanceof UserTimelineFragment){
                    ((UserTimelineFragment) TweetsListFragment.this ).populateTimeline();
                }

            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return v;
    }


    // ceation lifecycle event

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create the ArrayList (from data source)
        tweets = new ArrayList<>();
        // construct the adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);



    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.timeline_menu, menu);
    }

   /* public void onMenu(MenuItem item) {

        fragmentTweet.show(fragmentManager, "Tweet");

    }*/




    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
        aTweets.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);

    }

    /*public void onTweet(View view) {
        fragmentTweet.show(fragmentManager, "Tweet");

    }*/

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }
}
