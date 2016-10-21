package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;





/**
 * Created by christophercoffee on 10/20/16.
 */

public class Place_detail extends Fragment {
    EditText name;
    File mPhotoFile;
    ImageView picView;
    Places place;
    private static final int REQUEST_PHOTO= 2;
    public static int VIDEO_CAPTURED = 1;
    private GoogleApiClient mClient;
    String longitude, latitude;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                        System.out.println("got connected");
                        getLocation();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

    }

    private void getLocation() {

        System.out.println("in getLocation");
        LocationRequest request = LocationRequest.create();



        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("location", "check location permissions");
            return;
        }

        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i("location", "Got a fix: " + location);
                        //new SearchTask().execute(location);
                        Log.d("location","longitude " + String.valueOf(location.getLongitude()));
                        Log.d("location","latitude " + String.valueOf(location.getLatitude()));

                        longitude = String.valueOf(location.getLongitude());
                        latitude = String.valueOf(location.getLatitude());

                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().invalidateOptionsMenu();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        mClient.disconnect();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.place_detail_view,container,false);

        Bundle args = getArguments();




        name = (EditText) v.findViewById(R.id.editText);


        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getActivity().getPackageManager();

        place = new Places();
        UUID id = UUID.randomUUID();
        System.out.println("random id is " + id.toString());
        place.setId(id);

        PictureUtils pictureUtils = new PictureUtils();
        mPhotoFile = pictureUtils.getPhotoFile(getActivity(),place);


        Button takePic = (Button) v.findViewById(R.id.button8);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        takePic.setEnabled(canTakePhoto);





        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            System.out.println("can take photo");
            System.out.println("photoFile:" + mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);



            }
        });

        // Button save = (Button) v.findViewById(R.id.button9);
        picView = (ImageView)v.findViewById(R.id.imageView2);
        picView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView name = (TextView)v.findViewById(R.id.textView);
        name.setText(place.getName());

        v.setBackgroundColor(Color.BLUE);



        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {

            setPhotoView();

        }


    }



    private void setPhotoView()
    {
        try {
            File f = new File(mPhotoFile.getPath());
            ExifInterface exif = new ExifInterface(f.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            int angle = 0;

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90;
            }
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180;
            }
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270;
            }

            Matrix mat = new Matrix();
            mat.postRotate(angle);

            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
            Bitmap correctBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mat, true);
            Bitmap finalBm = PictureUtils.getScaledBitmap(correctBmp,400,400);
            Bitmap circleBm = PictureUtils.getclip(finalBm);
            Drawable d = new BitmapDrawable(finalBm);
            //RoundedBitmapDrawable circleWithBorder = PictureUtils.createRoundedBitmapDrawableWithBorder(finalBm,getActivity());

            //picView.setImageDrawable(circleWithBorder);
            picView.setImageBitmap(finalBm);
        }
        catch (IOException e) {
            Log.w("TAG", "-- Error in setting image");
        }
        catch(OutOfMemoryError oom) {
            Log.w("TAG", "-- OOM Error in setting image");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.menu_add,menu);
        MenuItem savePlace =  menu.findItem(R.id.menu_save);
        savePlace.setTitle("save");



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        CM_dates dates = new CM_dates();
        switch(item.getItemId())
        {
            case R.id.menu_save:
                Place_crud crud = Place_crud.get(getActivity());
                place.setName(name.getText().toString());
                place.setTime(dates.getCurrentDate() + ", " + dates.getCurrentTime());
                place.setLatitude(latitude);
                place.setLongitude(longitude);
                place.setPicLoc(mPhotoFile.getPath());
                place.setVisits("1");
                crud.addMember(place);
                CM_fragment frag = new CM_fragment();
                frag.popFragByName(getActivity(),"Place_detail");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}

