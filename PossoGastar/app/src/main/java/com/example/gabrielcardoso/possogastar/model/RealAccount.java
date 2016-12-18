package com.example.gabrielcardoso.possogastar.model;

import android.support.annotation.Nullable;

import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfavato on 27/11/16.
 */

public class RealAccount extends BaseAccount {
    public RealAccount(String name, REAL_TYPE type) {
        super(name);
        this.realType = type;
        this.accountType = ACCOUNT_TYPE.REAL;
    }
    public RealAccount(BaseAccount base) {
        this(base.getName(), base.realType);
        this.setId(base.getId());
    }

    @Nullable
    public static RealAccount queryForId(Integer id) throws SQLException {
        BaseAccount b = BaseAccount.queryForId(id);
        if(b != null && b.accountType == ACCOUNT_TYPE.REAL) {
            return new RealAccount(b);
        }
        return null;
    }

    public static List<RealAccount> queryAll() throws SQLException {
        List<RealAccount> list = new ArrayList<>();
        for(BaseAccount b: (List<BaseAccount>) BaseAccount.queryForField("accountType", ACCOUNT_TYPE.REAL)) {
            list.add(new RealAccount(b));
        }
        return list;
    }
}
