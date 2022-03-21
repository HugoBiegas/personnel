package InterfaceApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.SauvegardeImpossible;

public class EditRoot {
 
private GestionPersonnel gestionPersonnel;
private JTextField nom;
private JTextField prenom;
private JTextField email;
private JPasswordField pass;
private Employe connectedEmploye;
private JTextField arrive;
private JTextField depart;
private JFrame root = new JFrame();

	public EditRoot(GestionPersonnel gestionPersonnel, Employe connectedEmploye) {
		  this.gestionPersonnel = gestionPersonnel;
		  this.connectedEmploye = connectedEmploye;
	}
	
	public JFrame frame()
	{
		root.getContentPane().setBackground(Color.decode("#0080ff"));
		root.setTitle("Editer le root");
		root.setSize(700,700);
		root.setLocationRelativeTo(null);
		root.setLayout(new GridBagLayout());
		root.setJMenuBar(menuBar());
		root.add(panelContainer());
   	 	//icon en haut a gauche
   	 	Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");  
   	 	root.setIconImage(icon); 
        //permet de faire que la personne ne peux pas modifier la taille de la fenettre
   	 	root.setResizable(false);
		root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return root;
	}
	private JMenuBar menuBar()
	 {
		 JMenuBar menubar = new JMenuBar();
		 menubar.setPreferredSize(new Dimension(50,50));
		 menubar.setBackground(Color.decode("#9f9f9f"));
		 JMenu menu = new JMenu("Compte root");
		 menu.setAlignmentX(SwingConstants.WEST);
		 menu.setFont(new Font("Serif", Font.BOLD, 20));
		 menu.setForeground(Color.decode("#fafafa"));
		 menu.setSize(80,80);
		 menubar.add(menu);
		return menubar;
	 }
	
	private JPanel panelContainer()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.add(labelAndInput());
		return panel;
	}
	
	private JPanel labelAndInput()
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#9f9f9f"));
		panel.setBorder(BorderFactory.createLineBorder(Color.decode("#cbc0d3"), 3));
		GridLayout layout = new GridLayout(0,2);
		layout.setVgap(40);
		layout.setHgap(10);
		panel.setLayout(layout);
		panel.add(nom());
		panel.add(nomInput());
		panel.add(prenom());
		panel.add(prenomInput());
		panel.add(email());
		panel.add(emailInput());
		panel.add(pass());
		panel.add(passInput());
		//si pas un root on mais d'autre truque
		if(!connectedEmploye.estRoot()) {
			panel.add(arrive());
			panel.add(dateArrive());
			panel.add(depart());
			panel.add(dateDepart());
		}
		//les boutons
		panel.add(save());
		panel.add(cancel());
		return panel;
	}
	
    private JLabel nom()
    {
    	JLabel label = new JLabel("Nom :");
    	label.setFont(new Font("Serif", Font.PLAIN, 20));
    	label.setForeground(Color.decode("#540b0e"));
    	return label;
    }
    
    private JLabel prenom()
    {
    	JLabel label = new JLabel("Prénom :");
    	label.setFont(new Font("Serif", Font.PLAIN, 20));
    	label.setForeground(Color.decode("#540b0e"));
    	return label;
    }
    
    private JLabel email()
    {
    	JLabel label = new JLabel("Email :");
    	label.setFont(new Font("Serif", Font.PLAIN, 20));
    	label.setForeground(Color.decode("#540b0e"));
    	return label;
    }
    
    private JLabel pass()
    {
    	JLabel label = new JLabel("Password :");
    	label.setFont(new Font("Serif", Font.PLAIN, 20));
    	label.setForeground(Color.decode("#540b0e"));
    	return label;
    }
    
    
    private JLabel arrive()
    {
    	JLabel label = new JLabel("Date d'arrivée :");
    	label.setFont(new Font("Serif", Font.PLAIN, 20));
    	label.setForeground(Color.decode("#540b0e"));
    	return label;
    }
    
    private JLabel depart()
    {
    	JLabel label = new JLabel("Date de départ :");
    	label.setFont(new Font("Serif", Font.PLAIN, 20));
    	label.setForeground(Color.decode("#540b0e"));
    	return label;
    }
    
    
    private JTextField nomInput()
    {
    	nom = new JTextField();
    	nom.setText(connectedEmploye.getNom());
    	return nom;
    }
    
    private JTextField prenomInput()
    {
    	prenom = new JTextField();
    	prenom.setText(connectedEmploye.getPrenom());
    	return prenom;
    }
    
    private JTextField emailInput()
    {
    	email = new JTextField();
    	email.setText(connectedEmploye.getMail());
    	return email;
    }
    
    private JTextField passInput()
    {
    	pass = new JPasswordField();
    	pass.setText(connectedEmploye.getPassword());
    	return pass;
    }
    
    private JTextField dateArrive()
    {
    	arrive = new JTextField();
    	arrive.setText(String.valueOf(connectedEmploye.getDateArrivee()));
    	return arrive;
    }
    
    private JTextField dateDepart()
    {
    	depart = new JTextField();
    	depart.setText(String.valueOf(connectedEmploye.getDateDepart()));
    	return depart;
    }
    
    //bouton de sauvegarde
    private JButton save()
    {
    	JButton btn = new JButton("Enregistrer");
    	btn.setBackground(Color.decode("#540b0e"));
		btn.setForeground(Color.decode("#fafafa"));
		btn.setFont(new Font("Serif", Font.PLAIN, 20));
		btn.addActionListener(EnregisterAction());
    	return btn;
    }
    
    private ActionListener EnregisterAction() {
    	return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String p= new String(pass.getPassword());
				 if(email.getText().contains("@") && !p.equals("") && !nom.getText().equals("") && !prenom.getText().equals("")) {
					 	connectedEmploye.setMail(email.getText());
					 	connectedEmploye.setNom(nom.getText());
					 	connectedEmploye.setPrenom(prenom.getText());
					 	connectedEmploye.setPassword(p);
						 //update
						 if(connectedEmploye.estRoot()) {
							 try {
							 	gestionPersonnel.updateRoot(gestionPersonnel.getRoot());
						 	} catch (SauvegardeImpossible e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}					 
						 }else {
							 connectedEmploye.updateEmploye(connectedEmploye);
						 }
						 root.dispose();
						 HomePage home  = new HomePage(gestionPersonnel, connectedEmploye);
						 home.frame().setVisible(true);
				 }
				 else 
					JOptionPane.showMessageDialog(null, "entrai des donnée valide", "Formulaire", JOptionPane.ERROR_MESSAGE);
				 
				
			}
		};
    }
    //bonton pour annuler sans modif
    private JButton cancel()
    {
    	JButton btn = new JButton("Annuler");
    	btn.setBackground(Color.decode("#540b0e"));
		btn.setForeground(Color.decode("#fafafa"));
		btn.setFont(new Font("Serif", Font.PLAIN, 20));
		btn.addActionListener(annuleAction());
    	return btn;
    }
    
    private ActionListener annuleAction() {
    	return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				root.dispose();
				HomePage home = new HomePage(gestionPersonnel, connectedEmploye);
				home.frame().setVisible(true);
			}
		};
    }
    
}
