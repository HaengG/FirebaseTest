package com.nslb.firebasetest.ItemModel;

public class CommentModel {
    public String uid;
    public String author;
    public String text;

    public CommentModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }
    public CommentModel(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }
}
