package activerecord.poo;


import activerecord.database.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        List<Personne> res = new ArrayList<>();
        try {
            Connection c = ConnectionSingleton.getInstance();
            String sql = "SELECT * FROM Personne;";
            PreparedStatement pre = c.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                res.add(new Personne(rs.getLong("id"), rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Personne findById(long id) {
        Personne p = null;
        try {
            Connection c = ConnectionSingleton.getInstance();
            String sql = "SELECT * FROM Personne WHERE id = ?";
            PreparedStatement pre = c.prepareStatement(sql);
            pre.setLong(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                p = new Personne(rs.getLong("id"), rs.getString("nom"), rs.getString("prenom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static List<Personne> findByName(String n) {
        return null;
    }

}
