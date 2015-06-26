package com.example.lutfihabiba.dietchartfinal1;

/**
 * Created by Lutfi Habiba on 26/06/2015.
 */
public class DietChart {
    public int _id;
    public String _catagory;
    public String _date;
    public String _time;
    public String _catagoryValue;

    public DietChart() {
    }

    // constructor
    public DietChart(int id, String catagory, String _date, String _time,String _catagoryValue) {
        this._id = id;
        this._catagory = catagory;
        this._date = _date;
        this._time = _time;
        this._catagoryValue=_catagoryValue;

    }
    // constructor
    public DietChart(String catagory, String _date, String _time,String _catagoryValue) {
        this._catagory = catagory;
        this._date = _date;
        this._time = _time;
        this._catagoryValue=_catagoryValue;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_catagory() {
        return _catagory;
    }

    public void set_catagory(String _catagory) {
        this._catagory = _catagory;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_catagoryValue() {
        return _catagoryValue;
    }

    public void set_catagoryValue(String _catagoryValue) {
        this._catagoryValue = _catagoryValue;
    }



}
