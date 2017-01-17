package com.mydrinksclub.ui.notifications;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mydrinksclub.R;
import com.mydrinksclub.model.Notification;

import java.util.List;

/**
 * Created by ERD on 02/11/2016.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.VersionViewHolder> {

    private List<Notification> merchantList;
    private int rowLayout;
    Context context;
    OnItemClickListener clickListener;

    public NotificationAdapter(List<Notification> login, int rowLayout, Context context, OnItemClickListener listener) {
        this.merchantList = login;
        this.rowLayout = rowLayout;
        this.context = context;
        this.clickListener = listener;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_notifications, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        final Notification model = merchantList.get(i);
        versionViewHolder.bind(model, clickListener);
    }

    @Override
    public int getItemCount() {
        return merchantList == null ? 0 : merchantList.size();
    }

    public void animateTo(List<Notification> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Notification> newModels) {
        for (int i = merchantList.size() - 1; i >= 0; i--) {
            final Notification model = merchantList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Notification> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Notification model = newModels.get(i);
            if (!merchantList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Notification> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Notification model = newModels.get(toPosition);
            final int fromPosition = merchantList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Notification removeItem(int position) {
        final Notification model = merchantList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Notification model) {
        merchantList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Notification model = merchantList.remove(fromPosition);
        merchantList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    class VersionViewHolder extends RecyclerView.ViewHolder {
        CardView cardStore;
        TextView txtNotifications;

        public VersionViewHolder(View itemView) {
            super(itemView);

            txtNotifications = (TextView) itemView.findViewById(R.id.txt_notification);

        }

        public void bind(final Notification model, final OnItemClickListener listener) {
            txtNotifications.setText( model.message);

            txtNotifications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);

                }
            });
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(Notification model);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}