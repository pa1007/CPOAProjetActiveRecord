package activerecord.poo;


import java.util.List;

public class Personne {

    private long   id;
    private String nom;
    private String prenom;


    public Personne(long id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public static List<Personne> findAll() {
        return null;
    }

    public static Personne findById(int id) {
        return null;
    }

    public static List<Personne> findByName(String n) {
        return null;
    }

}
