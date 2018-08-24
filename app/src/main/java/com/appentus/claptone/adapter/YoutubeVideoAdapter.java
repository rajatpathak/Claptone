package com.appentus.claptone.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.claptone.R;
import com.appentus.claptone.activities.VideoDetails;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.YoutubeViewHolder> {
    private static final String TAG = YoutubeVideoAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<VideoDetails> videoDetailsArrayList;
    public String API_KEY = "AIzaSyAjmwf7LpMHJnpr_z18bMnswPiMB7jFQOQ";
    private static String VIDEO_ID = "EGy39OMyHzw";
    //position to check which position is selected
    private int selectedPosition = 0;


    public YoutubeVideoAdapter(Context context, ArrayList<VideoDetails> videoDetailsArrayList) {
        this.context = context;
        this.videoDetailsArrayList = videoDetailsArrayList;
    }

    public class YoutubeViewHolder extends RecyclerView.ViewHolder {
        public YouTubeThumbnailView video_thumbnail_image_view;
//        public ImageView video_thumbnail_image_view;
        public TextView vidTitle;


        public YoutubeViewHolder(View view) {
            super(view);
            video_thumbnail_image_view= (YouTubeThumbnailView) view.findViewById(R.id.video_thumbnail_image_view);
//            video_thumbnail_image_view= (ImageView) view.findViewById(R.id.video_thumbnail_image_view);
            vidTitle= (TextView) view.findViewById(R.id.vidTitle);

        }
    }

    @Override
    public YoutubeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.youtube_video_list, parent, false);
        return new YoutubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YoutubeViewHolder holder, final int position) {


        /*  initialize the thumbnail image view , we need to pass Developer Key */
       /* holder.video_thumbnail_image_view.initialize(API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(videoDetailsArrayList.get(position).getVideoId());

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        //print or show error when thumbnail load failed
                        Log.e(TAG, "Youtube Thumbnail Error");
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //print or show error when initialization failed
                Log.e(TAG, "Youtube Initialization Failure");

            }
        });*/


        holder.vidTitle.setText(videoDetailsArrayList.get(position).getVideoName());
    }

    @Override
    public int getItemCount() {
        return videoDetailsArrayList != null ? videoDetailsArrayList.size() : 0;
    }

    /**
     * method the change the selected position when item clicked
     * @param selectedPosition
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        //when item selected notify the adapter
        notifyDataSetChanged();
    }

}