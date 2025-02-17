package InterfaceApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;


public class Connexions{
	
    static GestionPersonnel gestionPersonnel;
    Ligue ligue;
    Employe employe;
    private JPasswordField passwordTxt;
    private JTextField login;
    private JFrame frame = new JFrame();
    Employe connectedEmploye;
    private JLabel passIncorrect;
    
    public Connexions(GestionPersonnel gestionPersonnel)
	{
		this.gestionPersonnel = gestionPersonnel;
	}
	
    public void signIn()
    {
    	frame().setVisible(true);
    }
    
    private JFrame frame()
    {
    	//backgrawd couleur
    	 frame.getContentPane().setBackground(Color.decode("#0080ff"));
    	 //titre
    	 frame.setTitle("Personnel");
    	 //taille
    	 frame.setSize(600,600);
    	 //center la fenettre
    	 frame.setLocationRelativeTo(null);
    	 //mise en place d'une grille complexe de ligne et colone
         frame.setLayout(new GridBagLayout());
         //permet de fermet proprement les Jframe
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         //ajouter le contenu
         frame.add(container());
    	 //icon en haut a gauche
    	 Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");  
    	 frame.setIconImage(icon); 
         //permet de faire que la personne ne peux pas modifier la taille de la fenettre
         frame.setResizable(false);
    	 return frame;
    }
    
    private JPanel container()
    {
    	JPanel panel = new JPanel();
    	//dimentions de la fenettre
    	panel.setPreferredSize(new Dimension(400,400));
    	//couleur de fon
    	panel.setBackground(Color.decode("#9f9f9f"));
    	//faire les bordure avec couleur rouge épéseur 1
    	panel.setBorder(BorderFactory.createLineBorder(Color.decode("#9f9f9f"), 1));
    	//ajouer du login passaword
        panel.add(loginPasswordInput());
        return panel;
    }
    private JPanel  loginPasswordInput()
    {
    	JPanel panel = new JPanel();
    	//couleur de font
    	panel.setBackground(Color.decode("#9f9f9f"));
    	//créations d'une grille pour superposer les élément 
    	//0 ces pour vertical 2 horrizontal
    	//https://docs.oracle.com/javase/7/docs/api/java/awt/GridLayout.html
    	GridLayout layout = new GridLayout(0,1);
    	//espace vertical de la grille
    	layout.setVgap(5);
    	//message si connections fail
        panel.add(passwordFailed());
    	//ajoute dans le panel
        panel.setLayout(layout);
        //ajoue du texte email
        panel.add(email());
        //endroit ou mettre l'email
        panel.add(loginInput());
        //ajoue du texte pasword
        panel.add(password());
        //endroi ou rentrer le mdp
        panel.add(passInput());
        //ajoue du bouton de connection
        panel.add(btnConnexion());
        return panel;
    }
    
    private JLabel email()
    {
    	JLabel loginL = new JLabel("email : ");
        loginL.setFont(new Font("Serif", Font.BOLD, 25));
        loginL.setForeground(Color.decode("#540b0e"));
        return loginL;
    }
    
    private JTextField loginInput()
    {
    	login = new JTextField();
        return login;
    }
    
    private JLabel password()
    {
    	JLabel passwordL = new JLabel("Mot de passe : ");
        passwordL.setFont(new Font("Serif", Font.BOLD, 25));
        passwordL.setForeground(Color.decode("#540b0e"));
        return passwordL;
    }
    
    private JPasswordField passInput()
    {
    	passwordTxt = new JPasswordField();
        return passwordTxt;
    }
    private JLabel passwordFailed() 
    {
    	passIncorrect = new JLabel();
    	return passIncorrect;
    }
    
    private  JButton btnConnexion()
    {
    	 JButton btnconnexion = new JButton("Connexion");
    	 //Dimension btn, couleur, actions ...
         btnconnexion.setPreferredSize(new Dimension(200,50));
         btnconnexion.setBackground(Color.decode("#540b0e"));
         btnconnexion.setForeground(Color.decode("#fafafa"));
         btnconnexion.setFont(new Font("Serif", Font.PLAIN, 20));
         btnconnexion.addActionListener(btnAction());
         
         return btnconnexion;
    }
    private ActionListener btnAction() {
    	return new ActionListener()
        {
   		@Override
   		public void actionPerformed(ActionEvent arg0) {
   			int cpt=0;
   			//récupérer le mdp
   			String p= new String(passwordTxt.getPassword());
   			//teste le champs password et mail de root
   			if(p.equals(gestionPersonnel.getRoot().getPassword()) && login.getText().equals(gestionPersonnel.getRoot().getMail())){
	    	    	//envoi des imformations nésésére a la page d'acueil
   				   PageAcceuil home = new PageAcceuil(gestionPersonnel, gestionPersonnel.getRoot());
   				   //rendre visible la page
   				   home.frame().setVisible(true);
      				//permet de fermet la fenettre actuelle
   				   frame.dispose();
   			}
   			else {
   				//boucle sur tout les ligue
   				for(Ligue ligue : gestionPersonnel.getLigues()) {
   					//boucle sur tout les employers
      		    	 for(Employe employe : ligue.getEmployes()) {
      		    			 if(p.equals(employe.getPassword()) && login.getText().equals(employe.getMail()) && employe.getDateDepart() ==null) { 
      		    				 //envoi des imformations nésésére a la page d'acueil
      		    				 PageAcceuil home = new PageAcceuil(gestionPersonnel, employe);
      		    				 //rendre visible la page
      		    				 home.frame().setVisible(true);
      		    				 //fermeture de la fenettre de connections
      		    				 frame.dispose();
      		    			 }
      		    	 }
      		     }
   			//si il as pas quiter la page bat ces que il a rentrer des movaise donnée
   				passIncorrect.setText("email ou mot de passe incorrect!");
   			}	
   		}
        };
    }
       
    
    public static void main(String[] args) throws SauvegardeImpossible
    {
    	Connexions signInPage = new Connexions(GestionPersonnel.getGestionPersonnel());
    	signInPage.gestionPersonnel.getRootBaseDeDonnees();
    	//rend visible la page de connections
    	signInPage.signIn();
    		      	 
	}
	
}
