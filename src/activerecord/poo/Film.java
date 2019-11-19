package activerecord.poo;


import activerecord.database.ConnectionSingleton;
import activerecord.exception.RealisateurAbsentException;

import java.sql.*;

public class Film {

    private int    id;
    private String titre;
    private int    idRea;


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

    public static void createTable() {
        String co = "CREATE TABLE IF NOT EXISTS `film` (\n" +
                "  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `TITRE` varchar(40) NOT NULL,\n" +
                "  `ID_REA` int(11) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`ID`),\n" +
                "  KEY `ID_REA` (`ID_REA`)\n" +
                ") ENGINE=InnoDB  DEFAULT CHARSET=latin1;";
        ConnectionSingleton.execute(co);
    }

    public void delete() {
        if (id != -1) {
            try {
                Connection        c   = ConnectionSingleton.getInstance();
                String            sql = "delete from film where ID = ?";
                PreparedStatement pre = c.prepareStatement(sql);
                pre.setInt(1, id);
                pre.execute();
                id = -1;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
    public static Film findByRealisateur(int idN) {
        Film p = null;
        try {
            Connection        c   = ConnectionSingleton.getInstance();
            String            sql = "SELECT * FROM film WHERE ID_REA = ?";
            PreparedStatement pre = c.prepareStatement(sql);
            pre.setInt(1, idN);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                p = new Film(rs.getInt("ID"), rs.getString("TITRE"), idN);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static void deleteTable() {
        String co = "drop table film;";
        ConnectionSingleton.execute(co);
    }

    public void save() {
        try {
            if (idRea == -1) {
                throw new RealisateurAbsentException();
            }
            Connection c = ConnectionSingleton.getInstance();
            if (id == -1) {
                String sql = "insert into film (TITRE, ID_REA) VALUES "
                        + "(?,?)";
                PreparedStatement pre = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pre.setString(1, titre);
                pre.setInt(2, idRea);
                pre.executeUpdate();
                ResultSet keys = pre.getGeneratedKeys();
                keys.next();
                this.id = keys.getInt(1);
            } else {
                String sql = "update film set titre = ? , ID_REA =  ? where id = ?";
                PreparedStatement pre = c.prepareStatement(sql);
                pre.setString(1, titre);
                pre.setInt(2, idRea);
                pre.setInt(3, id);
                pre.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
