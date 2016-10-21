package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

;

/**
 * Created by christophercoffee on 10/20/16.
 */

public class RussAdapter extends RecyclerView.Adapter<RussAdapter.MyViewHolder> {

    private List<Places> mMemberList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Button deleteBut;
        public ImageView imageView;
        private File mPhotoFile;

        public MyViewHolder(View view) {
            super(view);


            imageView = (ImageView)view.findViewById(R.id.imageView) ;


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Places place = mMemberList.get(getAdapterPosition());
                    Bundle args = new Bundle();
                    args.putSerializable("place", place);
                    CM_fragment updateDetailFrag = new CM_fragment();
                    FragmentActivity mycontext = (FragmentActivity)mContext;
                    updateDetailFrag.launchFragWithName(mycontext,"Place_update_detail",args);


                }
            });


            name = (TextView) view.findViewById(R.id.name);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            deleteBut = (Button)view.findViewById(R.id.button7);
            deleteBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Places place = mMemberList.get(getAdapterPosition());
                    Place_crud place_crud = Place_crud.get(mContext);
                    place_crud.deleteMember(place);
                    mMemberList.clear();
                    mMemberList = place_crud.getMembers(null,null,null);
                    notifyDataSetChanged();
                }
            });

        }
    }

    public void setmMemberList(List memberList)
    {
        mMemberList = memberList;
    }



    public RussAdapter(List<Places> memberList, Context context) {
        this.mMemberList = memberList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_list_items, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Places place = mMemberList.get(position);
        holder.name.setText(place.getName());


        PictureUtils pictureUtils = new PictureUtils();
        File mPhotoFile = pictureUtils.getPhotoFile(mContext,place);
        System.out.println(mPhotoFile.getPath());

        File f = new File(mPhotoFile.getPath());
        Glide.with(mContext)
                .load(new File(f.getPath())) // Uri of the picture
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mMemberList.size();
    }
}
