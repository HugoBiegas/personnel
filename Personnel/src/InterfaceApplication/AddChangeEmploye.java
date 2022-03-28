package InterfaceApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.JapaneseDate;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;



public class AddChangeEmploye {
	
	private static Ligue ligue;
	private static GestionPersonnel gestionPersonnel;
	private static PageAcceuil ligues;
	private JTextField nom;
	private JTextField prenom;
	private JTextField mail;
	private JPasswordField pass;
	private JTextField dateArrive;
	private JTextField dateDepart;
	private Employe connectedEmploye;
	private JFrame employeAdd = new JFrame();

	
	
	public AddChangeEmploye(GestionPersonnel gestionPersonnel, Ligue ligue, Employe connectedEmploye) {
		    this.ligue = ligue;
		    this.gestionPersonnel = gestionPersonnel;
		    this.connectedEmploye = connectedEmploye;
	}
	
	public void AddEmploye() {
		
		frame().setVisible(true);
	}
	
	private JFrame frame()
	{
		employeAdd.getContentPane().setBackground(Color.decode("#0080ff"));
		employeAdd.setTitle("Ajouter un employé");
		employeAdd.setLayout(new GridBagLayout());
		employeAdd.setSize(700,700);
		employeAdd.setLocationRelativeTo(null);
		employeAdd.setJMenuBar(menuBar());
		employeAdd.add(panelContainer());
   	 	//icon en haut a gauche
   	 	Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");  
   	 	employeAdd.setIconImage(icon); 
        //permet de faire que la personne ne peux pas modifier la taille de la fenettre
   	 	employeAdd.setResizable(false);
		employeAdd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return employeAdd;
	}
	
	 private JMenuBar menuBar()
	 {
		 JMenuBar menubar = new JMenuBar();
		 menubar.setPreferredSize(new Dimension(60,60));
		 JMenu menu = new JMenu("Quitter");
		 menu.setFont(new Font("Serif", Font.BOLD, 20));
		 menu.setSize(70,70);
		 menu.setForeground(Color.decode("#fafafa"));
		 menu.add(deco());
		 menubar.add(menu);
		 menubar.setBackground(Color.decode("#9f9f9f"));
		return menubar;
	 }
	 private JMenuItem deco()
	 {
		 JMenuItem itemMenu = new JMenuItem("déconnexion");
		 itemMenu.setFont(new Font("Serif", Font.PLAIN, 20));
		 itemMenu.setBackground(Color.decode("#9f9f9f"));
		 itemMenu.setForeground(Color.decode("#fafafa"));
		 itemMenu.addActionListener(gérerdeco());
		 return itemMenu;
	 }
	 
	 private ActionListener gérerdeco() {
		 return new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
						employeAdd.dispose();
						Connexions account = new Connexions(gestionPersonnel);
						account.signIn();
				}
			};
	 }


	
	private JPanel panel()
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#cbc0d3"));
		panel.setLayout(new GridLayout(0,2, 25,25));
		panel.setPreferredSize(new Dimension(500,500));
		JLabel nomL = new JLabel("Nom :");
		nomL.setFont(new Font("Serif", Font.PLAIN, 22));
		JLabel prenomL = new JLabel("Prénom :");
		prenomL.setFont(new Font("Serif", Font.PLAIN, 22));
		JLabel emailL = new JLabel("Email :");
		emailL.setFont(new Font("Serif", Font.PLAIN, 22));
		JLabel passwordL = new JLabel("Password :");
		passwordL.setFont(new Font("Serif", Font.PLAIN, 22));
		JLabel dateArriveeL = new JLabel("Date d'arrivée :");
		dateArriveeL.setFont(new Font("Serif", Font.PLAIN, 22));
		JLabel dateDepartL = new JLabel("Date de départ :");
		dateDepartL.setFont(new Font("Serif", Font.PLAIN, 22));

		
		panel.add(nomL);
		panel.add(nameInput());
		panel.add(prenomL);
		panel.add(secondNameInput());
		panel.add(emailL);
		panel.add(mailInput());
		panel.add(passwordL);
		panel.add(passwordInput());
		panel.add(addEmploye());
		panel.add(cancelAdd());
		return panel;
	}
	
	private JTextField nameInput()
	{
		nom = new JTextField();
		return nom;
	}
	
	private JTextField secondNameInput()
	{
		prenom = new JTextField();
		return prenom;
	}
	
	
	private  JTextField mailInput()
	{
		mail = new JTextField();
		return mail;
	}
	
	private  JPasswordField passwordInput()
	{
		pass = new JPasswordField();
		return pass;
	}
	
	
	
	private  JButton addEmploye()
	{
		JButton addbtn = new JButton("Ajouter");
		addbtn.setBackground(Color.decode("#cbc0d3"));
		addbtn.setForeground(Color.decode("#540b0e"));
		addbtn.addActionListener(addEmployeActions());
		return addbtn;
	}
	private ActionListener addEmployeActions() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String p= new String(pass.getPassword());
				if(!p.equals("") && !nom.getText().equals("") && !prenom.getText().equals("") && mail.getText().contains("@")) {
					ligue.addEmploye(nom.getText(), prenom.getText(), mail.getText(),p,LocalDate.now(),null);
					employeAdd.dispose();
		            listEmployesLigue employesPage = new listEmployesLigue(gestionPersonnel, ligue, connectedEmploye,false);
					employesPage.listEmployes();
				}
				else
					JOptionPane.showMessageDialog(null, "entrai des donnée valide", "Formulaire", JOptionPane.ERROR_MESSAGE);
			}
		};
	}
	
	private JButton cancelAdd()
	{
		JButton cancelbtn = new JButton("Annuler");
		cancelbtn.setBackground(Color.decode("#cbc0d3"));
		cancelbtn.setForeground(Color.decode("#540b0e"));
		cancelbtn.addActionListener(cancelAddAction());
		return cancelbtn;
	}
	
	private ActionListener cancelAddAction() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				employeAdd.dispose();
				listEmployesLigue employesPage = new listEmployesLigue(gestionPersonnel, ligue, connectedEmploye,false);
				employesPage.listEmployes();
			}
		};
	}
	
	private JPanel panelContainer()
	{
		JPanel panelContainer = new JPanel();
		panelContainer.setBackground(Color.decode("#cbc0d3"));
		panelContainer.setLayout(new BorderLayout());
		panelContainer.setPreferredSize(new Dimension(550,600));
		JLabel text = new JLabel("Ajouter un employe");
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setForeground(Color.decode("#540b0e"));
		Border borderTitle = new EmptyBorder(25,25,25,25);
		text.setBorder(borderTitle);
		text.setFont(new Font("Serif", Font.BOLD, 30));
		panelContainer.add(panel(), BorderLayout.CENTER);
		panelContainer.add(text, BorderLayout.NORTH);
		return panelContainer;
	}
}
