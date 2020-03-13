package com.nslb.firebasetest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.nslb.firebasetest.FirebaseMethodBoard;
import com.nslb.firebasetest.FirebaseRecyclerAdapterEx;
import com.nslb.firebasetest.Interface.RecyclerAdapterInterface;
import com.nslb.firebasetest.ItemModel.BoardModel;
import com.nslb.firebasetest.R;

public class TripTalkView extends Fragment implements RecyclerAdapterInterface {
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
//    private FirebaseRecyclerAdapterEx<BoardModel, BoardViewHolder> mAdapter;
    private FirebaseMethodBoard firebaseMethodBoard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_triptalkview,container,false);

        mRecycler = view.findViewById(R.id.boardList);
        mRecycler.setHasFixedSize(true);
        firebaseMethodBoard = new FirebaseMethodBoard(this);


        view.findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewBoardActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        //mAdapter = new FirebaseRecyclerAdapterEx<BoardModel, BoardViewHolder>(firebaseMethodBoard.setBoardItem(), this);

        /*mAdapter = new FirebaseRecyclerAdapter<BoardModel, BoardViewHolder>(firebaseMethodBoard.setBoardItem()) {
            @Override
            public BoardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new BoardViewHolder(inflater.inflate(R.layout.item_board, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder( BoardViewHolder viewHolder, int position, @NonNull BoardModel model) {

                if (model.stars.containsKey(firebaseMethodBoard.getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                }else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }
                viewHolder.bindToBoard(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        };*/
        mRecycler.setAdapter(firebaseMethodBoard.getAdapter());
    }
    @Override
    public void onStart() {
        super.onStart();
        if (firebaseMethodBoard.getAdapter() != null) {
            firebaseMethodBoard.getAdapter().startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseMethodBoard.getAdapter() != null) {
            firebaseMethodBoard.getAdapter().stopListening();
        }
    }

    @Override
    public void onViewHolderClick(View v) {

    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder viewholder, int position, final  @NonNull BoardModel model, final String key, final DatabaseReference databaseReference) {
        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch PostDetailActivity
                Intent intent = new Intent(getActivity(), BoardDetailActivity.class);
                intent.putExtra(BoardDetailActivity.EXTRA_POST_KEY, key);
                startActivity(intent);
            }
        });
        if (model.stars.containsKey(firebaseMethodBoard.getUid())) {
            viewholder.starView.setImageResource(R.drawable.ic_toggle_star_24);
        } else {
            viewholder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
        }
        viewholder.bindToBoard(model, new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                // Need to write to both places the post is stored
                firebaseMethodBoard.clickStars(databaseReference, model);
            }
        });
    }
}
