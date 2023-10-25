package com.crio.shorturl;

import java.util.HashMap;

public class XUrlImpl implements XUrl {

    private HashMap<String, String> mapShortUrlToLongUrl = new HashMap<>();
    private HashMap<String, String> mapLongUrlToShortUrl = new HashMap<>();
    private HashMap<String, Integer> mapUrlToHitCount = new HashMap<>();
    private final String alphaNumericString =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789";


    // If longUrl already has a corresponding shortUrl, return that shortUrl
    // If longUrl is new, create a new shortUrl for the longUrl and return it
    @Override
    public String registerNewUrl(String longUrl) {
        String shortUrl;

        if (mapLongUrlToShortUrl.containsKey(longUrl)) {
            shortUrl = mapLongUrlToShortUrl.get(longUrl);

        } else {
            shortUrl = "http://short.url/" + generateShortUrl();
            addUrlToMap(longUrl, shortUrl);

        }
        return shortUrl;
    }

    private String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder(9);

        for (int shortUrlIndex = 0; shortUrlIndex < 9; shortUrlIndex++) {
            int alphaNumericStringIndex = (int) (alphaNumericString.length() * Math.random());
            shortUrl.append(alphaNumericString.charAt(alphaNumericStringIndex));
        }
        return shortUrl.toString();
    }

    private void addUrlToMap(String longUrl, String shortUrl) {
        mapLongUrlToShortUrl.put(longUrl, shortUrl);
        mapShortUrlToLongUrl.put(shortUrl, longUrl);
        return;
    }

    // If shortUrl is already present, return null
    // Else, register the specified shortUrl for the given longUrl
    // Note: You don't need to validate if longUrl is already present,
    // assume it is always new i.e. it hasn't been seen before
    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        // TODO Auto-generated method stub
        if (mapShortUrlToLongUrl.containsKey(shortUrl)) {
            return null;
        } else{
            addUrlToMap(longUrl, shortUrl);
        }
        return shortUrl;
    }

    // If shortUrl doesn't have a corresponding longUrl, return null
    // Else, return the corresponding longUrl
    @Override
    public String getUrl(String shortUrl) {
        String longUrl = null;

        if (mapShortUrlToLongUrl.containsKey(shortUrl)) {
            longUrl = mapShortUrlToLongUrl.get(shortUrl);
            setHitCount(longUrl);
        }
        return longUrl;
    }

    // Return the number of times the longUrl has been looked up using getUrl()
    @Override
    public Integer getHitCount(String longUrl) {
        // TODO Auto-generated method stub
        int hitCount = 0;
        if (mapUrlToHitCount.containsKey(longUrl)) {
            hitCount = mapUrlToHitCount.get(longUrl);
        }
        return hitCount;
    }

    private void setHitCount(String longUrl) {
        mapUrlToHitCount.putIfAbsent(longUrl, 0);
        mapUrlToHitCount.put(longUrl, mapUrlToHitCount.get(longUrl) + 1);
    }

    // Delete the mapping between this longUrl and its corresponding shortUrl
    // Do not zero the Hit Count for this longUrl
    @Override
    public String delete(String longUrl) {
        String shortUrl = null;
        if (mapLongUrlToShortUrl.containsKey(longUrl)) {
            shortUrl = mapLongUrlToShortUrl.get(longUrl);
            mapLongUrlToShortUrl.remove(longUrl);
            mapShortUrlToLongUrl.remove(shortUrl);
        }
        return shortUrl;
    }

}