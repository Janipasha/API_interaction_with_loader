package com.example.android.quakereport;

/**
 * Created by Jani on 06-12-2016.
 */

    public class earthquakes {

        private String mCityName;
        private double mMag;
        private String mDate;
        private String mtime;
        private String mURL;

        public earthquakes(String cityName,double Mag,String Date, String Time, String url){
            mCityName = cityName;
            mMag = Mag;
            mDate = Date;
            mtime = Time;
            mURL = url;
        }

        public String getCityName(){return mCityName;}
        public double getMag(){return mMag;}
        public String getDate(){return mDate;}
        public String getTime(){return mtime;}
        public String getURL(){return mURL;}
    }
