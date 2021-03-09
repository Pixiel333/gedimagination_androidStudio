package bts.sio.gedimaginationandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void supprimerTous()
    {
        maBase.execSQL("DELETE FROM PHOTO;");
        maBase.execSQL("DELETE FROM DATES");
    }

    public Cursor selectionnerLesDates()
    {
        Cursor curseurContact = maBase.rawQuery("SELECT dateFinInsc, dateDebutVote FROM DATES;" , new String[] {});
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

}
