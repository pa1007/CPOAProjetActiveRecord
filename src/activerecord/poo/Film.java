package activerecord.poo;


public class Film {

    private long   id;
    private String titre;
    private long   idRea;


    public Film(long id, String titre, long idRea) {
        this.id = id;
        this.titre = titre;
        this.idRea = idRea;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }


    public long getIdRea() {
        return idRea;
    }

    public void setIdRea(long idRea) {
        this.idRea = idRea;
    }

}
