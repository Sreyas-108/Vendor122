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
import static com.example.abhishek.myapplication.PendAccFragment.pendaccList;
import static com.example.abhishek.myapplication.PendAccFragment.usersListAdapter2;


public class FinDecFragment extends Fragment {


    static List<UserModel> findecList;
    static UsersListAdapter usersListAdapter1;

    public FinDecFragment() {
        // Required empty public constructor
    }

    public static FinDecFragment newInstance(String param1, String param2) {
        FinDecFragment fragment = new FinDecFragment();
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
        return inflater.inflate(R.layout.fragment_fin_dec, container, false);
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
        mMainList = (RecyclerView) getActivity().findViewById(R.id.fin_dec_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(getContext()));
        //usersListAdapter1 = new UsersListAdapter(findecList);
        mMainList.setAdapter(usersListAdapter1);

        /*for(int i=0;;i++)
        {
            UserModel usermodel=null;
            try{
                usermodel=userModelList.get(i);}
            catch(Exception et){break;}
            if(usermodel==null){break;}
            if(usermodel.status.equals("Finished")||usermodel.status.equals("Declined"))
            {findecList.add(usermodel);usersListAdapter1.notifyDataSetChanged();}
        }

        for(int i=0;;i++)
        {
            UserModel usermodel=null;
            try{
                usermodel=userModelList.get(i);}
            catch(Exception et){break;}
            if(usermodel==null){break;}
            if(usermodel.status.equals("Pending")||usermodel.status.equals("Accepted"))
            {pendaccList.add(usermodel);usersListAdapter2.notifyDataSetChanged();}
        }*/
    }
}
