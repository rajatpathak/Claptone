package com.appentus.claptone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appentus.claptone.R;
import com.appentus.claptone.adapter.YoutubeVideoAdapter;
import com.appentus.claptone.fragment.RecyclerTouchListener;
import com.appentus.claptone.model.ClickListener;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class YoutubeActivity extends AppCompatActivity {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    public String API_KEY = "AIzaSyAjmwf7LpMHJnpr_z18bMnswPiMB7jFQOQ";
    private static String VIDEO_ID = "EGy39OMyHzw";
    LinearLayout clap,show,music,video,shop;
    private static final String TAG = MainActivity.class.getSimpleName();
    String URL="https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC7ZRAt7eWXsmanQ3x4EWmZw&maxResults=25&key=AIzaSyAjmwf7LpMHJnpr_z18bMnswPiMB7jFQOQ";

    private RecyclerView recyclerView;
    //youtube player fragment
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private ArrayList<String> youtubeVideoArrayList;

    //youtube player to play video when new video selected
    private YouTubePlayer youTubePlayer;
    private ArrayList<VideoDetails> videoDetailsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        /*
        clap=(LinearLayout)findViewById(R.id.clap);
        show=(LinearLayout)findViewById(R.id.show);
        music=(LinearLayout)findViewById(R.id.music);
        video=(LinearLayout)findViewById(R.id.video);
        shop=(LinearLayout)findViewById(R.id.shop);
        clap.setOnClickListener(this);
        show.setOnClickListener(this);
        music.setOnClickListener(this);
        shop.setOnClickListener(this);
        */
        videoDetailsArrayList=new ArrayList<>();


        showVideo();

        }


    /**
     * getting video here
     */

    private void showVideo() {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("items");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId=jsonObject1.getJSONObject("id");
                        JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");
                        VideoDetails videoDetails=new VideoDetails();

                        String videoid=jsonVideoId.getString("videoId");

                        Log.e(TAG," New Video Id" +videoid);
                        videoDetails.setURL(jsonObjectdefault.getString("url"));
                        videoDetails.setVideoName(jsonsnippet.getString("title"));
                        videoDetails.setVideoDesc(jsonsnippet.getString("description"));
                        videoDetails.setVideoId(videoid);

                        Log.e("vidId",videoDetails.getVideoName());
                        videoDetailsArrayList.add(videoDetails);
                        setUpRecyclerView();
                        populateRecyclerView();
                        if (videoDetailsArrayList.size()>0) {
                            initializeYoutubePlayer();
                        }
                    }
//                    lvVideo.setAdapter(customListAdapter);
//                    customListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


    /**
     * initialize youtube player via Fragment and get instance of YoutubePlayer
     */
    private void initializeYoutubePlayer() {

        youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.youtube_player_fragment);

        if (youTubePlayerFragment == null)
            return;

        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                                boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;
                    //set the player style default
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    //cue the 1st video by default
                    youTubePlayer.loadVideo(videoDetailsArrayList.get(0).getVideoId());

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {

                //print or show error if initialization failed
                Log.e("Eoorrrr", "Youtube Player View initialization failed");
            }
        });
    }


    /**
     * setup the recycler view here
     */
    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Horizontal direction recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * populate the recycler view and implement the click event here
     */
    private void populateRecyclerView() {

        for (int i=0;i<videoDetailsArrayList.size();i++){
            Log.e("recThisTest", videoDetailsArrayList.get(i).getURL());
        }
        final YoutubeVideoAdapter adapter = new YoutubeVideoAdapter(this, videoDetailsArrayList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {

                Log.e("recThis", "Short press on position :" + position);
                if (youTubePlayerFragment != null && youTubePlayer != null) {
                    //update selected position
                    adapter.setSelectedPosition(position);

                    //load selected video
                    youTubePlayer.loadVideo(videoDetailsArrayList.get(position).getVideoId());
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));


        //set click event
//        recyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(this, new RecyclerViewOnClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//                if (youTubePlayerFragment != null && youTubePlayer != null) {
//                    //update selected position
//                    adapter.setSelectedPosition(position);
//
//                    //load selected video
//                    youTubePlayer.cueVideo(youtubeVideoArrayList.get(position));
//                }
//
//            }
//        }));
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
                intent.putExtra("backCode", 1);//1 for fragament A use 2 for fragment B
                finish();
                startActivity(intent);
    }
}


























//
//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
//        if (!wasRestored) {
//            player.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
//            player.loadVideo(VIDEO_ID);
//        }
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
//        if (errorReason.isUserRecoverableError()) {
//            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
//        } else {
////            String error = String.format(getString(R.string.player_error), errorReason.toString());
//            Toast.makeText(this, "mkf", Toast.LENGTH_LONG).show();
//            Log.e("Error",errorReason.toString());
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RECOVERY_REQUEST) {
//            // Retry initialization if user performed a recovery action
//            getYouTubePlayerProvider().initialize(API_KEY, this);
//        }
//    }
//
//    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
//        return youTubeView;
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.clap:
//                Toast.makeText(getApplicationContext(),"home",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
//                intent.putExtra("SELECTEDVALUE", 1);//1 for fragament A use 2 for fragment B
//                finish();
//                startActivity(intent);
//
//                break;
//            case R.id.show:
//                Toast.makeText(getApplicationContext(),"home1",Toast.LENGTH_SHORT).show();
//                Intent intent2 = new Intent(getApplicationContext(), ActivityHome.class);
//                intent2.putExtra("SELECTEDVALUE", 2);//1 for fragament A use 2 for fragment B
//                finish();
//                startActivity(intent2);
//                break;
//            case R.id.music:
//                Toast.makeText(getApplicationContext(),"home2",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.shop:
//                Toast.makeText(getApplicationContext(),"home3",Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
