package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by christophercoffee on 9/20/16.
 */


public class Start_frag extends Fragment {

    private RecyclerView mMemberRecyclerView;
    private RussAdapter mMemberAdapter;
    private List<Places> mMemberList ;
    private List<Places> mMemberListSave ;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.start_frag,container,false);


        mMemberList = new ArrayList<>();


        Place_crud user_crud = Place_crud.get(getActivity());



        mMemberList = user_crud.getMembers(null,null,"id desc");





        mMemberRecyclerView = (RecyclerView) v.findViewById(R.id.member_recycler_view);
        mMemberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMemberAdapter = new RussAdapter(mMemberList,getActivity());
        mMemberRecyclerView.setAdapter(mMemberAdapter);

        mMemberAdapter.notifyDataSetChanged();


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.search_item,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView1 = (SearchView) searchItem.getActionView();


        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("searchQuerySubmit","query is : " + query);
                lookForMember(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("searchQueryOnTextChange","query textchange is : " + newText);
                return false;
            }
        });

        searchView1.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("search","search closed");
                lookForMember("");
                return false;
            }
        });

        MenuItem addMember = menu.findItem(R.id.menu_add_place);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_add_place:
                CM_fragment frag = new CM_fragment();
                frag.launchFragWithName(getActivity(),"Place_detail",null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    private void updateUI()
    {
        Place_crud user_crud = Place_crud.get(getActivity());
        mMemberList = user_crud.getMembers(null,null,null);
        if(mMemberAdapter == null)
        {
            mMemberAdapter = new RussAdapter(mMemberList,getActivity());
            mMemberRecyclerView.setAdapter(mMemberAdapter);
        }
        else
        {
            mMemberAdapter.setmMemberList(mMemberList);
            mMemberAdapter.notifyDataSetChanged();
        }
    }

    public void lookForMember(String query)
    {
        List<Places> new_memberList = new ArrayList<>();
        Place_crud user_crud = Place_crud.get(getActivity());

        if(!query.trim().isEmpty())
        {


            mMemberList.clear();
            mMemberList = user_crud.getMembers(PlaceDbSchema.PlaceTable.Cols.NAME + " LIKE ?",new String[] {"%" + query +"%"},null);
            mMemberAdapter.setmMemberList(mMemberList);
            System.out.println("memberlist size in search " + mMemberList.size());
            mMemberAdapter.notifyDataSetChanged();
        }
        else
        {
            mMemberList.clear();
            mMemberList = user_crud.getMembers(null,null,null);
            mMemberAdapter.setmMemberList(mMemberList);
            mMemberAdapter.notifyDataSetChanged();
        }

    }

}


