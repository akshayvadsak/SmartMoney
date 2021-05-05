package com.credistudio.dailybudgettracker.Database;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build.VERSION;
import android.os.Environment;

import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DbExportImport {
    protected static File DATABASE_DIRECTORY = null;
    public static final String DATABASE_NAME = "MyExpenseDB";
    public static final String DATABASE_TABLE = "entryTable";
    private static final File DATA_DIRECTORY_DATABASE;
    protected static File IMPORT_FILE = null;
    public static final String PACKAGE_NAME = "app.f5buddy.com.dailybudgettracker";
    public static final String TAG = "app.f5buddy.com.dailybudgettracker.Database.DbExportImport";
    protected static File exportDir;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getDataDirectory());
        sb.append("/data/");
        sb.append("app.f5buddy.com.dailybudgettracker");
        sb.append("/databases/");
        sb.append("MyExpenseDB");
        DATA_DIRECTORY_DATABASE = new File(sb.toString());
    }

    public static void createFolder(Context context) {
        if (isStoragePermissionGranted(context)) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append(File.separator);
            sb.append("SMoneyMan");
            DATABASE_DIRECTORY = new File(sb.toString());
            if (!DATABASE_DIRECTORY.exists()) {
                DATABASE_DIRECTORY.mkdir();
            }
            exportDir = DATABASE_DIRECTORY;
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            IMPORT_FILE = new File(DATABASE_DIRECTORY, "moneyMan.db");
            exportDb(context);
        }
    }

    public static boolean exportDb(Context context) {
        if (!SdIsPresent()) {
            return false;
        }
        File file = DATA_DIRECTORY_DATABASE;
        String str = "moneyMan.db";
        File file2 = null;
        if (exportDir != null) {
            file2 = new File(exportDir, str);
        }
        try {
            file2.createNewFile();
            copyFile(file, file2);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean restoreDb(Context context) {
        createFolder(context);
        if (!SdIsPresent()) {
            return false;
        }
        File file = DATA_DIRECTORY_DATABASE;
        File file2 = IMPORT_FILE;
        if (!checkDbIsValid(file2)) {
            return false;
        }
        if (!file2.exists()) {
            Log.d(TAG, "File does not exist");
            return false;
        }
        try {
            file.createNewFile();
            copyFile(file2, file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean importIntoDb(Context context) {
        if (!SdIsPresent()) {
            return false;
        }
        createFolder(context);
        File file = IMPORT_FILE;
        if (!checkDbIsValid(file)) {
            return false;
        }
        try {
            SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(file.getPath(), null, 1);
            DBHelper dBHelper = new DBHelper(context);
            openDatabase.close();
            dBHelper.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static boolean checkDbIsValid(File file) {
        try {
            SQLiteDatabase.openDatabase(file.getPath(), null, 1).close();
            return true;
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "Database valid but not the right type");
            e.printStackTrace();
            return false;
        } catch (SQLiteException e2) {
            Log.d(TAG, "Database file is invalid.");
            e2.printStackTrace();
            return false;
        } catch (Exception e3) {
            Log.d(TAG, "checkDbIsValid encountered an exception");
            e3.printStackTrace();
            return false;
        }
    }

    private static void copyFile(File file, File file2) throws IOException {
        FileChannel channel = new FileInputStream(file).getChannel();
        FileChannel channel2 = new FileOutputStream(file2).getChannel();
        try {
            channel.transferTo(0, channel.size(), channel2);
        } finally {
            if (channel != null) {
                channel.close();
            }
            if (channel2 != null) {
                channel2.close();
            }
        }
    }

    public static boolean SdIsPresent() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isStoragePermissionGranted(Context context) {
        if (VERSION.SDK_INT < 23 || context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        Log.e("No Permission", "...................................................................................................................");
        return false;
    }
}
