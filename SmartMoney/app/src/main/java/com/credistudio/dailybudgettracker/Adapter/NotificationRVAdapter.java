package com.credistudio.dailybudgettracker.Adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.credistudio.dailybudgettracker.Classes.Notifications;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.R;
import java.util.ArrayList;
import javax.inject.Inject;

public class NotificationRVAdapter extends RecyclerView.Adapter<NotificationRVAdapter.MyViewHolder> {
    Context context;
    @Inject
    DataManager dataManager;
    ArrayList<Notifications> notificationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotification_date;
        TextView tvNotification_message;
        TextView tvNotification_time;
        TextView tvNotification_title;

        public MyViewHolder(View view) {
            super(view);
            this.tvNotification_date = (TextView) view.findViewById(R.id.tvNotification_date);
            this.tvNotification_title = (TextView) view.findViewById(R.id.tvNotification_title);
            this.tvNotification_message = (TextView) view.findViewById(R.id.tvNotification_message);
            this.tvNotification_time = (TextView) view.findViewById(R.id.tvNotification_time);
        }
    }

    public NotificationRVAdapter(Context context2, ArrayList<Notifications> arrayList) {
        this.context = context2;
        this.notificationList = arrayList;
        DaggerApplication.get(context2).getComponent().inject(this);
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, null));
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String[] split = ((Notifications) this.notificationList.get(i)).getDate().split("\\s+");
        myViewHolder.tvNotification_date.setText(split[0]);
        myViewHolder.tvNotification_time.setText(split[1]);
        myViewHolder.tvNotification_title.setText(((Notifications) this.notificationList.get(i)).getTitle());
        myViewHolder.tvNotification_message.setText(((Notifications) this.notificationList.get(i)).getMessage());
        StringBuilder sb = new StringBuilder();
        sb.append("...........");
        sb.append(i);
        sb.append("............");
        Log.e("Notification Rv Adapter", sb.toString());
        if (i > 49) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("...........");
            sb2.append(i);
            sb2.append("......>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>......");
            Log.e("Notification Rv Adapter", sb2.toString());
            try {
                this.dataManager.deleteNotification(((Notifications) this.notificationList.get(i)).getId());
                this.notificationList.remove(i);
                notifyItemRemoved(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getItemCount() {
        return this.notificationList.size();
    }
}
