package test;

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

    public FilmTest() {
    }

    @Before
    public void creerDonnees() throws RealisateurAbsentException {
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
        (new Film("Film1", this.personne)).save();
        (new Film("Film2", this.personne1)).save();
        (new Film("Film3", this.personne)).save();
        (new Film("Film4", this.personne1)).save();
        (new Film("Film5", this.personne3)).save();
        (new Film("Film6", this.personne3)).save();
        (new Film("Film7", this.personne2)).save();
    }

    @After
    public void detruireDonnees() {
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    public void testConstruct() {
        Film f = new Film("Film8Temp", this.personne);
        Assert.assertEquals("l'objet n'est pas dans la base", (long) f.getId(), -1L);
    }

    @Test
    public void testfindById() {
        Film f = Film.findById(1);
        Assert.assertEquals("Film1", f.getTitre());
        Assert.assertEquals("Personne", f.getRealisateur().getNom());
    }

    @Test
    public void testfindByIdBis() {
        Film f = Film.findById(5);
        Assert.assertEquals("Film5", f.getTitre());
        Assert.assertEquals("Personne4", f.getRealisateur().getNom());
    }

    @Test
    public void testfindByIdInexistant() {
        Film f = Film.findById(8);
        Assert.assertEquals("pas de film correspondant", (Object) null, f);
    }

    @Test
    public void testSaveAvecRealisateur() throws RealisateurAbsentException {
        (new Film("Film", this.personne3)).save();
        Film f2 = Film.findById(8);
        Assert.assertEquals("Film", f2.getTitre());
        Assert.assertEquals("Personne4", f2.getRealisateur().getNom());
        Assert.assertEquals(4L, (long) f2.getRealisateur().getId());
    }

    @Test
    public void testSaveAvecRealisateurRecupere() throws RealisateurAbsentException {
        Personne p = Personne.findByName("Personne").get(0);
        (new Film("Film9Temp", p)).save();
        Film f2 = Film.findById(8);
        Assert.assertEquals("Film9Temp", f2.getTitre());
        Assert.assertEquals("Personne", f2.getRealisateur().getNom());
        Assert.assertEquals(1L, (long) f2.getRealisateur().getId());
    }

    @Test
    public void test2Saves() throws RealisateurAbsentException {
        Film f = new Film("Film", this.personne3);
        f.save();
        Film f2 = new Film("Film9Temp", this.personne);
        f2.save();
        Film f3 = Film.findById(9);
        Assert.assertEquals("Film9Temp", f3.getTitre());
        Assert.assertEquals("Personne", f3.getRealisateur().getNom());
        Assert.assertEquals(1L, (long) f3.getRealisateur().getId());
    }

    @Test
    public void testNouveauRealSauve() throws RealisateurAbsentException {
        Personne p = new Personne("Personne5", "nom5");
        p.save();
        (new Film("FILM", p)).save();
        Film f = Film.findById(8);
        Assert.assertEquals("FILM", f.getTitre());
        Assert.assertEquals("Personne5", f.getRealisateur().getNom());
        Assert.assertEquals(5L, (long) f.getRealisateur().getId());
    }

    @Test(
            expected = RealisateurAbsentException.class
    )
    public void testNouveauRealInconnu() throws RealisateurAbsentException {
        Personne p = new Personne("Personne5", "nom5");
        (new Film("FILM", p)).save();
    }

    @Test
    public void testChangeNomReal() throws RealisateurAbsentException {
        Film f = new Film("FILM", this.personne3);
        f.save();
        this.personne3.setNom("prenom2");
        this.personne3.save();
        Film f2 = Film.findById(8);
        Assert.assertEquals("FILM", f2.getTitre());
        Assert.assertEquals("prenom2", f2.getRealisateur().getNom());
        Assert.assertEquals(4L, f2.getRealisateur().getId());
    }
}
