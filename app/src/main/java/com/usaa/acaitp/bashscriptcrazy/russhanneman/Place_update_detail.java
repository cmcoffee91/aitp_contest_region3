package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



/**
 * Created by christophercoffee on 10/20/16.
 */

public class Place_update_detail extends Fragment {
    EditText name, timesVisited;
    TextView timeView, locationView;
    ImageView picView;
    Button  viewMap;
    File mPhotoFile;
    Places place;
    private static final int REQUEST_PHOTO= 2;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.place_update_detail_view,container,false);

        Bundle args = getArguments();
        place = (Places) args
                .getSerializable("place");


        name = (EditText) v.findViewById(R.id.firstName);

        timeView = (TextView)v.findViewById(R.id.textView2);
        timeView.setText(place.getTime());

        timesVisited = (EditText)v.findViewById(R.id.editText3);
        timesVisited.setText(place.getVisits());

        locationView = (TextView)v.findViewById(R.id.textView3);
        locationView.setText("Long:" + place.getLatitude() + ", Lat:" + place.getLongitude());



        picView = (ImageView) v.findViewById(R.id.picView);

        name.setText(place.getName());

        viewMap = (Button) v.findViewById(R.id.button2);
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri gmmIntentUri = Uri.parse("geo:" + place.getLatitude() + "," + place.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });


        PictureUtils pictureUtils = new PictureUtils();
        mPhotoFile = pictureUtils.getPhotoFile(getActivity(),place);

        setPhotoView();












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
            picView.setImageBitmap(correctBmp);
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
        menuInflater.inflate(R.menu.menu_update,menu);
        MenuItem saveMember =  menu.findItem(R.id.menu_update_place);
        saveMember.setTitle("UPDATE");



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_update_place:
                Place_crud crud = Place_crud.get(getActivity());
                place.setName(name.getText().toString());
                place.setTime(place.getTime());
                place.setLatitude(place.getLatitude());
                place.setLongitude(place.getLongitude());
                place.setPicLoc(mPhotoFile.getPath());
                place.setVisits(timesVisited.getText().toString());
                crud.updateMember(place);
                CM_fragment frag = new CM_fragment();
                frag.popFragByName(getActivity(),"Place_update_detail");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
