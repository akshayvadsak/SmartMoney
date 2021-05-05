package com.credistudio.dailybudgettracker.ServiceUtility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.credistudio.dailybudgettracker.R;


public class Util {
    private Dialog dialog1;

    public void showDialog(Context context) {
        this.dialog1 = new Dialog(context);
        this.dialog1.requestWindowFeature(1);
        this.dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog1.setTitle("Processing...");
        this.dialog1.setContentView(R.layout.custom_progress_dialog);
        this.dialog1.setCancelable(false);
        this.dialog1.setCanceledOnTouchOutside(false);
        this.dialog1.show();
        LayoutParams layoutParams = new LayoutParams();
        Window window = this.dialog1.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = -2;
        layoutParams.height = -2;
        window.setAttributes(layoutParams);
    }

    public void dismissDialog() {
        if (this.dialog1 != null && this.dialog1.isShowing()) {
            this.dialog1.dismiss();
        }
    }
}
