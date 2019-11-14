package activerecord.poo;


import activerecord.database.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Personne {

    private int   id;
    private String nom;
    private String prenom;


    public Personne(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        List<Personne> res = new ArrayList<>();
        try {
            Connection c = ConnectionSingleton.getInstance();
            String sql = "SELECT * FROM Personne";
            PreparedStatement pre = c.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                res.add(new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Personne findById(int id) {
        Personne p = null;
        try {
            Connection c = ConnectionSingleton.getInstance();
            String sql = "SELECT * FROM Personne WHERE id = ?";
            PreparedStatement pre = c.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                p = new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static List<Personne> findByName(String n) {
        List<Personne> res = new ArrayList<>();
        try {
            Connection c = ConnectionSingleton.getInstance();
            String sql = "SELECT * FROM Personne WHERE nom = ?";
            PreparedStatement pre = c.prepareStatement(sql);
            pre.setString(1, n);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                res.add(new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void createTable() {
        String sql = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        ConnectionSingleton.execute(sql);
    }

    public static void deleteTable() {
        String sql = "DROP table personne";
        ConnectionSingleton.execute(sql);
    }
}
