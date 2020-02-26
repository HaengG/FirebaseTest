package com.nslb.firebasetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.nslb.firebasetest.UI.SectionsPagerAdapter;
import com.nslb.firebasetest.UI.SignInActivity;

public class MainActivity extends AppCompatActivity {
    public FirebaseMethodUserSettings firebaseMethodUserSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseMethodUserSettings= new FirebaseMethodUserSettings();
        if (firebaseMethodUserSettings.CheckAuth() == 0){
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this , getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        for (int i = 0; i < sectionsPagerAdapter.getCount(); i++)
        {
            tabs.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    private int[] tabIcons={
            R.drawable.ic_person_black_24dp,
            R.drawable.ic_message_black_24dp,
            R.drawable.ic_store_black_24dp,
            R.drawable.ic_local_hospital_black_24dp,
            R.drawable.ic_settings_black_24dp
    };
}
