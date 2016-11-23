package com.example.gabrielcardoso.possogastar.db;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dfavato on 21/11/16.
 */

public interface Databasable<T extends Serializable, K> {
    static DataBaseHelper db = null;
    public void save();
    public T getById(K id);

    static List<?> queryAll() {
        return null;
    };

    static void setDb(DataBaseHelper db) {
        this.db = db;
    }

}
