package com.nslb.firebasetest.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.nslb.firebasetest.FirebaseMethodBoard;
import com.nslb.firebasetest.Interface.RecyclerAdapterInterface;
import com.nslb.firebasetest.ItemModel.BoardModel;
import com.nslb.firebasetest.R;

public class BoardDetailActivity extends AppCompatActivity implements View.OnClickListener, RecyclerAdapterInterface {
    private String mPostKey;
    public static final String EXTRA_POST_KEY = "post_key";
    private Button mCommentButton;
    private FirebaseMethodBoard firebaseMethodBoard;
    private EditText mCommentField;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        firebaseMethodBoard = new FirebaseMethodBoard(this);
        mCommentButton = findViewById(R.id.buttonPostComment);
        mCommentField = findViewById(R.id.fieldCommentText);


        mCommentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==R.id.buttonPostComment){
            String commentText = mCommentField.getText().toString();
            firebaseMethodBoard.postComment(commentText, mPostKey);
            mCommentField.setText(null);
        }
    }

    @Override
    public void onViewHolderClick(View v) {

    }

    @Override
    public void onBindViewHolder(BoardViewHolder holder, int position, @NonNull BoardModel model, String key, DatabaseReference databaseReference) {

    }
}
