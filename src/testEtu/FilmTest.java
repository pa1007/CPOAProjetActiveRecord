package testEtu;

import activerecord.exception.RealisateurAbsentException;
import activerecord.poo.Film;
import activerecord.poo.Personne;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilmTest {

    Personne personne;
    Personne personne1;
    Personne personne2;
    Personne personne3;

    @Before
    public void before() {
        Personne.createTable();
        Film.createTable();
        this.personne = new Personne("Personne", "Nom");
        this.personne.save();
        this.personne1 = new Personne("Personne1", "Nom1");
        this.personne1.save();
        this.personne2 = new Personne("Personne3", "Nom3");
        this.personne2.save();
        this.personne3 = new Personne("Personne4", "Nom4");
        this.personne3.save();
        new Film("Film1", this.personne).save();
        new Film("Film2", this.personne1).save();
        new Film("Film3", this.personne).save();
        new Film("Film4", this.personne1).save();
        new Film("Film5", this.personne3).save();
        new Film("Film6", this.personne3).save();
        new Film("Film7", this.personne2).save();
    }

    @After
    public void after() {
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    public void testConstruct() {
        Film f = new Film("Film8Temp", this.personne);
        Assert.assertEquals("Aucune id", f.getId(), -1);
    }

    @Test
    public void testfindById() {
        Film f = Film.findById(1);
        Assert.assertEquals("Le titre n'est pas bon", "Film1", f.getTitre());
        Assert.assertEquals("le nom n'est pas bon", "Personne", f.getRealisateur().getPrenom());
    }

    @Test
    public void testfindByIdInexistante() {
        Film f = Film.findById(8);
        Assert.assertNull("Aucun film", f);
    }

    @Test
    public void testSaveAvecRealisateur() {
        new Film("Film", this.personne3).save();
        Film f2 = Film.findById(8);
        Assert.assertEquals("Le titre n'est pas bon", "Film", f2.getTitre());
        Assert.assertEquals("Le nom n'est pas bon", "Personne4", f2.getRealisateur().getPrenom());
        Assert.assertEquals("L'id du real n'est pas bonne", 4, f2.getRealisateur().getId());
    }

    @Test
    public void testSaveAvecRealisateurRecupere() {
        Personne p = Personne.findByName("Nom").get(0);
        new Film("Film9Temp", p).save();
        Film f2 = Film.findById(8);
        Assert.assertEquals("Le titre n'est pas bon", "Film9Temp", f2.getTitre());
        Assert.assertEquals("Le prenom n'est pas bon", "Personne", f2.getRealisateur().getPrenom());
        Assert.assertEquals("l'id n'est pas la bonne", 1, f2.getRealisateur().getId());
    }

    @Test
    public void testNouveauRealSauve() {
        Personne p = new Personne("Personne5", "nom5");
        p.save();
        new Film("FILM", p).save();
        Film f = Film.findById(8);
        Assert.assertEquals("Titre faux", "FILM", f.getTitre());
        Assert.assertEquals("Nom réa faux", "Personne5", f.getRealisateur().getPrenom());
        Assert.assertEquals("Id nouvelle", 5, f.getRealisateur().getId());
    }

    @Test(expected = RealisateurAbsentException.class)
    public void testNouveauRealInconnu() {
        Personne p = new Personne("Personne5", "nom5");
        new Film("FILM", p).save();
    }

    @Test
    public void testChangeNomReal() {
        Film f = new Film("FILM", this.personne3);
        f.save();
        this.personne3.setPrenom("prenom2");
        this.personne3.save();
        Film f2 = Film.findById(8);
        Assert.assertEquals("FILM", f2.getTitre());
        Assert.assertEquals("prenom2", f2.getRealisateur().getPrenom());
        Assert.assertEquals(4L, f2.getRealisateur().getId());
    }
}
