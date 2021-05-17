package bts.sio.gedimaginationandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bts.sio.gedimaginationandroid.models.Dates;
import bts.sio.gedimaginationandroid.models.Photo;
import bts.sio.gedimaginationandroid.models.Vote;

public class PhotosDAO {

    private SQLiteDatabase maBase;
    private PhotosHelper mesPhotosHelper;

    public PhotosDAO(Context context)
    {
        mesPhotosHelper = new PhotosHelper(context);
        maBase = mesPhotosHelper.getWritableDatabase();
    }

    public void close() {
        maBase.close();
    }

    public void ajouterPhoto(Photo unePhoto)
    {
        //création d'un ContentValues
        ContentValues v = new ContentValues();
        // ajout des propriétés au ContentValues
        v.put("id", unePhoto.getId());
        v.put("titre", unePhoto.getTitre());
        v.put("date", unePhoto.getDate());
        v.put("description", unePhoto.getDescription());
        v.put("chemin_photo", unePhoto.getChemin_photo());
        // ajout de la photo dans la table photo
        maBase.insert("PHOTO", null, v);
    }

    public void ajouterDates(Dates lesDates)
    {
        //création d'un ContentValues
        ContentValues v = new ContentValues();
        // ajout des propriétés au ContentValues
        v.put("dateDebutInsc", lesDates.getDateDebutInsc());
        v.put("dateFinInsc", lesDates.getDateFinInsc());
        v.put("dateDebutVote", lesDates.getDateDebutVote());
        v.put("dateFinVote", lesDates.getDateFinVote());
        // ajout des dates du concours dans la table dates
        maBase.insert("DATES", null, v);
    }

    public void ajouterAchat(String id, String prenom, String nom, String email)
    {
        //création d'un ContentValues
        ContentValues v = new ContentValues();
        // ajout des propriétés au ContentValues
        v.put("idTicket", id);
        v.put("prenom", prenom);
        v.put("nom", nom);
        v.put("email", email);
        // ajout des dates du concours dans la table dates
        maBase.insert("ACHAT", null, v);
    }

    public void ajouterVote(String idTicket,Integer idPhoto, Integer rating, String dateVote)
    {
        //création d'un ContentValues
        ContentValues v = new ContentValues();
        // ajout des propriétés au ContentValues
        v.put("idTicket", idTicket);
        v.put("idPhoto", idPhoto);
        v.put("rating", rating);
        v.put("dateVote", dateVote);
        // ajout des dates du concours dans la table dates
        maBase.insert("VOTE", null, v);
    }

    public void supprimerTous()
    {
        maBase.execSQL("DELETE FROM PHOTO;");
        maBase.execSQL("DELETE FROM DATES;");
        maBase.execSQL("DELETE FROM ACHAT;");
        maBase.execSQL("DELETE FROM VOTE;");
    }

    public Cursor selectionnerLesDates()
    {
        Cursor curseurContact = maBase.rawQuery("SELECT dateFinInsc, dateDebutVote, dateFinVote FROM DATES;" , new String[] {});
        return curseurContact;
    }

    public Boolean idTicketExistant(String unId)
    {
        Boolean existe = false;
        Cursor curseurTicket = maBase.rawQuery("SELECT idTicket FROM ACHAT WHERE idTIcket=?",new String[] {unId});
        if (curseurTicket.getCount() != 0)
        {
            existe = true;
        }
        return existe;
    }

    public Boolean donneesImportees()
    {
        Boolean existe = false;
        Cursor curseurDates = maBase.rawQuery("SELECT * FROM DATES",new String[] {});
        Cursor curseurPhotos = maBase.rawQuery("SELECT id FROM PHOTO",new String[] {});
        if (curseurDates.getCount() != 0 && curseurPhotos.getCount() != 0)
        {
            existe = true;
        }
        return existe;
    }

    public ArrayList<Photo> selectionnerLesPhotos()
    {
        ArrayList<Photo> listPhotos = new ArrayList<>();
        Cursor curseurContact = maBase.rawQuery("SELECT * FROM PHOTO;" , new String[] {});
        for(curseurContact.moveToFirst(); !curseurContact.isAfterLast(); curseurContact.moveToNext()) {
            listPhotos.add(
                    new Photo(
                            curseurContact.getInt(curseurContact.getColumnIndex("id")),
                            curseurContact.getString(curseurContact.getColumnIndex("titre")),
                            curseurContact.getString(curseurContact.getColumnIndex("description")),
                            curseurContact.getString(curseurContact.getColumnIndex("date")),
                            curseurContact.getString(curseurContact.getColumnIndex("chemin_photo"))
                    )
            );
        }
        return listPhotos;
    }

    public ArrayList<Vote> getDataVote()
    {
        ArrayList<Vote> lesVotes = new ArrayList<>();
        Cursor curseurContact = maBase.rawQuery("SELECT * FROM VOTE;" , new String[] {});
        for(curseurContact.moveToFirst(); !curseurContact.isAfterLast(); curseurContact.moveToNext()) {
            lesVotes.add(
                    new Vote(
                            curseurContact.getString(curseurContact.getColumnIndex("idTicket")),
                            curseurContact.getInt(curseurContact.getColumnIndex("idPhoto")),
                            curseurContact.getInt(curseurContact.getColumnIndex("rating")),
                            curseurContact.getString(curseurContact.getColumnIndex("dateVote"))
                    )
            );
        }
        return lesVotes;
    }
}
