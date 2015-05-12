package me.cassiano.lddm_tp3.models;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by matheus on 5/7/15.
 */
public class Shake extends SugarRecord<Shake> {

    double latitude;
    double longitude;
    Date date;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Date getDate() {
        return date;
    }

    public Shake() {

    }

    public Shake(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = new Date();
    }



}
