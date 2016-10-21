package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.usaa.acaitp.bashscriptcrazy.russhanneman.PlaceDbSchema.PlaceTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christophercoffee on 10/20/16.
 */

public class Place_crud {
    private static Place_crud sUser_crud;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Place_crud get(Context context) {
        if (sUser_crud == null) {
            sUser_crud = new Place_crud(context);
        }
        return sUser_crud;
    }

    private Place_crud(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new RussHannBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Places place) {
        ContentValues values = new ContentValues();
        values.put(PlaceTable.Cols.ID, place.getId().toString());
        values.put(PlaceTable.Cols.NAME, place.getName());
        values.put(PlaceTable.Cols.LATITUDE, place.getLatitude());
        values.put(PlaceTable.Cols.LONGITTUDE, place.getLongitude());
        values.put(PlaceTable.Cols.VISITS, place.getVisits());
        values.put(PlaceTable.Cols.TIME, place.getTime());
        values.put(PlaceTable.Cols.PIC_LOCATION, place.getPicLoc());

        return values;
    }

    public void addMember(Places c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(PlaceTable.NAME, null, values);
    }

    public void updateMember(Places place) {
        String IDString = place.getId().toString();
        ContentValues values = getContentValues(place);

        mDatabase.update(PlaceTable.NAME, values,
                PlaceTable.Cols.ID + " = ?",
                new String[] { IDString });
    }

    public void deleteMember(Places place) {
        String IDString = place.getId().toString();
        mDatabase.delete(PlaceTable.NAME, PlaceTable.Cols.ID + " = ? ",
                new String[] { IDString });

    }

    private RussHannCursorWrapper queryMembers(String whereClause, String[] whereArgs, String orderBy) {
        Cursor cursor = mDatabase.query(
                PlaceTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                orderBy  // orderBy
        );

        return new RussHannCursorWrapper(cursor);
    }


    public List<Places> getMembers(String whereClause, String[] whereArgs, String orderBy) {
        List<Places> members = new ArrayList<>();

        RussHannCursorWrapper cursor = queryMembers(whereClause, whereArgs, orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            members.add(cursor.getPlace());
            cursor.moveToNext();
        }
        cursor.close();

        return members;
    }

    public Places getMember(Integer id) {
        RussHannCursorWrapper cursor = queryMembers(
                PlaceTable.Cols.ID + " = ?",
                new String[] { id.toString() }, null
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPlace();
        } finally {
            cursor.close();
        }
    }
}
