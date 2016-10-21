package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.usaa.acaitp.bashscriptcrazy.russhanneman.PlaceDbSchema.PlaceTable;

import java.util.UUID;



/**
 * Created by christophercoffee on 10/20/16.
 */

public class RussHannCursorWrapper extends CursorWrapper

{
    public RussHannCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Places getPlace() {
        String id = getString(getColumnIndex(PlaceTable.Cols.ID));
        String name = getString(getColumnIndex(PlaceTable.Cols.NAME));
        String visits = getString(getColumnIndex(PlaceTable.Cols.VISITS));
        String time = getString(getColumnIndex(PlaceTable.Cols.TIME));
        String pic = getString(getColumnIndex(PlaceTable.Cols.PIC_LOCATION));
        String longitude = getString(getColumnIndex(PlaceTable.Cols.LONGITTUDE));
        String latitude = getString(getColumnIndex(PlaceTable.Cols.LATITUDE));

        Places place = new Places();
        place.setName(name);
        place.setVisits(visits);
        place.setPicLoc(pic);
        place.setLongitude(longitude);
        place.setLatitude(latitude);
        place.setTime(time);
        place.setId(UUID.fromString(id));


        return place;
    }
}

