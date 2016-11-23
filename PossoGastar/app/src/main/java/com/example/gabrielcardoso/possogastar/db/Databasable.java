package com.example.gabrielcardoso.possogastar.db;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dfavato on 21/11/16.
 */

public interface Databasable<T extends Serializable, K> {
    public void save();
    public T getById(K id);

    static List<?> queryAll() {
        return null;
    };


}
