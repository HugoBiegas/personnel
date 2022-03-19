package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;



public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;
    //private LigueConsole ligueConsole;
    
	
	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
		//this.ligueConsole = ligueConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(selectionnerLigue());
		menu.add(ajouterLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	
	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {
			
			for (Employe employes: ligue.getEmployes())
			System.out.println(employes);
			
			if(ligue.getEmployes().size() == 0)
			{
				System.out.println("Il n'y a aucun employ� dans cette ligue");
			}
		});
	}

	
	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom_ligue : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	
	private Menu editerLigue(Ligue ligue) 
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
	    menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue, null));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}
	
	
	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));
				System.out.println("La ligue a bien été renommée par" + ligue.getNom());
				});
		      
	}

	
	//change admin
	private List<Employe> changerAdministrateur(final Ligue ligue, final Employe employe)
	{
		List<Employe> EmployeList = new List<>("Changer administrateur", "c",
				   ()-> new ArrayList<>(ligue.getEmployes()),
				   (index, element) -> {
					   ligue.setAdministrateur(element);
				   });
		  EmployeList.addBack("q");
		          return EmployeList;
    }	 
		 
	
	private List<Ligue> selectionnerLigue() 
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue) throws SauvegardeImpossible 
	{
		return new Option("ajouter un employé", "a",
				() -> 
				{
					System.out.println("Ajouter un employe");
					ligue.addEmploye(getString("nom : "), 
							getString("prenom : "), getString("mail : "), 
						    getString("password : "), LocalDate.parse(getString("Date d'arriv�e (YYYY-MM-DD) : ")), null);
					
					}
		);
	}
	
	
	private Menu gererEmployes(Ligue ligue) 
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		try {
			menu.add(ajouterEmploye(ligue));
		} catch (SauvegardeImpossible e) {
			System.err.println("Impossible de sauvegarder cet employ�");
			e.printStackTrace();
		}
		menu.add(selectionnerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}
	
	
	
	private Menu selectionnerEmploye(Ligue ligue) 
	{
		Menu menu = new Menu("Selectionner employe de  " + ligue.getNom(), "e");
		menu.add(modifierEmploye(ligue));
		menu.add(supprimerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}
	

	
	private List<Employe> supprimerEmploye(final Ligue ligue)
	{
		List<Employe> EmployeList = new List<>("Supprimer un employé", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {try {
					element.remove();
				} catch (SauvegardeImpossible e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
				);
		EmployeList.addBack("q");
		return EmployeList;
	}
	

	private List<Employe> modifierEmploye(final Ligue ligue)
	{
		List<Employe> EmployeList = new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
		EmployeList.addBack("q");
		return EmployeList;
	}
	
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {try {
			ligue.remove();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	
	
}
