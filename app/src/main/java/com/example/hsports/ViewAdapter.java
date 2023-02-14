package com.example.hsports;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private ArrayList<ViewModel> list;

    public ViewAdapter(ArrayList<ViewModel> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(list.get(position).name);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent("broadcast");
            intent.putExtra("name", list.get(position).name);
            LocalBroadcastManager.getInstance(holder.itemView.getContext()).sendBroadcast(intent);
        });
    }

    public void setItems(ArrayList<ViewModel> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.card_view_name);
        }
    }
}
