package com.credistudio.dailybudgettracker.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.R;
import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.PatternLockView.Dot;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.android.gms.ads.InterstitialAd;
import io.paperdb.Paper;
import java.util.List;
import javax.inject.Inject;
import me.drakeet.materialdialog.MaterialDialog;

public class SplashLockActivity extends AppCompatActivity {
    Button btnSetPattern;
    String final_pattern = "";
    private InterstitialAd mInterstitialAd;
    /* access modifiers changed from: private */
    public PatternLockView mPatternLockView;
    MaterialDialog materialDialog_forgot_pattern;
    String save_pattern_key = "pattern_code";
    @Inject
    SharedPreferences sharedPreferences;
    TextView tvSplashLock_forgotPattern;

    /* access modifiers changed from: protected */
    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
//        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_splash_lock);
        this.mPatternLockView = (PatternLockView) findViewById(R.id.patter_lock_view);
        this.btnSetPattern = (Button) findViewById(R.id.btnSetPattern);
        this.tvSplashLock_forgotPattern = (TextView) findViewById(R.id.tvSplashLock_forgotPattern);
        ((DaggerApplication) getApplication()).getComponent().inject(this);
        Paper.init(this);
        final String str = (String) Paper.book().read(this.save_pattern_key);
        if (str == null || str.equals("null")) {
            this.btnSetPattern.setVisibility(0);
            this.tvSplashLock_forgotPattern.setVisibility(8);
            this.mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                public void onCleared() {
                }

                public void onProgress(List<Dot> list) {
                }

                public void onStarted() {
                }

                public void onComplete(List<Dot> list) {
                    SplashLockActivity.this.final_pattern = PatternLockUtils.patternToString(SplashLockActivity.this.mPatternLockView, list);
                }
            });
            this.btnSetPattern.setOnClickListener(new OnClickListener() {
                @SuppressLint("WrongConstant")
                public void onClick(View view) {
                    Paper.book().write(SplashLockActivity.this.save_pattern_key, SplashLockActivity.this.final_pattern);
                    Toast.makeText(SplashLockActivity.this, "Password has been saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashLockActivity.this, HomeActivity.class);
                    intent.putExtra("Status", 0);
                    SplashLockActivity.this.startActivity(intent);
                    SplashLockActivity.this.finish();
                }
            });
            return;
        }
        this.btnSetPattern.setVisibility(8);
        this.tvSplashLock_forgotPattern.setVisibility(0);
        this.mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            public void onCleared() {
            }

            public void onProgress(List<Dot> list) {
            }

            public void onStarted() {
            }

            public void onComplete(List<Dot> list) {
                SplashLockActivity.this.final_pattern = PatternLockUtils.patternToString(SplashLockActivity.this.mPatternLockView, list);
                if (SplashLockActivity.this.final_pattern.equals(str)) {
                    Intent intent = new Intent(SplashLockActivity.this, HomeActivity.class);
                    SplashLockActivity.this.startActivity(intent);
                    SplashLockActivity.this.finish();
                    Toast.makeText(SplashLockActivity.this, "Password is correct", Toast.LENGTH_SHORT).show();
                    intent.putExtra("Status", 1);
                    return;
                }
                Toast.makeText(SplashLockActivity.this, "Password is not correct",Toast.LENGTH_LONG).show();
            }
        });
        this.tvSplashLock_forgotPattern.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View inflate = ((LayoutInflater) SplashLockActivity.this.getSystemService("layout_inflater")).inflate(R.layout.item_forgot_pattern, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtForgotPattern_userName);
                SplashLockActivity.this.materialDialog_forgot_pattern = new MaterialDialog(SplashLockActivity.this).setTitle((CharSequence) "Forgot Pattern").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter User Name");
                        } else if (SplashLockActivity.this.sharedPreferences.getString("UserName", "").equals(editText.getText().toString().trim())) {
                            Intent intent = new Intent(SplashLockActivity.this, LockSettingActivity.class);
                            intent.putExtra("LockStatus", 1);
                            SplashLockActivity.this.startActivity(intent);
                            SplashLockActivity.this.finish();
                        } else {
                            editText.requestFocus();
                            Toast.makeText(SplashLockActivity.this, "Enter correct user name", 0).show();
                        }
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        SplashLockActivity.this.materialDialog_forgot_pattern.dismiss();
                    }
                });
                SplashLockActivity.this.materialDialog_forgot_pattern.show();
            }
        });
    }
}
