package com.credistudio.dailybudgettracker.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.PatternLockView.Dot;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.credistudio.dailybudgettracker.R;

import io.paperdb.Paper;
import java.util.List;

public class LockSettingActivity extends AppCompatActivity {
    Button btnLockSetting_cancel;
    Button btnLockSetting_set;
    String final_pattern = "";
    PatternLockView patter_lock_view;
    String save_pattern_key = "pattern_code";
    int status = 0;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView((int) R.layout.activity_lock_setting);
        this.btnLockSetting_cancel = (Button) findViewById(R.id.btnLockSetting_cancel);
        this.btnLockSetting_set = (Button) findViewById(R.id.btnLockSetting_set);
        this.patter_lock_view = (PatternLockView) findViewById(R.id.patter_lock_view);
        this.status = getIntent().getIntExtra("LockStatus", 0);
        this.patter_lock_view.addPatternLockListener(new PatternLockViewListener() {
            public void onCleared() {
            }

            public void onProgress(List<Dot> list) {
            }

            public void onStarted() {
            }

            public void onComplete(List<Dot> list) {
                LockSettingActivity.this.final_pattern = PatternLockUtils.patternToString(LockSettingActivity.this.patter_lock_view, list);
                StringBuilder sb = new StringBuilder();
                sb.append(".....................");
                sb.append(LockSettingActivity.this.final_pattern);
                sb.append("................-------------------------final_patternLock-----------------------------");
                Log.e("Lock Setting", sb.toString());
            }
        });
        this.btnLockSetting_set.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Paper.book().write(LockSettingActivity.this.save_pattern_key, LockSettingActivity.this.final_pattern);
                Toast.makeText(LockSettingActivity.this, "Pattern has been saved", 1).show();
                if (LockSettingActivity.this.status == 1) {
                    LockSettingActivity.this.finish();
                    LockSettingActivity.this.startActivity(new Intent(LockSettingActivity.this, HomeActivity.class));
                    return;
                }
                LockSettingActivity.this.finish();
                LockSettingActivity.this.startActivity(new Intent(LockSettingActivity.this, SettingsActivity.class));
            }
        });
        this.btnLockSetting_cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (LockSettingActivity.this.status == 1) {
                    LockSettingActivity.this.finish();
                    LockSettingActivity.this.startActivity(new Intent(LockSettingActivity.this, SplashLockActivity.class));
                    return;
                }
                LockSettingActivity.this.finish();
                LockSettingActivity.this.startActivity(new Intent(LockSettingActivity.this, SettingsActivity.class));
            }
        });
    }
}
