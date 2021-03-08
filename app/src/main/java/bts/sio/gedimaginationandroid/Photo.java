package bts.sio.gedimaginationandroid;


public class Photo {
    // attributs privés
    private Integer id;
    private String titre;
    private String description;
    private String date;
    private String chemin_photo;

    // constructeur
    public Photo(Integer id, String titre, String description,String date,String chemin_photo) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.chemin_photo = chemin_photo;
    }

    // getteurs - setteurs -> id
    public Integer getId() {return this.id;}
    public void setId(Integer id) { this.id = id; }
    // getteurs - setteurs -> titre
    public String getTitre() {return this.titre;}
    public void setTitre(String titre) { this.titre = titre; }
    // getteurs - setteurs -> description
    public String getDescription() {return this.description;}
    public void setDescription(String description) { this.description = description; }
    // getteurs - setteurs -> date
    public String getDate() {return this.date;}
    public void setDate(String date) { this.date = date;}
    // getteurs - setteurs -> chemin_photo
    public String getChemin_photo() {return this.chemin_photo;}
    public void setChemin_photo(String chemin_photo) {this.chemin_photo = chemin_photo;}
}
