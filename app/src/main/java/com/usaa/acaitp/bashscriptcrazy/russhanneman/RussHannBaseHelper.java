package com.usaa.acaitp.bashscriptcrazy.russhanneman;

/**
 * Created by christophercoffee on 10/20/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.usaa.acaitp.bashscriptcrazy.russhanneman.PlaceDbSchema.PlaceTable;

/**
 * Created by christophercoffee on 10/12/16.
 */

public class RussHannBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "RussHannBaseHelper";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "russHannBase.db";

    public RussHannBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + PlaceTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                PlaceTable.Cols.ID + ", " +
                PlaceTable.Cols.NAME + ", " +
                PlaceTable.Cols.LATITUDE + ", " +
                PlaceTable.Cols.LONGITTUDE + ", " +
                PlaceTable.Cols.VISITS + ", " +
                PlaceTable.Cols.TIME + ", " +
                PlaceTable.Cols.PIC_LOCATION +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
