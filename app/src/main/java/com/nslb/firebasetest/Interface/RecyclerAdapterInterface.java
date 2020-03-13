package com.nslb.firebasetest.Interface;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.nslb.firebasetest.ItemModel.BoardModel;
import com.nslb.firebasetest.UI.BoardViewHolder;

public interface RecyclerAdapterInterface {
    void onViewHolderClick(View v);
    void onBindViewHolder(BoardViewHolder holder, int position, @NonNull BoardModel model, String key, DatabaseReference databaseReference);
}
