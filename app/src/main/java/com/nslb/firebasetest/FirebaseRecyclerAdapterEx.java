package com.nslb.firebasetest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.nslb.firebasetest.Interface.RecyclerAdapterInterface;
import com.nslb.firebasetest.ItemModel.BoardModel;
import com.nslb.firebasetest.UI.BoardViewHolder;

public class FirebaseRecyclerAdapterEx <T, VH extends RecyclerView.ViewHolder> extends FirebaseRecyclerAdapter {
    private RecyclerAdapterInterface recyclerAdapterInterface = null;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public String key = "";
    public DatabaseReference boardRef = null;
    public FirebaseRecyclerAdapterEx(@NonNull FirebaseRecyclerOptions options, RecyclerAdapterInterface recyclerAdapterInterface) {
        super(options);
        this.recyclerAdapterInterface = recyclerAdapterInterface;
    }

    //public void onRecyclerAdapterInterface(RecyclerAdapterInterface recyclerAdapterInterface){}

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Object model) {
        DatabaseReference boardRef = getRef(position);
        key = boardRef.getKey();
        recyclerAdapterInterface.onBindViewHolder((BoardViewHolder)holder,position,(BoardModel)model,key, boardRef);


    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new BoardViewHolder(inflater.inflate(R.layout.item_board, viewGroup, false));
    }
}
