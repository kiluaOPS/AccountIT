package com.kilproj.AccountIT.geocoding;

import com.google.gson.*;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * Geocoding implementation class.
 */
public class GeocodingImpl implements Geocoding {

    private static final String KEY = "AIzaSyCPoX9d6IA5elU9fw2hILwAms-3cn35c9I";
    private static final String AREA = "ADMINISTRATIVE_AREA_LEVEL_2";
    private static final String COUNTRY = "COUNTRY";
    private static final String UK_SHORT_NAME = "UK";
    private static final String TYPES = "types";
    private static final String SHORT_NAME = "shortName";
    private String response;
    private String district = "";

    public GeocodingImpl() {
    }

    @Override
    public JsonArray getDistrict(final double latitude, final double longitude) throws GeocodingException {

//        boolean inItaly = false;

        try {
            this.reverseGeocode(latitude, longitude);
        } catch (final Exception e) {
            throw new GeocodingException();
        }

        final JsonParser parser = new JsonParser();
        final JsonElement jsonTree = parser.parse(this.response);
        final JsonArray array = jsonTree.getAsJsonArray();
//        for (int i = 0; i < array.size(); i++) {
//            final JsonObject object = (JsonObject) array.get(i);
//            final Object type = object.get(TYPES);
//            final String typeString = type.toString();
//            if (typeString.contains(AREA)) {
//                this.district = object.get(SHORT_NAME).toString().substring(1, 3);
//            }
////            if (typeString.contains(COUNTRY) && object.get(SHORT_NAME).toString().substring(1, 3).equals(UK_SHORT_NAME)) {
////                inItaly = true;
////            }
//        }

//        if (!inItaly) {
//            throw new GeocodingException();
//        }

        return array;
    }

    @Override
    public JsonObject getLatLng(final String address) throws GeocodingException {

        try {
            this.geocode(address);
        } catch (final Exception e) {
            throw new GeocodingException();
        }

        final JsonObject json = new JsonParser().parse(this.response).getAsJsonObject();
        return json;

    }

    @Override
    public double getDistance(float lat1, float lon1, float lat2, float lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K")
                dist = dist * 1.609344;
            return dist;
        }
    }

    /**
     * Sends a reverse geocode request obtaining the district identifier.
     *
     * @param latitude  latitude value
     * @param longitude longitude value
     * @throws Exception if a geocoding error occurred
     */
    private void reverseGeocode(final double latitude, final double longitude) throws Exception {
        final GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(KEY)
                .build();
        final LatLng latlng = new LatLng(latitude, longitude);
        final GeocodingResult[] results;
        try {
            results = GeocodingApi.reverseGeocode(context, latlng).await();
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            this.response = gson.toJson(results[0].addressComponents);
        } catch (final Exception e) {
            throw e;
        }
    }

    /**
     * Sends a geocode request obtaining latitude and longitude values.
     *
     * @param address a string representing the address
     * @throws Exception if a geocoding error occurred
     */
    private void geocode(final String address) throws Exception {
        final GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(KEY)
                .build();
        final GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, address).await();
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            this.response = gson.toJson(results[0].geometry.location);
        } catch (final Exception e) {
            throw e;
        }

    }
}