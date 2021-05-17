package bts.sio.gedimaginationandroid.models;

public class Vote {

    // attributs privés
    private String idTicket;
    private Integer idPhoto;
    private Integer rating;
    private String dateVote;

    // constructeur
    public Vote(String idTicket, Integer idPhoto, Integer rating,String dateVote) {
        this.idTicket = idTicket;
        this.idPhoto = idPhoto;
        this.rating = rating;
        this.dateVote = dateVote;
    }

    //Getter & Setter
    public String getIdTicket() {
        return idTicket;
    }
    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public Integer getIdPhoto() {
        return idPhoto;
    }
    public void setIdPhoto(Integer idPhoto) {
        this.idPhoto = idPhoto;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDateVote() {
        return dateVote;
    }
    public void setDateVote(String dateVote) {
        this.dateVote = dateVote;
    }

}
