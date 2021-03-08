package bts.sio.gedimaginationandroid;

public class Dates {

    //Attributs privés
    private String dateDebutInsc;
    private String dateFinInsc;
    private String dateDebutVote;
    private String dateFinVote;

    public Dates(String dateDebutInsc, String dateFinInsc, String dateDebutVote, String dateFinVote) {
        this.dateDebutInsc = dateDebutInsc;
        this.dateFinInsc = dateFinInsc;
        this.dateDebutVote = dateDebutVote;
        this.dateFinVote = dateFinVote;
    }


    public String getDateDebutInsc() {
        return dateDebutInsc;
    }
    public void setDateDebutInsc(String dateDebutInsc) {
        this.dateDebutInsc = dateDebutInsc;
    }
    public String getDateFinInsc() {
        return dateFinInsc;
    }
    public void setDateFinInsc(String dateFinInsc) {
        this.dateFinInsc = dateFinInsc;
    }
    public String getDateDebutVote() {
        return dateDebutVote;
    }
    public void setDateDebutVote(String dateDebutVote) {
        this.dateDebutVote = dateDebutVote;
    }
    public String getDateFinVote() {
        return dateFinVote;
    }
    public void setDateFinVote(String dateFinVote) {
        this.dateFinVote = dateFinVote;
    }
}
