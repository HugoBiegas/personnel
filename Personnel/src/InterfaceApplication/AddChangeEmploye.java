package InterfaceApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
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
	private static HomePage ligues;
	private JTextField nom;
	private JTextField prenom;
	private JTextField mail;
	private JTextField pass;
	private JTextField dateArrive;
	private JTextField dateDepart;
	private Employe connectedEmploye;
	
	
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
		JFrame employeAdd = new JFrame();
		employeAdd.getContentPane().setBackground(Color.decode("#cbc0d3"));
		employeAdd.setTitle("Ajouter un employé");
		employeAdd.setLayout(new GridBagLayout());
		employeAdd.setSize(700,700);
		employeAdd.setLocationRelativeTo(null);
		employeAdd.setJMenuBar(menuBar());
		employeAdd.add(panelContainer());
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
		 menubar.add(menu);
		 menubar.setBackground(Color.decode("#6f1d1b"));
		return menubar;
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
		//panel.add(dateArriveeL);
		//panel.add(DateArriveInput());
		//panel.add(dateDepartL);
		//panel.add(DateDepartInput());
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
	
	private  JTextField passwordInput()
	{
		pass = new JTextField();
		return pass;
	}
	
	private  JTextField DateArriveInput()
	{
		dateArrive = new JTextField();
		return dateArrive;
	}
	
	private JTextField DateDepartInput()
	{
		dateDepart = new JTextField();
		return dateDepart;
	}
	
	
	
	private  JButton addEmploye()
	{
		JButton addbtn = new JButton("Ajouter");
		addbtn.setBackground(Color.decode("#feeafa"));
		addbtn.setForeground(Color.decode("#540b0e"));
		addbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ligue.addEmploye(nom.getText(), prenom.getText(), mail.getText(), pass.getText(), null, null);
	            frame().setVisible(false);
	            frame().dispose();
	            listEmployes employesPage = new listEmployes(gestionPersonnel, ligue, connectedEmploye);
				employesPage.listEmployes();
			}
		});
		return addbtn;
	}
	
	private JButton cancelAdd()
	{
		JButton cancelbtn = new JButton("Annuler");
		cancelbtn.setBackground(Color.decode("#feeafa"));
		cancelbtn.setForeground(Color.decode("#540b0e"));
		cancelbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame().setVisible(false);
				frame().dispose();
				listEmployes employesPage = new listEmployes(gestionPersonnel, ligue, connectedEmploye);
				employesPage.listEmployes();
			}
		});
		return cancelbtn;
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
