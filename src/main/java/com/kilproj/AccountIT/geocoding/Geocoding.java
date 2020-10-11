package com.kilproj.AccountIT.geocoding;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * An interface modelling geocoding operations.
 */
public interface Geocoding {

    /**
     * Gets the short name of a district from its latitude and longitude.
     *
     * @param latitude  the district latitude
     * @param longitude the district longitude
     * @return the district short name
     * @throws GeocodingException if an error occur while executing geocoding operations
     */
    JsonArray getDistrict(double latitude, double longitude) throws GeocodingException;

    /**
     * Gets latitude and longitude of a specified address.
     *
     * @param address the address
     * @return a JsonObject containing the address latitude and longitude
     * @throws GeocodingException if an error occur while executing geocoding operations
     */
    JsonObject getLatLng(String address) throws GeocodingException;


    /**
     * Gets gets distance between two postcodes in km or miles.
     *
     * @param lat1,lon1 the address1
     * @param lat2,lon2 the address2
     * @return the distance in km or miles
     * @throws GeocodingException if an error occur while executing geocoding operations
     */
    double getDistance(float lat1, float lon1, float lat2, float lon2, String unit) throws GeocodingException;;
}