package com.jstechnologies.helpinghands.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;

import java.util.Objects;

public class Address {

    String state;
    @ColumnInfo(name = "address_line1")
    String addressLine1;
    @ColumnInfo(name = "address_line2")
    String addressLine2;
    String city;
    String pincode;
    double lat,lon;
    @ColumnInfo(name = "land_mark")
    String landMark;
    String country;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getShortAddress()
    {
        return city+", "+state;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Double.compare(address.lat, lat) == 0 &&
                Double.compare(address.lon, lon) == 0 &&
                Objects.equals(state, address.state) &&
                Objects.equals(addressLine1, address.addressLine1) &&
                Objects.equals(addressLine2, address.addressLine2) &&
                Objects.equals(city, address.city) &&
                Objects.equals(pincode, address.pincode) &&
                Objects.equals(landMark, address.landMark);
    }

    @Override
    public String toString() {
        return addressLine1+", "+addressLine2+", "+city+", "+state+"-"+pincode+", "+country;
    }
}
