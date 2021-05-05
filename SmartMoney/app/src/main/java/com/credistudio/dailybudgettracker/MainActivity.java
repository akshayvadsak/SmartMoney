package com.credistudio.dailybudgettracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.credistudio.dailybudgettracker.Activity.CategoryManagementActivity;
import com.credistudio.dailybudgettracker.Activity.NotificationActivity;
import com.credistudio.dailybudgettracker.Activity.SettingsActivity;
import com.credistudio.dailybudgettracker.Database.DbImportExport;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends AppCompatActivity  {

    LinearLayout accsetting;
    LinearLayout catmement;
    LinearLayout more;
    LinearLayout Share;
    LinearLayout rate;
    LinearLayout privecy;
    LinearLayout notification;
    Toolbar toolbar_setting;
    MaterialDialog materialDialog_import;
    DbImportExport dbImportExport;
    MaterialDialog materialDialog_export;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.intersatital));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });


        this.toolbar_setting = (Toolbar) findViewById(R.id.toolbar_set);
        toolbar_setting.setTitleTextColor(getResources().getColor(R.color.white));
//        toolbar_setting.inflateMenu(R.menu.more);
        setSupportActionBar(this.toolbar_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.toolbar_setting.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        }
        this.toolbar_setting.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.onBackPressed();
            }
        });


        this.accsetting = (LinearLayout) findViewById(R.id.setting);
        this.catmement = (LinearLayout) findViewById(R.id.catmegment);
        this.more = (LinearLayout) findViewById(R.id.moreapps);
        this.rate = (LinearLayout) findViewById(R.id.rate);
        this.Share = (LinearLayout) findViewById(R.id.Share);
        this.privecy = (LinearLayout) findViewById(R.id.privecy);
        this.notification = (LinearLayout) findViewById(R.id.notification);

        this.accsetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.finish();
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
                mInterstitialAd.show();
            }
        });

        this.catmement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.finish();
                Intent intent = new Intent(MainActivity.this, CategoryManagementActivity.class);
                MainActivity.this.startActivity(intent);
                mInterstitialAd.show();
            }
        });

        this.more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Doroly+Studio")));
//                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=Crediture+App+Studio")));
//                }
            }
        });

        this.Share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + "\n\n" + "Download now!!:\n\n https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        this.rate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=" + getPackageName())));

            }
        });

        this.privecy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentPrivacy = new Intent(Intent.ACTION_VIEW, Uri.parse("https://crediture.wordpress.com/2020/02/12/55/"));
                startActivity(intentPrivacy);
            }


        });

        this.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                MainActivity.this.startActivity(intent);
                mInterstitialAd.show();


            }
        });


//        toolbar_setting.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                if (item.getItemId() == R.id.rate) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=" + getPackageName())));
//
//                } else if (item.getItemId() == R.id.share) {
//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + "\n\n" + "Download now!!:\n\n https://play.google.com/store/apps/details?id=" + getPackageName());
//                    sendIntent.setType("text/plain");
//                    startActivity(sendIntent);
//
//                } else if (item.getItemId() == R.id.moreapps) {
//                    try {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Doroly+Studio")));
//                    } catch (android.content.ActivityNotFoundException anfe) {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=Doroly+Studio")));
//                    }
//
//                } else if (item.getItemId() == R.id.praecy) {
//                    Intent intentPrivacy = new Intent(Intent.ACTION_VIEW, Uri.parse("http://161.117.83.193/privacypolicy/budgettracker.html"));
//                    startActivity(intentPrivacy);
//                }
//                return false;
//            }
//
//        });
//    }
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            MenuInflater inflater = getMenuInflater();
//            inflater.inflate(R.menu.more,menu);
//            return true;
//        }
//
//
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        return false;
    }
}
