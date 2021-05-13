package com.craftbid.craftbid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.craftbid.craftbid.CreateListingActivity;
import com.craftbid.craftbid.CreatorProfile;
import com.craftbid.craftbid.MainActivity;
import com.craftbid.craftbid.R;
import com.craftbid.craftbid.model.Thumbnail;
import com.google.android.material.snackbar.Snackbar;
import com.stelladk.arclib.ArcLayout;

import java.util.List;

public class CollectionRecyclerAdapter extends RecyclerView.Adapter<CollectionRecyclerAdapter.ViewHolder> {

    List<String> collection;

    public CollectionRecyclerAdapter(List<String> collection){
        this.collection = collection;
    }

    //Temporary
    private CreateListingActivity context = null;
    public CollectionRecyclerAdapter(List<String> collection, CreateListingActivity context) {
        this.collection = collection;
        this.context = context;
    }

    @NonNull
    @Override
    public CollectionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new CollectionRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionRecyclerAdapter.ViewHolder holder, int i) {
        if(i == collection.size()){
            holder.image.setImageResource(0);
            holder.plus_sign.setVisibility(View.VISIBLE);
            holder.textAdd.setVisibility(View.VISIBLE);
            //TODO OnClickListener for adding pictures
            return;
        }
        if(context != null){
            holder.image.setImageResource(context.getDrawable(collection.get(i)));
        }else{
            holder.image.setImageResource(R.drawable.chair1);
        }
    }

    @Override
    public int getItemCount() {
        return collection.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image, plus_sign;
        public TextView textAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            plus_sign = itemView.findViewById(R.id.plus_sign);
            textAdd = itemView.findViewById(R.id.textAdd);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Clicked on item " + getAbsoluteAdapterPosition(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}