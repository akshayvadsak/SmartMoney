package com.credistudio.dailybudgettracker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DbImportExport {
    protected static File DATABASE_DIRECTORY = null;
    public static final String DATABASE_NAME = "MyExpenseDB";
    public static final String DATABASE_TABLE = "entryTable";
    private static final File DATA_DIRECTORY_DATABASE;
    protected static final File IMPORT_FILE = new File(DATABASE_DIRECTORY, "appData.db");
    public static final String PACKAGE_NAME = "app.f5buddy.com.dailybudgettracker";
    public static final String TAG = "app.f5buddy.com.dailybudgettracker.Database.DbImportExport";

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append("SmartMoneyman");
        DATABASE_DIRECTORY = new File(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getDataDirectory());
        sb2.append("/data/");
        sb2.append("app.f5buddy.com.dailybudgettracker");
        sb2.append("/databases/");
        sb2.append("MyExpenseDB");
        DATA_DIRECTORY_DATABASE = new File(sb2.toString());
    }

    public static boolean exportDb(Context context) {
        if (!SdIsPresent()) {
            return false;
        }
        File file = DATA_DIRECTORY_DATABASE;
        String str = "appData.db";
        File file2 = DATABASE_DIRECTORY;
        if (!file2.exists()) {
            file2.mkdirs();
            Log.e("DB_EXPORT", "..........No File.............");
        }
        File file3 = new File(file2, str);
        if (file2.exists()) {
            file2.delete();
        }
        if (!file2.exists()) {
            file2.mkdirs();
            Log.e("DB_EXPORT", "..........No File.....EXISTS................");
        }
        try {
            file3.createNewFile();
            copyFile(file, file3);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean restoreDb(Context context) {
        try {
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
        File file = IMPORT_FILE;
        if (!file.exists()) {
            Toast.makeText(context, "Data file does not found", 0).show();
        } else if (!checkDbIsValid(file)) {
            return false;
        } else {
            try {
                SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(file.getPath(), null, 1);
                DBHelper dBHelper = new DBHelper(context);
                openDatabase.close();
                dBHelper.close();
                Toast.makeText(context, "Data has been successfully import", 0).show();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    protected static boolean checkDbIsValid(File file) {
        if (file != null) {
            try {
                SQLiteDatabase.openDatabase(file.getPath(), null, 1).close();
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
        return true;
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
}
