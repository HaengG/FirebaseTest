package com.nslb.firebasetest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nslb.firebasetest.R;

public class TripTalkView extends Fragment {
    private DatabaseReference mDatabase;
    private RecyclerView mRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_triptalkview,container,false);

        //mRecycler.setHasFixedSize(true);

        view.findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewBoardActivity.class));
            }
        });
        return view;
    }
}
