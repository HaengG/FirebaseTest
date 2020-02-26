package com.nslb.firebasetest;

import com.google.firebase.auth.FirebaseAuth;

public abstract class FirebaseMethods {
    public void AccountAuthentication(){
        //계정 인증
    }
    public abstract void DatabaseAccess();//데이터베이스 접근
    public abstract void UploadData(String userId, String username, String title, String body);//업로드 불러오기

    public String getUid(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
