package personnel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Représente une ligue. Chaque ligue est reliée à une liste
 * d'employés dont un administrateur. Comme il n'est pas possible
 * de créer un employé sans l'affecter à une ligue, le root est 
 * l'administrateur de la ligue jusqu'à ce qu'un administrateur 
 * lui ait été affecté avec la fonction {@link #setAdministrateur}.
 */

public class Ligue implements Serializable, Comparable<Ligue>
{
	private static final long serialVersionUID = 1L;
	private int id = -1;
	private String nom;
	private SortedSet<Employe> employes;
	private Employe administrateur;
	private GestionPersonnel gestionPersonnel;
	
	/**
	 * Crée une ligue.
	 * @param nom le nom de la ligue.
	 */
	
	Ligue(GestionPersonnel gestionPersonnel, String nom) throws SauvegardeImpossible
	{
		this(gestionPersonnel, -1, nom);
		this.id = gestionPersonnel.insert(this); 
	}

	Ligue(GestionPersonnel gestionPersonnel, int id, String nom)
	{
		this.nom = nom;
		employes = new TreeSet<>();
		this.gestionPersonnel = gestionPersonnel;
		administrateur = gestionPersonnel.getRoot();
		this.id = id;
	}

	/**
	 * Retourne le nom de la ligue.
	 * @return le nom de la ligue.
	 */

	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom.
	 * @param nom le nouveau nom de la ligue.
	 */

	public void setNom(String nom)
	{
		this.nom = nom;
		this.update();
	}

	/**
	 * Retourne l'administrateur de la ligue.
	 * @return l'administrateur de la ligue.
	 */
	
	public Employe getAdministrateur()
	{
		return administrateur;
	}

	/**
	 * Fait de administrateur l'administrateur de la ligue.
	 * Lève DroitsInsuffisants si l'administrateur n'est pas 
	 * un employé de la ligue ou le root. Révoque les droits de l'ancien 
	 * administrateur.
	 * @param administrateur le nouvel administrateur de la ligue.
	 */
	
	public void setAdministrateur(Employe administrateur,Ligue ligue)
	{
		Employe root = gestionPersonnel.getRoot();
		if (administrateur != root && administrateur.getLigue() != this)
			throw new DroitsInsuffisants();
		this.administrateur = administrateur;
		gestionPersonnel.removeAdmin(ligue);
		gestionPersonnel.setAdmin(administrateur,ligue);
	}

	/**
	 * Retourne les employés de la ligue.
	 * @return les employés de la ligue dans l'ordre alphabétique.
	 */
	
	public SortedSet<Employe> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employes);
	}

	/**
	 * Ajoute un employé dans la ligue. Cette méthode 
	 * est le seul moyen de créer un employé.
	 * @param nom le nom de l'employé.
	 * @param prenom le prénom de l'employé.
	 * @param mail l'adresse mail de l'employé.
	 * @param password le password de l'employé.
	 * @return l'employé créé. 
	 */

	
	public Employe addEmploye(String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart)
	{
		//ajoue de l'employer dans l'applie actuelle
		Employe employe = new Employe(this.gestionPersonnel, this, nom, prenom, mail, password, dateArrivee, dateDepart);
		employes.add(employe);
		
		try {
			//ajoue de l' employer dans l'applications
			employe.setId(gestionPersonnel.insert(employe));
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
		return employe;
	}
	
	public Employe addEmploye(String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart, int id)
	{
		Employe employe = new Employe(this.gestionPersonnel, this, nom, prenom, mail, password, dateDepart, dateArrivee);
		employe.setId(id);
		employes.add(employe);
		return employe;
	}
	
	
	void remove(Employe employe)
	{
		employes.remove(employe);
	}
	
	/**
	 * Supprime la ligue, entraîne la suppression de tous les employés
	 * de la ligue.
	 * @throws SauvegardeImpossible 
	 */
	
	
	public void remove() throws SauvegardeImpossible
	{
		gestionPersonnel.remove(this);
	}
	
	public void removeAdmin() throws SauvegardeImpossible
	{
		gestionPersonnel.removeAdmin(this);
	}
	
	public void update()
	{
		try {
			gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			
			e.printStackTrace();
		}
	}

	public void changeAdmin(Employe employe,Ligue ligue) throws SauvegardeImpossible 
	{
		this.administrateur = employe;
		gestionPersonnel.changerAdmin(employe, ligue);
	}
	
	@Override
	public int compareTo(Ligue autre)
	{
		//si la lisgue et null ou vide elle est suprimer
		if(autre.getNom() ==null || autre.getNom().equals("")) {
		try {
			gestionPersonnel.remove(autre);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return getNom().compareTo(autre.getNom());
	}
	
	@Override
	public String toString()
	{
		return nom;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setAdmin(Employe employe, Ligue ligue) 
	{
		gestionPersonnel.setAdmin(employe,ligue);
	}
	
	

	
}
