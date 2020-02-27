package com.nslb.firebasetest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nslb.firebasetest.ItemModel.BoardModel;
import com.nslb.firebasetest.ItemModel.UserInfoModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FirebaseMethodBoard extends FirebaseMethods {

    private DatabaseReference mDatabase;


    public void submitPost(String title, String body){
        final String ftitle = title;
        final String fbody = body;
        final String userUid = getUid();
        DatabaseAccess();
        mDatabase.child("users").child(userUid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserInfoModel user = dataSnapshot.getValue(UserInfoModel.class);

                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userUid + " is unexpectedly null");
                        } else {
                            // Write new post
                            UploadData(userUid, user.username, ftitle, fbody);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    public FirebaseRecyclerOptions setBoardItem(){
        DatabaseAccess();
        Query postQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<BoardModel>()
                .setQuery(postQuery,BoardModel.class)
                .build();

        return options;
    }

    public void setComments(int position, String comment) {

    }

    /*public ArrayList<Comment> getComments(){
        mDatabaseReference.getKey();
    }*/
    public DatabaseReference mDatabaseReference = null;
    public void getDatabaseRef(DatabaseReference ref){
        this.mDatabaseReference = ref;
    }

    @Override
    public void DatabaseAccess() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void UploadData(String userId, String username, String title, String body) {
        String key = mDatabase.child("posts").push().getKey();
        BoardModel post = new BoardModel(userId, username, title, body);

        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/"+key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    public Query getQuery(DatabaseReference databaseReference) {
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);

        return recentPostsQuery;
    }
}
