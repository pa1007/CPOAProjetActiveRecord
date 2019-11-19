package activerecord.poo;


import activerecord.database.ConnectionSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Film {

    private int   id;
    private String titre;
    private int   idRea;


    public Film(String titre, Personne p) {
        id = -1;
        this.titre = titre;
        idRea = p.getId();
    }

    private Film(int id, String titre, int idRea) {
        this.id = id;
        this.titre = titre;
        this.idRea = idRea;
    }

    public Personne getRealisateur() {
        return Personne.findById(idRea);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setIdRea(int idRea) {
        this.idRea = idRea;
    }

    public static Film findById(int idN) {
        Film p = null;
        try {
            Connection        c   = ConnectionSingleton.getInstance();
            String            sql = "SELECT * FROM film WHERE id = ?";
            PreparedStatement pre = c.prepareStatement(sql);
            pre.setInt(1, idN);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                p = new Film(rs.getInt("ID"), rs.getString("TITRE"), rs.getInt("ID_REA"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return p;


    }

    public static void createTable() {
        String co = "create table film (\n"
                    + "    ID     int auto_increment primary key,\n"
                    + "    TITRE  varchar(40) not null,\n"
                    + "    ID_REA int         null,\n"
                    + "    constraint FK_FID_PID foreign key (ID_REA) references personne(ID)\n"
                    + ");\n;";
        ConnectionSingleton.execute(co);

        String co2 = "create index ID_REA on film(ID_REA);";
        ConnectionSingleton.execute(co2);
    }

    public static void dropTable() {
        String co = "drop table film;";
        ConnectionSingleton.execute(co);
        String co2 = "drop index ID_REA;";
        ConnectionSingleton.execute(co2);
    }
}
