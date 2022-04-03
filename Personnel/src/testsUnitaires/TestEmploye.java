package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import commandLine.DateInvalideException;
import personnel.*;


class testEmploye 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	 public Employe getAdministrateur() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.getAdministrateur();
				return employe;
	}
	
	@Test
	void toStringLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.toString());
	}
	
	
	@Test
	void getNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("ligue1");
		Employe employe = ligue.addEmploye("test", "test", "test@gmail.com", "admin", null, null);
		assertEquals("test", employe.getNom());
	}

	@Test
	void setNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("ligue1");
		Employe employe = ligue.addEmploye("test", "test", "test@mail.com", "admin", null, null);
		employe.setNom("John");
		assertEquals("John", employe.getNom());
	}
	
	@Test
	void getPrenom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("ligue1");
		Employe employe = ligue.addEmploye("test", "test", "test@mail.com", "admin", null, null);
		assertEquals("test", employe.getPrenom());
	}
	
	@Test
	void setPrenom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("ffsc");
		Employe employe = ligue.addEmploye("test", "test", "test@mail.com", "admin", null, null);
		employe.setPrenom("Doe");
		assertEquals("Doe", employe.getPrenom());
	}
	
	@Test
	void setAdmin() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("John", "Doe", "jhondoe@mail.com", "admin", null, null);
		ligue.setAdministrateur(employe,ligue);
		assertEquals(employe, ligue.getAdministrateur());
	}
	

	@Test
	void getDateDepart() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("nomLigue");
		Employe employe = ligue.addEmploye("Joseph", "Mozart", "josephmozart@gmail.com", "admin", LocalDate.parse("2016-04-05"), LocalDate.parse("2017-02-04"));
		assertEquals(LocalDate.parse("2017-02-04"), employe.getDateDepart());
	}
	
	@Test
	void setDateDepart() throws SauvegardeImpossible, DateInvalideException
	{
		try {
			Ligue ligue = gestionPersonnel.addLigue("nomLigue");
			Employe employe = ligue.addEmploye("Joseph", "Mozart", "josephmozart@gmail.com", "admin", LocalDate.parse("2016-07-05"), LocalDate.parse("2017-09-07"));
			employe.setDateDepart(LocalDate.parse("2019-01-09"));
			assertEquals(LocalDate.parse("2019-01-09"), employe.getDateDepart());
			employe.setDateDepart(null);
			assertEquals(null, employe.getDateDepart());
			employe.setDateDepart(LocalDate.parse("2019-01-08"));
		}
		catch (DateInvalideException err) {
			assertTrue(err instanceof DateInvalideException);
		}
	}
	
	@Test
	void getDateArrivee() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("nomLigue");
		Employe employe = ligue.addEmploye("Joseph", "Mozart", "josephmozart@gmail.com", "admin", LocalDate.parse("2016-09-05"), LocalDate.parse("2017-07-02"));
		assertEquals(LocalDate.parse("2016-09-05"), employe.getDateArrivee());
	}
	
	@Test
	void setDateArrivee() throws SauvegardeImpossible, DateInvalideException
	{
		try {
			Ligue ligue = gestionPersonnel.addLigue("nomLigue");
			Employe employe = ligue.addEmploye("Joseph", "Mozart", "josephmozart@gmail.com", "admin", LocalDate.parse("2016-01-05"), LocalDate.parse("2017-09-02"));
			employe.setDateArrivee(LocalDate.parse("2016-01-04"));
			assertEquals(LocalDate.parse("2016-01-04"), employe.getDateArrivee());
			employe.setDateArrivee(null);
			assertEquals(null, employe.getDateArrivee());
			employe.setDateArrivee(LocalDate.parse("2016-01-04"));
		} 
		catch (DateInvalideException err) {
			assertTrue(err instanceof DateInvalideException);
		}
	}
	
	@Test
	 void EstAdmin() throws SauvegardeImpossible
	 {
		 Ligue ligue = gestionPersonnel.addLigue ("ligue1");
		 Ligue ligue1 = gestionPersonnel.addLigue("ligue2");
		 Employe employe = ligue.addEmploye("test", "employe", "test@gmail.com","azerty", null, null);
		 Employe employe2 = ligue.addEmploye("Jaen", "Mathilde", "mathilde@gmail.com", "azerty", null, null);
		 ligue.setAdministrateur(employe,ligue);
		 assertTrue(employe.estAdmin(ligue));
		 assertFalse(employe.estAdmin(ligue1));
		 assertFalse(employe2.estAdmin(ligue));  
	 }
	
	 @Test
     void remove() throws SauvegardeImpossible, DateInvalideException
     {
             Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
             Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", null, null);
             Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
             ligue.setAdministrateur(employe,ligue);
             employe.remove(employe,ligue);
             assertTrue(employe.getDateDepart() !=null);
 			assertEquals(ligue.getAdministrateur().toString(), "root  admin");
            
     }
	
	
}


