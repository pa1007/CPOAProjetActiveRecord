package test;

import activerecord.poo.Personne;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class PersonneTest {

    public PersonneTest() {
    }

    @Before
    public void creerDonnees() {
        Personne.createTable();
        new Personne("nom1", "Prenom1").save();
        new Personne("nom4", "Prenom1").save();
        new Personne("nom2", "Prenom2").save();
        new Personne("nom3", "Prenom3").save();
    }

    @After
    public void supprimerDonnees() {
        Personne.deleteTable();
    }

    @Test
    public void testConstruct() {
        Personne p = new Personne("toto", "tot");
        Assert.assertEquals("objet pas dans la base", p.getId(), -1L);
    }

    @Test
    public void testFindByNameExiste() {
        List<Personne> p = Personne.findByName("Prenom3");
        Assert.assertEquals("une seule reponse", 1L, p.size());
        Personne pers = p.get(0);
        Assert.assertEquals(pers.getNom(), "Prenom3");
        Assert.assertEquals(pers.getPrenom(), "nom3");
    }

    @Test
    public void testFindByName2Existe() {
        List<Personne> p = Personne.findByName("Prenom1");
        Assert.assertEquals("deux reponses dans personne", 2L, p.size());
        Personne pers = p.get(0);
        Assert.assertEquals(pers.getNom(), "Prenom1");
        pers = p.get(1);
        Assert.assertEquals(pers.getNom(), "Prenom1");
    }

    @Test
    public void testFindByNameNon() {
        List<Personne> p = Personne.findByName("ee");
        Assert.assertEquals("pas de reponse", 0L, p.size());
    }

    @Test
    public void testFindAll() {
        List<Personne> p = Personne.findAll();
        Assert.assertEquals(4L, p.size());
    }

    @Test
    public void testSaveNew() {
        Personne p = new Personne("toto", "titi");
        p.save();
        Assert.assertEquals("id de la personne vient autoincrement", 5L, p.getId());
        Personne pers = Personne.findById(5);
        Assert.assertEquals(pers.getNom(), "toto");
        Assert.assertEquals(pers.getPrenom(), "titi");
        Assert.assertEquals(pers.getId(), 5L);
    }

    @Test
    public void testSaveExistant() {
        Personne p = Personne.findById(2);
        p.setNom("NewNom");
        p.save();
        Assert.assertEquals("id ne devrait pas bouger", 2L, p.getId());
        List<Personne> pers = Personne.findByName("NewNom");
        Assert.assertEquals(1L, pers.size());
        Personne p2 = pers.get(0);
        Assert.assertEquals("NewNom", p2.getNom());
        Assert.assertEquals("Prenom1", p2.getPrenom());
        Assert.assertEquals("ide devrait etre le meme", p2.getId(), 2L);
    }

    @Test
    public void testDelete() {
        Personne p = Personne.findById(1);
        p.delete();
        Personne p2 = Personne.findById(1);
        Assert.assertEquals("id devrait etre revenue à -1", -1L, p.getId());
        Assert.assertEquals("le supprime n'existe plus", null, p2);
    }
}
