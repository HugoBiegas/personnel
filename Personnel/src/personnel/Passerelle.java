package personnel;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel();
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible ;
	public int insert(Employe employe) throws SauvegardeImpossible;
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible;
	public void updateLigue(Ligue ligue) throws SauvegardeImpossible;
	public void updateEmploye(Employe employe, String string) throws SauvegardeImpossible;
	public Employe getSuperAdmin(Employe root) throws SauvegardeImpossible;
	public void updateEmp(Employe employe) throws SauvegardeImpossible;
	void SetAdmin(Employe employe, Ligue ligue) throws SauvegardeImpossible;
    void updateRoot(Employe employe) throws SauvegardeImpossible;
	public void setRoot(Employe employe);
	void removeAdmin(Ligue ligue) throws SauvegardeImpossible;
	public boolean isExistLigue(String ligue) throws SauvegardeImpossible;
	public boolean isExistEmployeNomLigue(int ligue,String nom) throws SauvegardeImpossible;
	public boolean isExistEmployePrenomLigue(int ligue,String prenom) throws SauvegardeImpossible;
	public void RestAdmin(Employe employe) throws SauvegardeImpossible; 
}

