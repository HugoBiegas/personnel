package jdbc;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import personnel.*;



public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			
			while (ligues.next())
			{
				
				gestionPersonnel.addLigue(ligues.getInt("num_ligue"), ligues.getString("nom_ligue"));
				//requet préparer
		        PreparedStatement response = connection.prepareStatement("SELECT * FROM employe WHERE num_ligue_Actu = ?");
		        response.setInt(1, ligues.getInt("num_ligue"));
		        ResultSet employe = response.executeQuery();
		        Ligue ligue = gestionPersonnel.getLigues().last();
			
			while (employe.next()) {
				int id = employe.getInt("id_employe");
		        String  nom = employe.getString("nom_employe");
			    String  prenom = employe.getString("prenom_employe");
				String	mail = employe.getString("mail_employe");
	            String	password = employe.getString("password_employe");
	            //si employe.getString("dateArrivee_employe") et différent de null on lui mais la valeur en local date sinon on lui attribu null
	            LocalDate dateArrivee;
	            LocalDate dateDepart;
		        if(employe.getString("dateArrivee_employe") != null) {
		        	String date = employe.getString("dateArrivee_employe");
		        	date= date.substring(0, 10);
		        	dateArrivee = LocalDate.parse(date);
		        }else {
		        	dateArrivee =null;
		        }
		        //si employe.getString("dateArrivee_employe") et différent de null on lui mais la valeur en local date sinon on lui attribu null		        
		        if(employe.getString("dateDepart_employe") != null) {
		        	String date = employe.getString("dateDepart_employe");
		        	date= date.substring(0, 10);
		        	dateDepart = LocalDate.parse(date);
		        }else {
		        	dateDepart =null;
		        }			    
		        Employe employes = ligue.addEmploye(nom, prenom, mail, password, dateArrivee, dateDepart, id);
			    //si il est admin d'une ligue
			    if(employe.getBoolean("admin")) {
			    	ligue.setAdministrateur(employes);
			    }
			} }
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom_ligue) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			//récupérer l'id de l'employer enregistrer
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	@Override
	public void updateLigue(Ligue ligue) throws SauvegardeImpossible 
	{
		try
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE ligue SET nom_ligue = ? WHERE num_ligue = ?");
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			instruction.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public void updateEmp(Employe employe) throws SauvegardeImpossible 
	{
		try
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe SET nom_employe = ?, prenom_employe = ?, mail_employe = ? , password_employe = ? WHERE id_employe = ?");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setInt(5, employe.getId());
			instruction.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible 
	{	
		try
		{
			PreparedStatement listLigue;
			listLigue = connection.prepareStatement("DELETE FROM ligue WHERE num_ligue = ?");
			listLigue.setInt(1, ligue.getId());
			listLigue.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
		
	}
	
	
	@Override
	public int insert(Employe employe) throws SauvegardeImpossible 
	{
		try {
			
			PreparedStatement instruction2;
			instruction2 = connection.prepareStatement("insert into employe (nom_employe, prenom_employe, mail_employe, password_employe, dateArrivee_employe, num_ligue_Actu) values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			instruction2.setString(1, employe.getNom());		
			instruction2.setString(2, employe.getPrenom());	
			instruction2.setString(3, employe.getMail());
			instruction2.setString(4, employe.getPassword());
			instruction2.setString(5, employe.getDateArrivee() == null ? null :  String.valueOf(employe.getDateArrivee()));
			instruction2.setInt(6, employe.getLigue().getId());
			instruction2.executeUpdate();
			//récupérer l'id de l'employer enregistrer
			ResultSet id = instruction2.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}
	
	
	
	public void updateEmploye(Employe employe, String dataList) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
	        instruction = connection.prepareStatement("UPDATE employe SET " + dataList + "= ? WHERE id_employe = ?");
	        //on créer une map avec tout les données de l'employer actuelle
			Map <String, String> map = new HashMap<>();
					map.put("nom_employe", employe.getNom());
					map.put("prenom_employe", employe.getPrenom());
					map.put("mail_employe", employe.getMail());
					map.put("password_employe", employe.getPassword());
					map.put("dateArrivee_employe", String.valueOf(employe.getDateArrivee()).isEmpty() ? null : String.valueOf(employe.getDateArrivee()));
					map.put("dateDepart_employe", String.valueOf(employe.getDateDepart()).isEmpty() ? null : String.valueOf(employe.getDateDepart()));
		//on prent la partie ou le nom du champ et égale aux nom que l'on veux modifier
		instruction.setString(1, map.get(dataList));
		//on récupére l'id pour l'envoiler
	    instruction.setInt(2, employe.getId());
	    //on lance l'ubdate
		instruction.executeUpdate();
		}
		catch (SQLException e) 
		{
			
			throw new SauvegardeImpossible(e);
	}
	}

	@Override
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible 
	{	
		try
		{
			PreparedStatement listEmploye;
			listEmploye = connection.prepareStatement("DELETE FROM employe WHERE id_employe = ?");
			listEmploye.setInt(1, employe.getId());
			listEmploye.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
		
	}

	@Override
	public void SetAdmin(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement listEmploye;
			listEmploye = connection.prepareStatement("UPDATE employe SET admin = ? WHERE num_ligue_Actu = ? AND id_employe = ?");
			listEmploye.setInt(1, 1);
			listEmploye.setInt(2, employe.getLigue().getId());
			listEmploye.setInt(3, employe.getId());
			listEmploye.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	public void setRoot(Employe employe) 
	{
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("INSERT INTO employe (nom_employe, prenom_employe, mail_employe, password_employe, superAdmin) VALUES (?,?,?,?,?)");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setInt(5, 1);
			instruction.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void updateRoot(Employe employe) throws SauvegardeImpossible
	{
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe SET nom_employe = ?, prenom_employe = ?, mail_employe = ?, password_employe = ? WHERE superAdmin = 1");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	@Override
	public void removeAdmin(Ligue ligue) throws SauvegardeImpossible
	{
		try
		{
			PreparedStatement tableEmploye;
			tableEmploye = connection.prepareStatement("UPDATE employe SET admin = 0 WHERE num_ligue = ?");
			tableEmploye.setInt(1, ligue.getId());
			tableEmploye.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	
	
	
	
	
	public void setAdmin(Employe employe) throws SauvegardeImpossible
	{
		try 
		{
			PreparedStatement tableEmploye;
			tableEmploye = connection.prepareStatement("UPDATE employe SET admin = (CASE WHEN id_employe = ? THEN 1 WHEN id_employe <> ? THEN 0 END) WHERE num_ligue_Actu = ?");
			tableEmploye.setInt(1, employe.getId());
			tableEmploye.setInt(2, employe.getId());
			tableEmploye.setInt(3, employe.getLigue().getId());
			tableEmploye.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	public Employe getSuperAdmin(Employe superadmin) throws SauvegardeImpossible
	{
		try {
			Statement intruction = connection.createStatement();
			String requete = "SELECT * FROM employe WHERE superAdmin = 1";
			ResultSet response = intruction.executeQuery(requete);
			if(!response.next()) {
				setRoot(superadmin);
			}
			else {
				superadmin.setId(1);
				String nom = response.getString("nom_employe");
				String prenom = response.getString("prenom_employe");
				String mail =  response.getString("mail_employe");
			    String password = response.getString("password_employe");
			    superadmin.setNom(nom);
			    superadmin.setPrenom(prenom);
			    superadmin.setMail(mail);
			    superadmin.setPassword(password);
			}
		    return superadmin;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	
	
	public Employe getAdmin(Employe admin) throws SauvegardeImpossible
	{
		try {
			Statement intruction = connection.createStatement();
			String requete = "SELECT * FROM employe WHERE admin = 1";
			ResultSet response = intruction.executeQuery(requete);
			if(!response.next()) {
				setRoot(admin);
			}
			else {
				admin.setId(1);
				String nom = response.getString("nom_employe");
				String prenom = response.getString("prenom_employe");
				String mail =  response.getString("mail_employe");
			    String password = response.getString("password_employe");
			    admin.setNom(nom);
			    admin.setPrenom(prenom);
			    admin.setMail(mail);
			    admin.setPassword(password);
			}
		    return admin;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	public boolean isExistLigue(String ligue) throws SauvegardeImpossible{
		try {
			PreparedStatement requete = connection.prepareStatement("SELECT * FROM ligue where nom_ligue = ?");
			requete.setString(1, ligue);
			ResultSet response = requete.executeQuery();
			if(response.next()) 
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
