package com.example.hsports;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RefereeViewAdapter extends RecyclerView.Adapter<RefereeViewAdapter.ViewHolder> {

    private ArrayList<RefereeViewModel> refereeList;

    public RefereeViewAdapter(ArrayList<RefereeViewModel> refereeList) {
        this.refereeList = refereeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.referee_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(refereeList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return refereeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.referee_card_view_name);
        }
    }
}
