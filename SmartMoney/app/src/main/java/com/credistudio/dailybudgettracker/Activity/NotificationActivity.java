package com.credistudio.dailybudgettracker.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.credistudio.dailybudgettracker.Adapter.NotificationRVAdapter;
import com.credistudio.dailybudgettracker.Classes.Notifications;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.MainActivity;
import com.credistudio.dailybudgettracker.R;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.inject.Inject;

public class NotificationActivity extends AppCompatActivity {
    @Inject
    DataManager dataManager;
    LinearLayoutManager layoutManager;
    /* access modifiers changed from: private */
    public InterstitialAd mInterstitialAd;
    ArrayList<Notifications> notificationsArrayList;
    RecyclerView rcv_notification;
    Toolbar toolbar_notification;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_notification);
        this.rcv_notification = (RecyclerView) findViewById(R.id.rcv_notification);
        this.toolbar_notification = (Toolbar) findViewById(R.id.toolbar_notification);
        setSupportActionBar(this.toolbar_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar_notification.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        this.toolbar_notification.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NotificationActivity.this.onBackPressed();
            }
        });
        ((DaggerApplication) getApplication()).getComponent().inject(this);
        this.notificationsArrayList = new ArrayList<>();

        getNotification();
    }

    public void getNotification() {
        try {
            this.notificationsArrayList = this.dataManager.getNotification();
            if (this.notificationsArrayList != null) {
                Collections.sort(this.notificationsArrayList, new Comparator<Notifications>() {
                    DateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");

                    public int compare(Notifications notifications, Notifications notifications2) {
                        try {
                            return this.f.parse(notifications.getDate()).compareTo(this.f.parse(notifications2.getDate()));
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                Collections.reverse(this.notificationsArrayList);
                this.layoutManager = new LinearLayoutManager(this);
                this.layoutManager.setOrientation(1);
                this.rcv_notification.setLayoutManager(this.layoutManager);
                this.rcv_notification.setAdapter(new NotificationRVAdapter(this, this.notificationsArrayList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}
