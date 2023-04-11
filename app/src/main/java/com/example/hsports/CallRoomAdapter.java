package com.example.hsports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CallRoomAdapter extends RecyclerView.Adapter<CallRoomAdapter.CallRoomHolder> {
    private Context context;

    private ArrayList<CallRoomModel> CallRoomArrayList;

    public CallRoomAdapter(Context context,ArrayList<CallRoomModel> CallRoomArrayList) {
        this.context = context;
        this.CallRoomArrayList = CallRoomArrayList;
    }

    @NonNull
    @Override
    public CallRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_room_card_layout, parent, false);
        return new CallRoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallRoomHolder holder, int position) {
        CallRoomModel model = CallRoomArrayList.get(position);

        holder.SetDetails(model);
    }

    @Override
    public int getItemCount() {
        return CallRoomArrayList.size();
    }

    class CallRoomHolder extends RecyclerView.ViewHolder{

//        private final ImageView BookingIcon;
        private final TextView Name;
        private final TextView Chest_number;
        private final TextView Event;

        CallRoomHolder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.card_booking_date);
            Chest_number = itemView.findViewById(R.id.card_booking_center);
            Event = itemView.findViewById(R.id.card_booking_approval_status);
        }

        void SetDetails(CallRoomModel details)
        {

            Name.setText(details.getName());
            Chest_number.setText(details.getChest_number());
            Event.setText(details.getEvent());
        }
    }
}



