package personnel;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Gestion du personnel. Un seul objet de cette classe existe.
 * Il n'est pas possible d'instancier directement cette classe, 
 * la méthode {@link #getGestionPersonnel getGestionPersonnel} 
 * le fait automatiquement et retourne toujours le même objet.
 * Dans le cas où {@link #sauvegarder()} a été appelé lors 
 * d'une exécution précédente, c'est l'objet sauvegardé qui est
 * retourné.
 */


public class GestionPersonnel implements Serializable
{
	private static final long serialVersionUID = -105283113987886425L;
	private static GestionPersonnel gestionPersonnel = null;
	private SortedSet<Ligue> ligues;
    private SortedSet<Employe> employes;
	private Employe root = new Employe(this, null, "root", "admin", "admin@gmail.com", "toor", null, null);
	public final static int SERIALIZATION = 1, JDBC = 2, 
			TYPE_PASSERELLE = JDBC;  
	private static Passerelle passerelle = TYPE_PASSERELLE == JDBC ? new jdbc.JDBC() : new serialisation.Serialization();	
	
	/**
	 * Retourne l'unique instance de cette classe.
	 * Crée cet objet s'il n'existe déjà.
	 * @return l'unique objet de type {@link GestionPersonnel}.
	 */
	
	public static GestionPersonnel getGestionPersonnel()
	{
		if (gestionPersonnel == null)
		{
			gestionPersonnel = passerelle.getGestionPersonnel();
			if (gestionPersonnel == null)
				gestionPersonnel = new GestionPersonnel();
		}
		return gestionPersonnel;
	}

	public GestionPersonnel()
	{
		if (gestionPersonnel != null)
			throw new RuntimeException("Vous ne pouvez créer qu'une seuls instance de cet objet.");
		ligues = new TreeSet<>();
		//employes = new TreeSet<>();
		gestionPersonnel = this;
	}
	
	public void sauvegarder() throws SauvegardeImpossible
	{
		passerelle.sauvegarderGestionPersonnel(this);
	}
	
	/**
	 * Retourne la ligue dont administrateur est l'administrateur,
	 * null s'il n'est pas un administrateur.
	 * @param administrateur l'administrateur de la ligue recherchée.
	 * @return la ligue dont administrateur est l'administrateur.
	 */
	
	public Ligue getLigue(Employe administrateur)
	{
		if (administrateur.estAdmin(administrateur.getLigue()))
			return administrateur.getLigue();
		else
			return null;
	}

	/**
	 * Retourne toutes les ligues enregistrées.
	 * @return toutes les ligues enregistrées.
	 */
	
	public SortedSet<Ligue> getLigues()
	{
		return Collections.unmodifiableSortedSet(ligues);
	}
	
	public SortedSet<Employe> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employes);
	}

	//ajouter une ligue avec son nom et l'id automatiquement générer
	//si il y aun probléme de sauvegarde ces a code de l'id
	public Ligue addLigue(String nom) throws SauvegardeImpossible
	{
		Ligue ligue = new Ligue(this, nom); 
		ligues.add(ligue);
		return ligue;
	}
	
	//ajouter un ligue avec son id et son nom 
	public Ligue addLigue(int id, String nom)
	{
		Ligue ligue = new Ligue(this, id, nom);
		if(id!=0)
			ligues.add(ligue);
		return ligue;
	}
	
	
	public Employe addEmploye(Ligue id, String nom, String prenom, String mail, String password, LocalDate dateDepart, LocalDate  dateArrivee) throws SauvegardeImpossible {
		   Employe employe = new Employe(this, id, nom, prenom, mail, password, dateDepart, LocalDate.now());
		    employes.add(employe);
			passerelle.insert(employe);
		
		return employe;
	}
	
	public Employe addEmploye(int id, String nom) throws SauvegardeImpossible
	{
		Employe employe = new Employe(this, id, nom);
		employes.add(employe);
		passerelle.insert(employe);
		return employe;
	}
	
	int insert(Ligue ligue) throws SauvegardeImpossible
	{
		 return passerelle.insert(ligue);
	}
	//savoir si il as les droits
	public boolean haveWritePermission(Ligue ligue, Employe employe) {
		if(employe.estRoot()) {
			return true;
		}
		else if(ligue.getAdministrateur().equals(employe))
		{
			return true;
		}
		else {
			return false;
		}
		
	}
	
	int insert(Employe employe) throws SauvegardeImpossible
	{
		return passerelle.insert(employe);
	}

	void update(Ligue ligue) throws SauvegardeImpossible
	{
		passerelle.updateLigue(ligue);
	}
	
	void updateEmploye(Employe employe) throws SauvegardeImpossible
	{
		passerelle.updateEmp(employe);
	}
	void update(Employe employe, String string) throws SauvegardeImpossible
	{
		passerelle.updateEmploye(employe, string);
	}
	 void delete(Employe employe) throws SauvegardeImpossible
	{
	   passerelle.deleteEmploye(employe);
		
	}
	void delete(Ligue ligue) throws SauvegardeImpossible
	{
			passerelle.deleteLigue(ligue);
		
	}
	void remove(Ligue ligue) throws SauvegardeImpossible
	{
		
		gestionPersonnel.delete(ligue);
		ligues.remove(ligue);
	}
	/**
	 * Retourne le root (super-utilisateur).
	 * @return le root.
	 */
	
	public Employe getRoot()
	{
		return root;
	}
	
	void changerAdmin(Employe employe) throws SauvegardeImpossible
	{
			passerelle.SetAdmin(employe);
		
	}
	
	public void RestAdmin(Employe employe) throws SauvegardeImpossible 
	{
		passerelle.RestAdmin(employe);
	}
	
	public void updateRoot(Employe employe) throws SauvegardeImpossible
	{
		passerelle.updateRoot(employe);
	}
	
	void setAdmin(Employe employe)
	{
		try
		{
			passerelle.SetAdmin(employe);
		}
		catch(SauvegardeImpossible e)
		{
			e.printStackTrace();
		}
	}
	
	public void getRootBaseDeDonnees() throws SauvegardeImpossible
	{
		root.setId(1);
		root = passerelle.getSuperAdmin(root);
	}
	
	public Employe getAdmin(Employe admin) throws SauvegardeImpossible
	{
		return gestionPersonnel.getAdmin(admin);
	}
	
	void removeAdmin(Ligue ligue)
	{
		try {
			passerelle.removeAdmin(ligue);
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
	}
	public boolean isExistLigue(String ligue) {
		try {
			return passerelle.isExistLigue(ligue);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean isExistEmployeNomLigue(int ligue,String nom) {
		try {
			return passerelle.isExistEmployeNomLigue(ligue,nom);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean isExistEmployePrenomLigue(int ligue,String prenom) {
		try {
			return passerelle.isExistEmployeNomLigue(ligue,prenom);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
