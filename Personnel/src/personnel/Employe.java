package personnel;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

import commandLine.DateInvalideException;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */


public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private LocalDate dateArrivee, dateDepart;
	private Employe administrateur;
	private Ligue ligue;
	private SortedSet<Employe> employes;
	private GestionPersonnel gestionPersonnel;
	private int id;
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		this.dateArrivee = dateArrivee;
		this.dateDepart = dateDepart;
	}
	
	public Employe(GestionPersonnel gestionPersonnel, int id, String nom) {
		this.nom = nom;
		employes = new TreeSet<>();
		this.gestionPersonnel = gestionPersonnel;
		administrateur = gestionPersonnel.getRoot();
		this.id = id;
	}

	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
		try {
			this.update("nom_employe");
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
		try {
			this.update("prenom_employe");
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 */

	public void setMail(String mail)
	{
		this.mail = mail;
		try {
			this.update("mail_employe");
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Retourne la date d'arriv�e de l'employe;
	 * */
	
	public LocalDate getDateArrivee()
	{
		 return dateArrivee;
	}
	
	
	/**
	 * Change la date d'arriv�e de l'employe
	 * */
	public void setDateArrivee(LocalDate dateArrivee) throws DateInvalideException 
	{
		this.dateArrivee = dateArrivee;
		try {
			this.update("dateArrivee_employe");
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne la date de d�part de l'employe
	 * */
	
	public LocalDate getDateDepart()
	{
		return dateDepart;
	}
	
	/**
	 * Change la date de d�part de l'employe
	 * */
	
	public void setDateDepart(LocalDate dateDepart) throws DateInvalideException 
	{
		this.dateDepart = dateDepart;
		try {
			this.update("dateDepart_employe");
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
		
	}

	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 */
	
	public void setPassword(String password)
	{
		this.password= password;
		try {
			this.update("password_employe");
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 * @throws SauvegardeImpossible 
	 */
	
	public void remove() throws SauvegardeImpossible
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			gestionPersonnel.delete(this);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + "  " + prenom;
		return res;
	}
	
	
	//@Override
	//public String toString()
//	{
	//	String res = nom + " " + prenom + " " + mail + " " + dateArrivee + " " + dateDepart + " (";
		//if (estRoot())
			//res += "super-utilisateur";
	//	else
	//		res += ligue.toString();
	//	return res + ")";
//	}
	
	

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void update(String string) throws SauvegardeImpossible {
		 gestionPersonnel.update(this, string);
	}
	
	public void updateEmploye()
	{
		try {
			gestionPersonnel.updateEmploye(this);
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
	}
	
}




