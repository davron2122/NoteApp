package com.example.noteapp.util;

public interface PreferenceHelper {
    void setValue(String key, Object value);

    <T> Object getValue(Class<T> aClass, String key, Object defaultValue);

    void removeKey(String key);

    void clear();
}
