package bts.sio.gedimaginationandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhotosHelper extends SQLiteOpenHelper {
    public PhotosHelper(Context context)
    {
        super(context, "basePhotosWS.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // création des tables de la base embarquée
        // création de la table PHOTO
        db.execSQL("CREATE TABLE PHOTO ("
                + "id INTEGER NOT NULL PRIMARY KEY,"
                + "titre TEXT NOT NULL,"
                + "description TEXT NOT NULL,"
                + "date TEXT NOT NULL,"
                + "chemin_photo TEXT);");

        db.execSQL("CREATE TABLE DATES ("
                + "dateDebutInsc TEXT,"
                + "dateFinInsc TEXT,"
                + "dateDebutVote TEXT,"
                + "dateFinVote TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PHOTO;");
        db.execSQL("DROP TABLE IF EXISTS DATES;");
        onCreate(db);
    }
}
