package com.viseo.poc.myweatherapplication.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Unexpected error using Kotlin version of BitmapLruCache.
 * Using Java version instead
 * https://stackoverflow.com/questions/19209114/volley-image-bitmap-is-null/19428501#19428501
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    public BitmapLruCache() {
        this(getDefaultLruCacheSize());
    }

    private BitmapLruCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    private static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}