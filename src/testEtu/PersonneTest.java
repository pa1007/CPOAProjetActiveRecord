package testEtu;

import activerecord.poo.Personne;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class PersonneTest {

    @Before
    public void before() {
        Personne.createTable();
        new Personne("Prenom1", "nom1").save();
        new Personne("Prenom3", "nom1").save();
        new Personne("Prenom2", "nom2").save();
        new Personne("Prenom3", "nom3").save();
    }

    @After
    public void after() {
        Personne.deleteTable();
    }


    @Test
    public void testFindByNameExiste() {
        List<Personne> p = Personne.findByName("nom3");
        Assert.assertEquals("Il doit y avoir une seul réponse", 1, p.size());
        Personne pers = p.get(0);
        Assert.assertEquals("Nom pas le bon", pers.getPrenom(), "Prenom3");
        Assert.assertEquals("Prenom pas le bon", pers.getNom(), "nom3");
    }

    @Test
    public void testFindByName2Existe() {
        List<Personne> p = Personne.findByName("nom1");
        Assert.assertEquals("Il doit y avoir 2 personne avec le meme nom", 2, p.size());
        Personne pers = p.get(0);
        Assert.assertEquals("PreNom pas le bon", pers.getPrenom(), "Prenom1");
        pers = p.get(1);
        Assert.assertEquals("Prenom pas le bon", pers.getPrenom(), "Prenom3");
    }

    @Test
    public void testFindAll() {
        List<Personne> p = Personne.findAll();
        Assert.assertEquals("Il y a 4 personnes", 4, p.size());
    }

    @Test
    public void testSaveNew() {
        Personne p = new Personne("TempP", "Nom2");
        p.save();
        Assert.assertEquals("L'id doit etre set automatiquement", 5, p.getId());
        Personne pers = Personne.findById(5);
        Assert.assertEquals("Nom pas le bon", pers.getPrenom(), "TempP");
        Assert.assertEquals("Prenom pas le bon", pers.getNom(), "Nom2");
        Assert.assertEquals("Id pas bonne", pers.getId(), 5L);
    }

    @Test
    public void testSaveExistant() {
        Personne p = Personne.findById(2);
        p.setNom("NewNom");
        p.save();
        Assert.assertEquals("Id diffente", 2, p.getId());
        List<Personne> pers = Personne.findByName("NewNom");
        Assert.assertEquals("la taille de la liste n'est pas bonne", 1, pers.size());
        Personne p2 = pers.get(0);
        Assert.assertEquals("Pas le bon nom", "NewNom", p2.getNom());
        Assert.assertEquals("Pas le bon prenom", "Prenom3", p2.getPrenom());
        Assert.assertEquals("id changé", p2.getId(), 2);
    }

    @Test
    public void testConstruct() {
        Personne p = new Personne("TempP", "TempN");
        Assert.assertEquals("L'id n'est pas -1", p.getId(), -1);
    }


    @Test
    public void testFindByNameNon() {
        List<Personne> p = Personne.findByName("RIEN");
        Assert.assertEquals("Pas de realisateur, liste vide", 0, p.size());
    }


    @Test
    public void testDelete() {
        Personne p = Personne.findById(1);
        p.delete();
        Personne p2 = Personne.findById(1);
        Assert.assertEquals("id devrait etre revenue à -1", -1L, p.getId());
        Assert.assertNull("le supprime n'existe plus", p2);
    }

}
