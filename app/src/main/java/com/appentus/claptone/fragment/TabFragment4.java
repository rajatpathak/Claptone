package com.appentus.claptone.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.appentus.claptone.R;
import com.appentus.claptone.activities.ActivitySignIn;
import com.appentus.claptone.activities.YoutubeActivity;
import com.appentus.claptone.adapter.VideoAdapter;
import com.appentus.claptone.model.ClickListener;
import com.appentus.claptone.model.VideoModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TabFragment4 extends Fragment {
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    private RecyclerView recyclerView;
    private VideoAdapter mAdapter;
    private List<VideoModel> videosList = new ArrayList<>();
    public String API_KEY = "AIzaSyAjmwf7LpMHJnpr_z18bMnswPiMB7jFQOQ";
    private static String VIDEO_ID = "EGy39OMyHzw";


    String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
            MediaStore.Video.Thumbnails.VIDEO_ID };
    private ImageView imCancel;
    private FrameLayout vidFrame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tab_fragment_4, container, false);


        videoView = view.findViewById(R.id.videoView);
        imCancel= view.findViewById(R.id.imCancel);
        vidFrame= view.findViewById(R.id.vidFrame);
        recyclerView = view.findViewById(R.id.recycler_view);



//        Intent i=new Intent(getActivity(),YoutubeActivity.class);
//        startActivity(i);
//        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
////        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
////        transaction.add(R.id.youtube_view, youTubePlayerFragment).commit();
//
//
////
//        imCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.setVisibility(View.GONE);
//                vidFrame.setVisibility(View.GONE);
//                imCancel.setVisibility(View.GONE);
//            }
//        });
//        // Set the media controller buttons
//        if (mediaController == null) {
//            mediaController = new MediaController(getContext());
//
//            // Set the videoView that acts as the anchor for the MediaController.
//            mediaController.setAnchorView(videoView);
//
//
//            // Set MediaController for VideoView
//            videoView.setMediaController(mediaController);
//        }
//        try {
//            // ID of video file.
//            int id = this.getRawResIdByName("video");
////            videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + id));
//
//        } catch (Exception e) {
//            Log.e("Error", e.getMessage());
//            e.printStackTrace();
//        }
//        videoView.requestFocus();
//
//        // When the video file ready for playback.
//        videoView.setOnPreparedListener(new OnPreparedListener() {
//
//            public void onPrepared(MediaPlayer mediaPlayer) {
//
//
//                videoView.seekTo(position);
//                if (position == 0) {
//                    videoView.start();
//                }   // When video Screen change size.
//                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
//                    @Override
//                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//
//                        // Re-Set the videoView that acts as the anchor for the MediaController
//                        mediaController.setAnchorView(videoView);
//                    }
//                });
//            }
//        });
//
//
//        getAllMedia();
//
//        recyIniti();
//
//        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
//
//
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
//                if (!wasRestored) {
//                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
//                    player.loadVideo(VIDEO_ID);
//                    player.play();
//                }
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
//                // YouTube error
//                String errorMessage = error.toString();
//                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
//                Log.d("errorMessage:", errorMessage);
//            }
//        });
        return view;
    }


    // Find ID corresponding to the name of the resource (in the directory raw).
    public int getRawResIdByName(String resName) {
        String pkgName = getActivity().getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }


    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }



//    // After rotating the phone. This method is called.
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        // Get saved position.
//        position = savedInstanceState.getInt("CurrentPosition");
//        videoView.seekTo(position);
//    }




    public ArrayList<String> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media._ID,MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.SIZE};
        String selection=MediaStore.Video.Media.DATA +" like?";
        String[] selectionArgs=new String[]{"%WhatsApp%"};
        final String orderBy   = MediaStore.Video.Media.DATE_TAKEN;
        Cursor cursor = getActivity().managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);
        try {
            cursor.moveToFirst();
            do{
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))));
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        for (int i=0;i<downloadedList.size();i++){
            Log.e("vid ", downloadedList.get(i));
            String vid=downloadedList.get(i);
            VideoModel model = new VideoModel(vid);
            videosList.add(model);
        }
        return downloadedList;
    }






    public void recyIniti() {
        mAdapter = new VideoAdapter(videosList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        videoView.setVideoPath(videosList.get(0).getTitle());
        //recyclerView item touch listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {
                vidFrame.setVisibility(View.VISIBLE);
                imCancel.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoPath(videosList.get(position).getTitle());
                Log.e("recThis", "Short press on position :" + position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));
    }
}