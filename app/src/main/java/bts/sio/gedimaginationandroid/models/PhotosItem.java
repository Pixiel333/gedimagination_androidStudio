package bts.sio.gedimaginationandroid.models;

public class PhotosItem {

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
