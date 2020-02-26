package com.nslb.firebasetest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nslb.firebasetest.Interface.SignInListener;
import com.nslb.firebasetest.ItemModel.UserInfoModel;

import static android.content.ContentValues.TAG;

public class FirebaseMethodUserSettings extends FirebaseMethods {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private SignInListener signInListener = null;
    boolean check = false;


    public int CheckAuth(){
        DatabaseAccess();
        if(mAuth.getCurrentUser() != null){
            onAuthSuccess(mAuth.getCurrentUser());
            return 1;
        }else
        return 0;
    }

    public void signIn(String Email, String Password) {
        Log.d(TAG, "signIn");
        /*if (!validateForm()) {
            return;
        }*/

        final String fEmail = Email;
        final String fPassword = Password;

        mAuth.signInWithEmailAndPassword(fEmail, fPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                    signInListener.SignSuccess();
                }else {
                    signInListener.SignFail();
                }
            }
        });
    }



    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public void signUp(String Email, String Password) {
        Log.d(TAG, "signUp");
        /*if (!validateForm()) {
            return;
        }*/

        final String fEmail = Email;
        final String fPassword = Password;

        mAuth.createUserWithEmailAndPassword(fEmail, fPassword);
    }

    public void onSignInListener(SignInListener listener){
        this.signInListener = listener;
    }

    private void onAuthSuccess(FirebaseUser user){
        String username = usernameFromEmail(user.getEmail());

        writeNewUser(user.getUid(), username, user.getEmail());
    }

    private void writeNewUser(String userId, String name, String email) {
        UserInfoModel user = new UserInfoModel(name,email);
        mDatabase.child("users").child(userId).setValue(user);
    }



    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    @Override
    public void DatabaseAccess() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void UploadData(String userId, String username, String title, String body) {

    }

}
