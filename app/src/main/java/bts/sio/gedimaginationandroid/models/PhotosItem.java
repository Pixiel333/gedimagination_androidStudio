package bts.sio.gedimaginationandroid.models;

import android.os.Parcelable;

import java.io.Serializable;

public class PhotosItem implements Serializable {

    private int numero;
    private  String titre;

    //constructor
    public PhotosItem(int numero, String titre) {
        this.numero = numero;
        this.titre = titre;
    }

    public int getNumero() {
        return numero;
    }

    public String getTitre() {
        return titre;
    }
}
