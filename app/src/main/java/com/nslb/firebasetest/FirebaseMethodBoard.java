package com.nslb.firebasetest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.nslb.firebasetest.Interface.RecyclerAdapterInterface;
import com.nslb.firebasetest.ItemModel.BoardModel;
import com.nslb.firebasetest.ItemModel.CommentModel;
import com.nslb.firebasetest.ItemModel.UserInfoModel;
import com.nslb.firebasetest.UI.BoardViewHolder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FirebaseMethodBoard extends FirebaseMethods {

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapterEx mFirebaseRecyclerAdapterEx = null;
    private FirebaseRecyclerAdapter mF;
    private DatabaseReference mCommentsReference;

    public FirebaseMethodBoard(RecyclerAdapterInterface recyclerAdapterInterface){
        mFirebaseRecyclerAdapterEx = new FirebaseRecyclerAdapterEx<BoardModel, BoardViewHolder>(setBoardItem(), recyclerAdapterInterface);
    }

    public FirebaseRecyclerAdapterEx getAdapter(){
        return mFirebaseRecyclerAdapterEx;
    }

    public void clickStars(DatabaseReference databaseReference, BoardModel model){
        DatabaseReference globalPostRef = mDatabase.child("posts").child(databaseReference.getKey());
        DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(databaseReference.getKey());

        // Run two transactions
        onStarClicked(globalPostRef);
        onStarClicked(userPostRef);
    }

    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                BoardModel p = mutableData.getValue(BoardModel.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

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

    public void postComment(final String commentText, final String postKey) {
        final String uid = getUid();
        DatabaseAccess();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        UserInfoModel user = dataSnapshot.getValue(UserInfoModel.class);
                        String authorName = user.username;

                        // Create new comment object

                        CommentModel comment = new CommentModel(uid, authorName, commentText);

                        // Push the comment, it will appear in the list
                        mDatabase.child("post-comment").child(postKey).push().setValue(comment);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
