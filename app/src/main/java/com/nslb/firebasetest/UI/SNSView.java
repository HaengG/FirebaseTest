package com.nslb.firebasetest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nslb.firebasetest.FirebaseMethodUserSettings;
import com.nslb.firebasetest.R;

public class SNSView extends Fragment {
    public FirebaseMethodUserSettings firebaseMethodUserSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        firebaseMethodUserSettings= new FirebaseMethodUserSettings();
        View view = inflater.inflate(R.layout.fragment_snsview,container,false);

        view.findViewById(R.id.bt_Logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseMethodUserSettings.signOut();
                startActivity(new Intent(getActivity(), SignInActivity.class));
            }
        });
        return view;


    }
}
