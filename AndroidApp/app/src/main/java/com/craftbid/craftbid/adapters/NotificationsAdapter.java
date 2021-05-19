package com.craftbid.craftbid.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.craftbid.craftbid.CreatorProfile;
import com.craftbid.craftbid.MainActivity;
import com.craftbid.craftbid.NotificationsActivity;
import com.craftbid.craftbid.PurchaseActivity;
import com.craftbid.craftbid.R;
import com.craftbid.craftbid.model.Thumbnail;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<Thumbnail> notifications;

    public NotificationsAdapter(List<Thumbnail> notifications) {
        this.notifications = notifications;
    }

    //Temporary
    private NotificationsActivity context = null;
    public NotificationsAdapter(List<Thumbnail> notifications, NotificationsActivity context) {
        this.notifications = notifications;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int i) {
        if(context != null){
            holder.thumbnail.setImageResource(context.getDrawable(notifications.get(i).getThumbnail()));
        }else{
            holder.thumbnail.setImageResource(R.drawable.chair1);
        }
//        holder.notif_text.setText();
        holder.final_price.setText(notifications.get(i).getMin_price()+"");
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView thumbnail;
        public TextView notif_text, final_price;
        public Button continue_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            notif_text = itemView.findViewById(R.id.notif_text);
            final_price = itemView.findViewById(R.id.final_price);
            continue_btn = itemView.findViewById(R.id.continue_btn);

            continue_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*Snackbar.make(view, "Clicked on item " + getAbsoluteAdapterPosition(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/
            final Thumbnail notification = notifications.get(getAbsoluteAdapterPosition());
            context.purchase(notification);
        }
    }
}