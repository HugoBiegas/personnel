package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
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
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", null, null); 
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
		Ligue ligue = gestionPersonnel.addLigue("nomLigue");
		assertEquals("nomLigue", ligue.getNom());
	}
	
	@Test
	void setNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		ligue.setNom("nomLigue");
		assertEquals("nomLigue", ligue.getNom());
	}
	
	@Test
	void getAdmin() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("root", ligue.getAdministrateur());
	}
	
	@Test
	void setAdmin() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("John", "Doe", "jhondoe@mail.com", "admin", null, null);
		ligue.setAdministrateur(employe);
		assertEquals(employe, ligue.getAdministrateur());
	}
	
	@Test
	void remove() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("nomLigue");
		int initSize = gestionPersonnel.getLigues().size();
		ligue.remove();
		assertEquals(initSize - 1, gestionPersonnel.getLigues().size());
	}
	
}



