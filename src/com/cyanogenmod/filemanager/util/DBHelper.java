/*
 * Copyright (C) 2015 The SudaMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyanogenmod.filemanager.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static String DB_PATH = "/data/data/com.cyanogenmod.filemanager/databases/";

    private static String SETTINS = "dir_settings";
    private SharedPreferences preferences;
    private Editor editor;

    private static String DB_NAME = "app_dir_name.db";
    private static String ASSETS_NAME = "app_dir_name.db";

    private SQLiteDatabase myDataBase = null;
    private final Context myContext;

    public DBHelper(Context context, String name, CursorFactory factory,
            int version) {
        super(context, name, null, version);
        this.myContext = context;
    }

    public DBHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DBHelper(Context context, String name) {
        this(context, name, DB_VERSION);
    }

    public DBHelper(Context context) {
        this(context, DB_PATH + DB_NAME);
        preferences = context.getSharedPreferences(SETTINS, context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            try {
                File dir = new File(DB_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File dbf = new File(DB_PATH + DB_NAME);
                if (dbf.exists()) {
                    dbf.delete();
                }
                SQLiteDatabase.openOrCreateDatabase(dbf, null);
                copyDataBase();
                editor.putInt("version", DB_VERSION);
                editor.commit();
            } catch (IOException e) {
                throw new Error("");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;
        boolean isOldVersion = (preferences.getInt("version", 0) < DB_VERSION);
        try {
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return (checkDB != null && !isOldVersion) ? true : false;
    }

    public void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(ASSETS_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
