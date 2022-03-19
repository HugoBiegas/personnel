package InterfaceApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;


public class signInPage{
	
    static GestionPersonnel gestionPersonnel;
    private  static HomePage homepage;
    listEmployes listemp;
    Ligue ligue;
    Employe employe;
    private JTextField passwordTxt;
    private TextField login;
    Employe connectedEmploye;
    private JLabel passIncorrect;
    public signInPage(GestionPersonnel gestionPersonnel)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.listemp =  new listEmployes(gestionPersonnel, ligue, connectedEmploye);
	}
	
    public void signIn()
    {
    	frame().setVisible(true);
    }
    
    private JFrame frame()
    {
    	JFrame frame = new JFrame();
    	 frame.getContentPane().setBackground(Color.decode("#cbc0d3"));
    	 frame.setTitle("Sign In !");
    	 frame.setSize(700,700);
    	 frame.setLocationRelativeTo(null);
         frame.setLayout(new GridBagLayout());
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setJMenuBar(menuBar());
         frame.add(container());
    	return frame;
    }
    
    private JPanel container()
    {
    	JPanel panel = new JPanel();
    	panel.setPreferredSize(new Dimension(450,300));
    	panel.setBackground(Color.decode("#feeafa"));
    	//panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#540b0e")));
    	panel.setBorder(BorderFactory.createLineBorder(Color.decode("#540b0e"), 1));
        panel.add(loginPasswordInput());
        return panel;
    }
    
    private JPanel  loginPasswordInput()
    {
    	JPanel panel = new JPanel();
    	panel.setBackground(Color.decode("#feeafa"));
    	GridLayout layout = new GridLayout(0,2);
    	layout.setVgap(40);
    	layout.setHgap(10);
        panel.setLayout(layout);
        panel.add(login());
        panel.add(loginInput());
        panel.add(password());
        panel.add(passInput());
        panel.add(btnConnexion());
        panel.add(passwordFailed());
        return panel;
    }
    
    private JLabel login()
    {
    	JLabel loginL = new JLabel("Login : ");
        loginL.setFont(new Font("Serif", Font.BOLD, 25));
        loginL.setForeground(Color.decode("#540b0e"));
        return loginL;
    }
    
    private TextField loginInput()
    {
    	login = new TextField();
        login.setPreferredSize(new Dimension(150,40));
        
        return login;
    }
    
    private JLabel password()
    {
    	JLabel passwordL = new JLabel("Mot de passe : ");
        passwordL.setFont(new Font("Serif", Font.BOLD, 25));
        passwordL.setForeground(Color.decode("#540b0e"));
        return passwordL;
    }
    
    private JTextField passInput()
    {
    	passwordTxt = new JTextField();
        return passwordTxt;
    }
    
    private  JButton btnConnexion()
    {
    	 JButton btnconnexion = new JButton("Connexion");
         btnconnexion.setPreferredSize(new Dimension(200,50));
         btnconnexion.setBackground(Color.decode("#540b0e"));
         btnconnexion.setForeground(Color.decode("#fafafa"));
         btnconnexion.setFont(new Font("Serif", Font.PLAIN, 20));
         btnconnexion.addActionListener(new ActionListener()
         {

    		/**
    		 *
    		 */
    		@Override
    		public void actionPerformed(ActionEvent arg0) {
    			
    			if(passwordTxt.getText().equals(gestionPersonnel.getRoot().getPassword())){
    				connectedEmploye = gestionPersonnel.getRoot();
    				   HomePage home = new HomePage(gestionPersonnel, connectedEmploye);
    				   home.frame().setVisible(true);
    				   home.getEmploye(gestionPersonnel.getRoot());
    			}
    			else {
    				for(Ligue ligue : gestionPersonnel.getLigues()) {
       		    	 for(Employe employe : ligue.getEmployes()) {
       		    	    if(passwordTxt.getText().equals(employe.getPassword()) && login.getText().equals(employe.getMail())) { 
       		    			connectedEmploye = employe;
       		    			HomePage home = new HomePage(gestionPersonnel, connectedEmploye);
       		    			home.getEmploye(connectedEmploye);
         				    home.frame().setVisible(true);
       		    		 }else if(!passwordTxt.getText().equals(employe.getPassword()) || !login.getText().equals(employe.getMail())) {
       		    			passIncorrect.setText("Login ou mot de passe incorrect!");
       		    		 }
       		    	 }
       		     }
    			}
    		}
         });
         
         return btnconnexion;
    }
    
    
    private JLabel passwordFailed() 
    {
    	passIncorrect = new JLabel();
    	return passIncorrect;
    }
       
    private JMenuBar menuBar()
	 {
		 JMenuBar menubar = new JMenuBar();
		 menubar.setPreferredSize(new Dimension(50,50));
		 menubar.setBackground(Color.decode("#540b0e"));
		 JMenu menu = new JMenu("Connexion");
		 menu.setAlignmentX(SwingConstants.WEST);
		 menu.setFont(new Font("Serif", Font.BOLD, 20));
		 menu.setForeground(Color.decode("#fafafa"));
		 menu.setSize(80,80);
		 menubar.add(menu);
		return menubar;
	 }
    
    public void HomePage() {
    	 JFrame homePage = new JFrame();
    	 homePage.setVisible(true);
		 homePage.setTitle("Home page");
		 homePage.getContentPane().setLayout(new FlowLayout());
		 homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 homePage.pack();
    }
    
    public static void main(String[] args) throws SauvegardeImpossible
    {
    	signInPage signInPage = 
				new signInPage(GestionPersonnel.getGestionPersonnel());
    	signInPage.gestionPersonnel.getRoot();
    	signInPage.signIn();
    		      	 
	}
	
}
