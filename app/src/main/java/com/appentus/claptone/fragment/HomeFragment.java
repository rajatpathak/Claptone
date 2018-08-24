package com.appentus.claptone.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.appentus.claptone.CameraPreview;
import com.appentus.claptone.MyBounceInterpolator;
import com.appentus.claptone.R;
import com.appentus.claptone.activities.NoCamera;
import com.appentus.claptone.activities.NoSDCard;
import com.appentus.claptone.adapter.EffectAdapter;
import com.appentus.claptone.model.ClickListener;
import com.appentus.claptone.model.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;


public class HomeFragment extends Fragment {
    private static int currentCameraId=1;
    private Camera mCamera;
    private CameraPreview mPreview;
    private SensorManager sensorManager = null;
    private int orientation;
    private ExifInterface exif;
    private int deviceHeight;
    private Button ibRetake;
    private Button ibUse;
    private ImageView ibCapture,imVid,imVid2,ibCapture2,vid,switchCamera;
    private LinearLayout flBtnContainer,imageSharePanel;
    private File sdRoot;
    private String dir;
    private String fileName;
    private ImageView rotatingImage;
    private int degrees = -1;

    private String clickedImagePath;
    public View view;
    private List<Model> effectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EffectAdapter mAdapter;
    private Display display;
    private int width,height ;
    private FrameLayout preview;
    private boolean inPreview=true;
    private RelativeLayout rlVid2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        recyIniti();
        // Setting all the path for the image
        sdRoot = Environment.getExternalStorageDirectory();
        dir = "/DCIM/Camera/";

        // Getting all the needed elements from the layout
        rotatingImage = (ImageView) view.findViewById(R.id.imageView1);
        ibRetake = (Button) view.findViewById(R.id.ibRetake);
        ibUse = (Button) view.findViewById(R.id.ibUse);
        ibCapture = (ImageView) view.findViewById(R.id.ibCapture);
        ibCapture2 = (ImageView) view.findViewById(R.id.ibCapture2);
        imVid=(ImageView)view.findViewById(R.id.imVid);
        rlVid2=(RelativeLayout) view.findViewById(R.id.rlVid2);
        switchCamera= (ImageView) view.findViewById(R.id.switchCamera);
        flBtnContainer = (LinearLayout) view.findViewById(R.id.flBtnContainer);
        imageSharePanel= (LinearLayout) view.findViewById(R.id.imageSharePanel);



        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                camSwitch();
            }

        });
        // Getting the sensor service.
//        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);

        // Selecting the resolution of the Android device so we can create a
        // proportional preview
        display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        final LinearLayout layout = (LinearLayout)view.findViewById(R.id.layoutcam);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                width  = layout.getMeasuredWidth();
                height = layout.getMeasuredHeight();

            }
        });
        deviceHeight = display.getHeight();
        // Add a listener to the Capture button
        ibCapture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                didTapButtonCam(view);


            }
        });
        // Add a listener to the Capture button
        rlVid2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                didTapButtonVid(view);


            }
        });
ibCapture2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageProcessing();
                mCamera.takePicture(null, null, mPicture);
            }
        });


        // Add a listener to the Retake button
        ibRetake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Deleting the image from the SD card/
                File discardedPhoto = new File(sdRoot, dir + fileName);
                discardedPhoto.delete();

                // Restart the camera preview.
                mCamera.startPreview();

                // Reorganize the buttons on the screen

                flBtnContainer.setVisibility(LinearLayout.VISIBLE);
                ibRetake.setVisibility(LinearLayout.GONE);
                ibUse.setVisibility(LinearLayout.GONE);
            }
        });

        // Add a listener to the Use button
        ibUse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Everything is saved so we can quit the app.
                getActivity().finish();
            }
        });

        return view;
    }

    private void imageProcessing() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable=true;
        Bitmap myBitmap = BitmapFactory.decodeResource(
                getActivity().getResources(),
                R.drawable.face,
                options);
        Paint myRectPaint = new Paint();
        myRectPaint.setStrokeWidth(5);
        myRectPaint.setColor(Color.BLACK);
        myRectPaint.setStyle(Paint.Style.STROKE);
        Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(myBitmap, 0, 0, null);
        FaceDetector faceDetector = new
                FaceDetector.Builder(getContext()).setTrackingEnabled(false)
                .build();

        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);
        for(int i=0; i<faces.size(); i++) {
            Face thisFace = faces.valueAt(i);
            float x1 = thisFace.getPosition().x;
            float y1 = thisFace.getPosition().y;
            float x2 = x1 + thisFace.getWidth();
            float y2 = y1 + thisFace.getHeight();
            tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
        }
//        camera_preview.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));
        preview.setBackgroundDrawable(new BitmapDrawable(getResources(),tempBitmap));
    }


    public void recyIniti() {


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new EffectAdapter(effectList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        prepareMovieData();




        //recyclerView item touch listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {

                Log.e("recThis", "Short press on position :" + position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

    }


    public void prepareMovieData() {
        Model model = new Model("No Effect");
        effectList.add(model);

        model = new Model("Effect 1");
        effectList.add(model);


        model = new Model("Effect 2");
        effectList.add(model);


        model = new Model("Effect 3");
        effectList.add(model);


        model = new Model("Effect 4");
        effectList.add(model);


        model = new Model("Effect 5");
        effectList.add(model);


        model = new Model("Effect 6");
        effectList.add(model);


        model = new Model("Effect 7");
        effectList.add(model);


        model = new Model("Effect 8");
        effectList.add(model);


        mAdapter.notifyDataSetChanged();
    }


    private void createCamera() {
        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Setting the right parameters in the camera

        Camera.Parameters params = mCamera.getParameters();
//set max Picture Size
        params.setPictureSize(display.getWidth(), display.getHeight());
        params.setPictureFormat(PixelFormat.JPEG);
        mCamera.setDisplayOrientation(90);
        params.setJpegQuality(100);
//        mCamera.setParameters(params);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(getContext(), mCamera);
        FrameLayout preview = (FrameLayout) view.findViewById(R.id.camera_preview);

        // Calculating the width of the preview so it is proportional.
        float widthFloat = (float) ((deviceHeight) * 4 / 3)/2;
        int width = Math.round(widthFloat);

        // Resizing the LinearLayout so we can make a proportional preview. This
        // approach is not 100% perfect because on devices with a really small
        // screen the the image will still be distorted - there is place for
        // improvment.
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, deviceHeight);
        preview.setLayoutParams(layoutParams);

        // Adding the camera preview after the FrameLayout and before the button
        // as a separated element.
        preview.addView(mPreview,0);
    }
    private void camSwitch() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            //mCamera = null;
        }

        //swap the id of the camera to be used
        if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }else {
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }

        try {
            mCamera= Camera.open(currentCameraId);
            mCamera.setPreviewDisplay(mPreview.getHolder());
//            getActivity().recreate();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Test if there is a camera on the device and if the SD card is
        // mounted.
        if (!checkCameraHardware(getContext())) {
            Intent i = new Intent(getContext(), NoCamera.class);
            startActivity(i);
            getActivity().finish();
        } else if (!checkSDCard()) {
            Intent i = new Intent(getContext(), NoSDCard.class);
            startActivity(i);
            getActivity().finish();
        }

        // Creating the camera
        createCamera();

        // Register this class as a listener for the accelerometer sensor

//        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        // release the camera immediately on pause event
        releaseCamera();

        // removing the inserted view - so when we come back to the app we
        // won't have the views on top of each other.
        preview = (FrameLayout) view.findViewById(R.id.camera_preview);
        preview.removeViewAt(0);
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release(); // release the camera for other applications
            mCamera = null;
        }
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private boolean checkSDCard() {
        boolean state = false;

        String sd = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(sd)) {
            state = true;
        }

        return state;
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            // attempt to get a Camera instance
            c = Camera.open(currentCameraId);
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }

        // returns null if camera is unavailable
        return c;
    }


    private android.hardware.Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {

            // Replacing the button after a photho was taken.
            flBtnContainer.setVisibility(View.GONE);
            imageSharePanel.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            ibRetake.setVisibility(View.VISIBLE);
            ibUse.setVisibility(View.VISIBLE);

            // File name of the image that we just took.
            fileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString() + ".jpg";

            // Creating the directory where to save the image. Sadly in older
            // version of Android we can not get the Media catalog name
            File mkDir = new File(sdRoot, dir);
            mkDir.mkdirs();

            // Main file where to save the data that we recive from the camera
            File pictureFile = new File(sdRoot, dir + fileName);

            Log.e("Path",""+"/sdcard/" + dir + fileName);
            clickedImagePath="/sdcard/" + dir + fileName;
            try {
                FileOutputStream purge = new FileOutputStream(pictureFile);
                purge.write(data);
                purge.close();
            } catch (FileNotFoundException e) {
                Log.d("DG_DEBUG", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("DG_DEBUG", "Error accessing file: " + e.getMessage());
            }

            // Adding Exif data for the orientation. For some strange reason the
            // ExifInterface class takes a string instead of a file.
            try {
                exif = new ExifInterface("/sdcard/" + dir + fileName);
                exif.setAttribute(ExifInterface.TAG_ORIENTATION, "" + orientation);
                exif.saveAttributes();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * Putting in place a listener so we can get the sensor data only when
     * something changes.
     */
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                RotateAnimation animation = null;
                if (event.values[0] < 4 && event.values[0] > -4) {
                    if (event.values[1] > 0 && orientation != ExifInterface.ORIENTATION_ROTATE_90) {
                        // UP
                        orientation = ExifInterface.ORIENTATION_ROTATE_90;
                        animation = getRotateAnimation(270);
                        degrees = 270;
                    } else if (event.values[1] < 0 && orientation != ExifInterface.ORIENTATION_ROTATE_270) {
                        // UP SIDE DOWN
                        orientation = ExifInterface.ORIENTATION_ROTATE_270;
                        animation = getRotateAnimation(90);
                        degrees = 90;
                    }
                } else if (event.values[1] < 4 && event.values[1] > -4) {
                    if (event.values[0] > 0 && orientation != ExifInterface.ORIENTATION_NORMAL) {
                        // LEFT
                        orientation = ExifInterface.ORIENTATION_NORMAL;
                        animation = getRotateAnimation(0);
                        degrees = 0;
                    } else if (event.values[0] < 0 && orientation != ExifInterface.ORIENTATION_ROTATE_180) {
                        // RIGHT
                        orientation = ExifInterface.ORIENTATION_ROTATE_180;
                        animation = getRotateAnimation(180);
                        degrees = 180;
                    }
                }
                if (animation != null) {
                    rotatingImage.startAnimation(animation);
                }
            }

        }
    }

    /**
     * Calculating the degrees needed to rotate the image imposed on the button
     * so it is always facing the user in the right direction
     *
     * @param toDegrees
     * @return
     */
    private RotateAnimation getRotateAnimation(float toDegrees) {
        float compensation = 0;

        if (Math.abs(degrees - toDegrees) > 180) {
            compensation = 360;
        }

        // When the device is being held on the left side (default position for
        // a camera) we need to add, not subtract from the toDegrees.
        if (toDegrees == 0) {
            compensation = -compensation;
        }

        // Creating the animation and the RELATIVE_TO_SELF means that he image
        // will rotate on it center instead of a corner.
        RotateAnimation animation = new RotateAnimation(degrees, toDegrees - compensation, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        // Adding the time needed to rotate the image
        animation.setDuration(250);

        // Set the animation to stop after reaching the desired position. With
        // out this it would return to the original state.
        animation.setFillAfter(true);

        return animation;
    }

    /**
     * STUFF THAT WE DON'T NEED BUT MUST BE HEAR FOR THE COMPILER TO BE HAPPY.
     */
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }




    //bounce animation
    public void didTapButtonCam(View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        imVid.setVisibility(View.GONE);
        ibCapture.setVisibility(View.GONE);
        ibCapture2.setVisibility(View.VISIBLE);
        ibCapture2.startAnimation(myAnim);
        rlVid2.setVisibility(View.VISIBLE);
    }

   //bounce animation
    public void didTapButtonVid(View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        ibCapture2.setVisibility(View.GONE);
        rlVid2.setVisibility(View.GONE);
        imVid.setVisibility(View.VISIBLE);
        imVid.startAnimation(myAnim);
        ibCapture.setVisibility(View.VISIBLE);
    }


}