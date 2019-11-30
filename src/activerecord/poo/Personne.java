package activerecord.poo;


import activerecord.database.ConnectionSingleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Personne {

    private int    id;
    private String prenom;
    private String nom;


    public Personne(String prenom, String noms) {
        this.prenom = prenom;
        this.nom = noms;
        id = -1;
    }

    private Personne(int id, String prenom, String noms) {
        this.id = id;
        this.prenom = prenom;
        this.nom = noms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void save() {
        if (this.id == -1) {
            try {
                Connection        c   = ConnectionSingleton.getInstance();
                String            sql = "INSERT INTO personne(NOM,PRENOM) VALUES(?,?)";
                PreparedStatement pre = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pre.setString(1, this.nom);
                pre.setString(2, this.prenom);
                pre.executeUpdate();
                ResultSet keys = pre.getGeneratedKeys();
                keys.next();
                this.id = keys.getInt(1);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Connection        c   = ConnectionSingleton.getInstance();
                String            sql = "UPDATE personne SET nom = ?, prenom = ? WHERE id = ?";
                PreparedStatement pre = c.prepareStatement(sql);
                pre.setString(1, this.nom);
                pre.setString(2, this.prenom);
                pre.setInt(3, this.id);
                pre.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete() {
        if (id != -1) {
            try {
                Connection        c   = ConnectionSingleton.getInstance();
                String            sql = "DELETE FROM personne WHERE id = ?";
                PreparedStatement pre = c.prepareStatement(sql);
                pre.setInt(1, this.id);
                pre.execute();
                this.id = -1;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Personne> findAll() {
        List<Personne> res = new ArrayList<>();
        try {
            Connection        c   = ConnectionSingleton.getInstance();
            String            sql = "SELECT * FROM personne";
            PreparedStatement pre = c.prepareStatement(sql);
            ResultSet         rs  = pre.executeQuery();
            while (rs.next()) {
                res.add(new Personne(rs.getInt("id"), rs.getString("prenom"), rs.getString("nom")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Personne findById(int id) {
        Personne p = null;
        try {
            Connection        c   = ConnectionSingleton.getInstance();
            String            sql = "SELECT * FROM personne WHERE id = ?";
            PreparedStatement pre = c.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                p = new Personne(rs.getInt("id"), rs.getString("prenom"), rs.getString("nom"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static List<Personne> findByName(String n) {
        List<Personne> res = new ArrayList<>();
        try {
            Connection        c   = ConnectionSingleton.getInstance();
            String            sql = "SELECT * FROM personne WHERE nom = ?";
            PreparedStatement pre = c.prepareStatement(sql);
            pre.setString(1, n);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                res.add(new Personne(rs.getInt("id"), rs.getString("prenom"), rs.getString("nom")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `personne` (\n" +
                     "  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                     "  `NOM` varchar(40) NOT NULL,\n" +
                     "  `PRENOM` varchar(40) NOT NULL,\n" +
                     "  PRIMARY KEY (`ID`)\n" +
                     ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 ;";
        ConnectionSingleton.execute(sql);
    }

    public static void deleteTable() {
        String sql = "DROP table personne";
        ConnectionSingleton.execute(sql);
    }
}
