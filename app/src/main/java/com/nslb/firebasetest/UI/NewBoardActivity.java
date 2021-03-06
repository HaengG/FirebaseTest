package com.nslb.firebasetest.UI;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.nslb.firebasetest.FirebaseMethodBoard;
import com.nslb.firebasetest.Interface.RecyclerAdapterInterface;
import com.nslb.firebasetest.ItemModel.BoardModel;
import com.nslb.firebasetest.R;

public class NewBoardActivity extends AppCompatActivity implements RecyclerAdapterInterface {

    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;
    public FirebaseMethodBoard firebaseMethodBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);
        mSubmitButton = findViewById(R.id.fabSubmitPost);
        firebaseMethodBoard = new FirebaseMethodBoard(this);

        //firebaseMethodBoard.DatabaseAccess();


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleField.getText().toString();
                String body = mBodyField.getText().toString();

                if (TextUtils.isEmpty(title)){
                    mTitleField.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(body)){
                    mBodyField.setError("Required");
                    return;
                }
                firebaseMethodBoard.submitPost(title, body);
                finish();
            }
        });
    }

    @Override
    public void onViewHolderClick(View v) {

    }

    @Override
    public void onBindViewHolder(BoardViewHolder holder, int position, @NonNull BoardModel model, String key, DatabaseReference databaseReference) {

    }
}
