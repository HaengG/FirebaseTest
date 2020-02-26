package com.nslb.firebasetest;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nslb.firebasetest.ItemModel.BoardModel;
import com.nslb.firebasetest.ItemModel.UserInfoModel;

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
}
