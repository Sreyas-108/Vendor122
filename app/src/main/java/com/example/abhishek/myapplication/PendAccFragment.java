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


public class PendAccFragment extends Fragment {

    static List<UserModel> pendaccList;
    static UsersListAdapter usersListAdapter2;

    public PendAccFragment() {
        // Required empty public constructor
    }

    public static PendAccFragment newInstance(String param1, String param2) {
        PendAccFragment fragment = new PendAccFragment();
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
        return inflater.inflate(R.layout.fragment_pend_acc, container, false);
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
        RecyclerView mMainList2;
        mMainList2 = (RecyclerView) getActivity().findViewById(R.id.pend_acc_list);
        mMainList2.setHasFixedSize(true);
        mMainList2.setLayoutManager(new LinearLayoutManager(getContext()));
        //usersListAdapter2 = new UsersListAdapter(pendaccList);
        mMainList2.setAdapter(usersListAdapter2);
    }
}
