package com.example.abhishek.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.example.abhishek.myapplication.MainActivity.userModelList;


public class ReadyFragment extends Fragment {

    static List<UserModel> readyList;
    static UsersListAdapter usersListAdapter3;

    public ReadyFragment() {
        // Required empty public constructor
    }

    public static ReadyFragment newInstance(String param1, String param2) {
        ReadyFragment fragment = new ReadyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ready, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onStart()
    {
        super.onStart();
        RecyclerView mMainList;
        mMainList = (RecyclerView) getActivity().findViewById(R.id.ready_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(getContext()));
        //usersListAdapter3 = new UsersListAdapter(readyList);
        mMainList.setAdapter(usersListAdapter3);

        /*for(int i=0;;i++)
        {
            UserModel usermodel=null;
            try{
                usermodel=userModelList.get(i);}
            catch(Exception et){break;}
            if(usermodel==null){break;}
            if(usermodel.status.equals("Ready"))
            {readyList.add(usermodel);usersListAdapter3.notifyDataSetChanged();}
        }*/
    }
}
