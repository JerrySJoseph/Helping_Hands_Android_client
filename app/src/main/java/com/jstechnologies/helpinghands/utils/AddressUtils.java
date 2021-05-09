package com.jstechnologies.helpinghands.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.jstechnologies.helpinghands.data.model.Address;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AddressUtils {
    private static final int RC_LOCATION_PERMISSION = 102;

    @AfterPermissionGranted(RC_LOCATION_PERMISSION)
    public Address getAddressFromGps(Context context) throws IOException {

        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(context, perms)) {
            String provider;
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);


            @SuppressLint("MissingPermission")
            Location location = locationManager.getLastKnownLocation(provider);
            Geocoder geocoder= new Geocoder(context, Locale.getDefault());
            List<android.location.Address> addresses  = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            Address address= new Address();
            address.setLat(location.getLatitude());
            address.setLon(location.getLongitude());
            address.setCountry(addresses.get(0).getCountryName());
            address.setCity(addresses.get(0).getLocality());
            address.setState(addresses.get(0).getAdminArea());
            address.setPincode(addresses.get(0).getPostalCode());
            address.setAddressLine1(addresses.get(0).getAddressLine(0));
            address.setAddressLine2(addresses.get(0).getAddressLine(1));

            return address;
        } else {
            EasyPermissions.requestPermissions((Activity) context,"Location permissions are required",RC_LOCATION_PERMISSION,perms);
            return null;
        }

    }
}
