package com.jstechnologies.helpinghands.data.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jstechnologies.helpinghands.data.model.ServiceModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static List<Boolean> fromStringBoolean(String value) {
        Type listType = new TypeToken<List<Boolean>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromListBoolean(List<Boolean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
