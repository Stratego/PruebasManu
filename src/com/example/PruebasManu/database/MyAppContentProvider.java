package com.example.PruebasManu.database;

/**
 * Created by Manuel on 29/03/2014.
 */
import android.database.sqlite.SQLiteDatabase;

public class MyAppContentProvider extends DespicableContentProvider {
    public static final String AUTHORITY = "com.example.PruebasManu";
    private static SQLiteDatabase db;

    @Override
    public void recruitMinions() {
        addMinion(new JugadorMinionContentProvider());
        addMinion(new JugadoresMinionContentProvider());
    }

    @Override
    public  String getAuthority() {
        return AUTHORITY;
    }

    @Override
    public SQLiteDatabase getDb() {
        if (db == null)
            db = new SQLiteHelper(getContext()).getReadableDatabase();

        return db;
    }
}