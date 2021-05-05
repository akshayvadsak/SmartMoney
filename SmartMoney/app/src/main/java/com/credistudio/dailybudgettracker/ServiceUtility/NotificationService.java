package com.credistudio.dailybudgettracker.ServiceUtility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import android.util.Log;

import androidx.annotation.RequiresApi;

import com.credistudio.dailybudgettracker.Activity.NotificationActivity;
import com.credistudio.dailybudgettracker.Classes.Expense;
import com.credistudio.dailybudgettracker.Classes.Notifications;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.R;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;

public class NotificationService extends Service {
    String bigtitle;
    @Inject
    DataManager dataManager;
    ArrayList<Expense> expenseList;
    Double fullExpense = Double.valueOf(0.0d);
    Double fullIncome = Double.valueOf(0.0d);
    ArrayList<Expense> incomeList;
    String title;

    public void onCreate() {
        super.onCreate();
        ((DaggerApplication) getApplication()).getComponent().inject(this);
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public int onStartCommand(Intent intent, int i, int i2) {
        String stringExtra = intent.getStringExtra("Type");
        try {
            this.expenseList = new ArrayList<>();
            this.incomeList = new ArrayList<>();
            this.expenseList.clear();
            this.incomeList.clear();
            this.expenseList = this.dataManager.getExpense();
            this.incomeList = this.dataManager.getIncome();
            if (this.expenseList != null) {
                for (int i3 = 0; i3 < this.expenseList.size(); i3++) {
                    Double valueOf = Double.valueOf(((Expense) this.expenseList.get(i3)).getAmount());
                    if (this.fullExpense.doubleValue() == 0.0d) {
                        this.fullExpense = valueOf;
                    } else {
                        this.fullExpense = Double.valueOf(this.fullExpense.doubleValue() + valueOf.doubleValue());
                    }
                }
            }
            if (this.incomeList != null) {
                for (int i4 = 0; i4 < this.incomeList.size(); i4++) {
                    Double valueOf2 = Double.valueOf(((Expense) this.incomeList.get(i4)).getAmount());
                    if (this.fullIncome.doubleValue() == 0.0d) {
                        this.fullIncome = valueOf2;
                    } else {
                        this.fullIncome = Double.valueOf(this.fullIncome.doubleValue() + valueOf2.doubleValue());
                    }
                }
            }
            if (!stringExtra.equals("Expense")) {
                this.title = "Dear user your Current Income Balance....";
                StringBuilder sb = new StringBuilder();
                sb.append("Your Income : ");
                sb.append(this.fullIncome);
                sb.append("\nYou should have to invest in mutual funds for more profit");
                this.bigtitle = sb.toString();
                PendingIntent activity = PendingIntent.getActivity(this, 1000, new Intent(this, NotificationActivity.class), 0);
                Log.e("Notification Service", "................if check..................");
                Notification.Builder largeIcon = new Notification.Builder(this).setContentTitle(this.title).setSmallIcon(R.mipmap.ic_app_logo).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_app_logo));
                StringBuilder sb2 = new StringBuilder();
                sb2.append("android.resource://");
                sb2.append(getPackageName());
                sb2.append("/");
                sb2.append(R.raw.my_notifi);
                Notification build = largeIcon.setSound(Uri.parse(sb2.toString())).setStyle(new Notification.BigTextStyle().bigText(this.bigtitle)).setContentIntent(activity).build();
                NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
                build.flags |= 16;
                notificationManager.notify(0, build);
                Calendar instance = Calendar.getInstance();
                PrintStream printStream = System.out;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Current time => ");
                sb3.append(instance.getTime());
                printStream.println(sb3.toString());
                String format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a").format(instance.getTime());
                Notifications notifications = new Notifications();
                notifications.setTitle(this.title);
                notifications.setMessage(this.bigtitle);
                notifications.setDate(format);
                this.dataManager.addNotification(notifications);
                Log.e("Notificstion Service", "...........Generated notification.......................");
            } else if (this.fullIncome.doubleValue() < this.fullExpense.doubleValue()) {
                this.title = "Dear user your expenses are more than your income....";
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Your Income : ");
                sb4.append(this.fullIncome);
                sb4.append("\nYour Expenses : ");
                sb4.append(this.fullExpense);
                this.bigtitle = sb4.toString();
                PendingIntent activity2 = PendingIntent.getActivity(this, 1000, new Intent(this, NotificationActivity.class), 0);
                Log.e("Notification Service", "................if check..................");
                Notification.Builder largeIcon2 = new Notification.Builder(this).setContentTitle(this.title).setSmallIcon(R.mipmap.ic_app_logo_round).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_app_logo));
                StringBuilder sb5 = new StringBuilder();
                sb5.append("android.resource://");
                sb5.append(getPackageName());
                sb5.append("/");
                sb5.append(R.raw.my_notifi);
                Notification build2 = largeIcon2.setSound(Uri.parse(sb5.toString())).setStyle(new Notification.BigTextStyle().bigText(this.bigtitle)).setContentIntent(activity2).build();
                NotificationManager notificationManager2 = (NotificationManager) getSystemService("notification");
                build2.flags |= 16;
                notificationManager2.notify(0, build2);
                Calendar instance2 = Calendar.getInstance();
                PrintStream printStream2 = System.out;
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Current time => ");
                sb6.append(instance2.getTime());
                printStream2.println(sb6.toString());
                String format2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a").format(instance2.getTime());
                Notifications notifications2 = new Notifications();
                notifications2.setTitle(this.title);
                notifications2.setMessage(this.bigtitle);
                notifications2.setDate(format2);
                this.dataManager.addNotification(notifications2);
                Log.e("Notificstion Service", "...........Generated notification.......................");
            }
            this.fullExpense = Double.valueOf(0.0d);
            this.fullIncome = Double.valueOf(0.0d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, i, i2);
    }
}
