package com.example.PruebasManu.database;

/**
 * Created by Manuel on 29/03/2014.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import java.util.Arrays;
import java.util.HashSet;

public class JugadoresMinionContentProvider extends MinionContentProvider {
    public static final String TABLE="Jugadores";
    @Override
    public String getBasePath() {
        return "jugadores";
    }

    @Override
    public Cursor query(SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return db.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public long insert(SQLiteDatabase db, Uri uri, ContentValues contentValues) {
        return db.insert(TABLE,null,contentValues);
    }

    @Override
    public int delete(SQLiteDatabase db, Uri uri, String where, String[] selectionArgs) {
        return db.delete(TABLE,where,selectionArgs);
    }

    @Override
    public int update(SQLiteDatabase db, Uri uri, ContentValues values, String where, String[] selectionArgs) {
        return db.update(TABLE,values,where,selectionArgs);
    }

    @Override
    public String getType() {
        return "Jugadores";
    }
}