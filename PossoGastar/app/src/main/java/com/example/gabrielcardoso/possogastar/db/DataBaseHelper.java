package com.example.gabrielcardoso.possogastar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gabrielcardoso.possogastar.R;
import com.example.gabrielcardoso.possogastar.model.BaseAccount;
import com.example.gabrielcardoso.possogastar.model.BasePaymentMethod;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;


/**
 * Created by dfavato on 19/11/16.
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "possoGastar.db";
    private static final int DATABASE_VERSION = 1;

    //Classes que devem ser transformadas em tabelas
    public static final Class<?>[] CLASSES = new Class[] {
            BaseAccount.class,
            BasePaymentMethod.class
    };

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onCreate");
            for (Class c: CLASSES) {
                Log.i(DataBaseHelper.class.getName(), "Tabela " + c.getName() + " criada.");
                TableUtils.createTable(connectionSource, c);
            }
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onUpdate");
            for (Class c : CLASSES) {
                TableUtils.dropTable(connectionSource, c, true);
            }
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't update databse", e);
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
