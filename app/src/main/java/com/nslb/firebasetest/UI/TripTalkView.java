package com.nslb.firebasetest.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nslb.firebasetest.R;

public class TripTalkView extends Fragment {@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View view = inflater.inflate(R.layout.fragment_triptalkview,container,false);
    return view;
}
}
