package com.redis.message.broker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import static com.redis.message.broker.config.UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter;

public class Utils {
    public static Gson getGson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, getUnixEpochDateTypeAdapter())
                .create();

        return gson;
    }
}
