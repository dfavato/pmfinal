package com.example.gabrielcardoso.possogastar;

import android.util.Log;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.example.gabrielcardoso.possogastar.model.AccountingAccount;
import com.j256.ormlite.dao.Dao;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public static void main(String[] args) {
        AccountingAccount a = new AccountingAccount("Alimentação", 0);
        System.out.println(a.toString());
    }
}