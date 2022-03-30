package InterfaceApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SortedSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;




public class listEmployesLigue {
	
	private  GestionPersonnel gestionPersonnel;
	private  Ligue ligue;
	private Employe connectedEmploye;
	private JFrame employes = new JFrame();
	private boolean Histo=false;
	
	 public listEmployesLigue(GestionPersonnel gestionPersonnel, Ligue ligue, Employe connectedEmploye,boolean Histo) {
		    this.gestionPersonnel = gestionPersonnel;
		    this.ligue = ligue;
		    this.connectedEmploye = connectedEmploye;
		    this.Histo = Histo;
	}
	 
	public void listEmployes()
	{
		frame().setVisible(true);
	}

	public JFrame frame()
	{
		employes.getContentPane().setBackground(Color.decode("#0080ff"));
		employes.setSize(700,700);
		employes.setLocationRelativeTo(null);
		employes.setJMenuBar(menuBar());
		employes.setLayout(new GridBagLayout());
		employes.add(container());
		//titre
		employes.setTitle("Liste employée");
   	 	//icon en haut a gauche
   	 	Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");  
   	 	employes.setIconImage(icon); 
        //permet de faire que la personne ne peux pas modifier la taille de la fenettre
   	 	employes.setResizable(false);
		employes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		return employes;
	}
	private JPanel container()
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#9f9f9f"));
		panel.setMinimumSize(new Dimension(500,500));
		BorderLayout layout = new BorderLayout();
		layout.setVgap(25);
		panel.setLayout(layout);
		panel.add(backAndTitleComponent(), BorderLayout.NORTH);
		panel.add(employesList(), BorderLayout.CENTER);
		panel.add(renameAndDelete(), BorderLayout.SOUTH);
		return panel;
	}
	
	
	private JPanel backAndTitleComponent()
	{
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2,1);
		layout.setVgap(15);
		panel.setLayout(layout);
		panel.setBackground(Color.decode("#9f9f9f"));
		Box back = Box.createHorizontalBox();
		back.add(back());
		panel.add(back);
		panel.add(titleLigue());
		return panel;
	}
	
	private JPanel employesList()
	{
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200,500));
		panel.setBackground(Color.decode("#E9967A"));
		if(ligue.getEmployes().size() > 0)
		{
			panel.add(list());
		}else {
			panel.add(notEmployesFunded());
		}
		return panel;
	}
	
	private JLabel notEmployesFunded()
	{
		JLabel label;
		if(Histo == true)
			 label = new JLabel("il n'y as pas d'employé virer dans cette ligue");
		else
			 label = new JLabel("Il y a aucun employé dans cette ligue");
		label.setFont(new Font("Serif", Font.BOLD, 22));
		label.setForeground(Color.decode("#cbc0d3"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}

	public JMenuBar menuBar()
	{
		 JMenuBar menubar = new JMenuBar();
		 menubar.setPreferredSize(new Dimension(60,60));
		 menubar.add(menuLigues());
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
						employes.dispose();
						Connexions account = new Connexions(gestionPersonnel);
						account.signIn();
				}
			};
	 }

	
	private JMenu menuLigues()
	{
		JMenu ligues = new JMenu("Mon compte");
		 ligues.setFont(new Font("Serif", Font.BOLD, 20));
		 ligues.setForeground(Color.decode("#fafafa"));
		 ligues.addSeparator();
         ligues.add(menuItem());
         ligues.add(deco());
		 return ligues;
	}
	
	 private JMenuItem menuItem()
	 {
		 JMenuItem itemMenu = new JMenuItem("Gérer mon compte");
		 itemMenu.setFont(new Font("Serif", Font.PLAIN, 20));
		 itemMenu.setBackground(Color.decode("#9f9f9f"));
		 itemMenu.setForeground(Color.decode("#fafafa"));
		 itemMenu.addActionListener(menutAction());
		 return itemMenu;
	 }
	 
	 private ActionListener menutAction() {
		 return new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					    frame().setVisible(false);
						RootData account = new RootData(gestionPersonnel, connectedEmploye);
						account.AccountData();
				}
			};
	 }
	
	public JList<Employe> list()
	{
		SortedSet<Employe> emp = ligue.getEmployes();
		JList<Employe> empL = new JList<>();
		DefaultListModel<Employe> listEmp = new DefaultListModel<>();
		empL.setModel(listEmp);
		//utilisateions des model pour mettre tout les employer
		for(Employe employe : emp)
		{
			if(Histo == false && employe.getDateDepart() == null)
				listEmp.addElement(employe);
			else if(Histo == true && employe.getDateDepart() !=null)
				listEmp.addElement(employe);
			
		}
		empL.addListSelectionListener(listeEploye());
		 empL.setFont(new Font("Serif", Font.BOLD, 22));
		 empL.setBackground(Color.decode("#E9967A"));
		 empL.setForeground(Color.decode("#540b0e"));
		 DefaultListCellRenderer renderer =  (DefaultListCellRenderer)empL.getCellRenderer();  
		 renderer.setHorizontalAlignment(JLabel.CENTER);
		 empL.setFixedCellWidth(500);
		 empL.setFixedCellHeight(40);
		return empL;
	}
	
	private ListSelectionListener listeEploye() {
		return new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//si il y as un changement 
				if (!e.getValueIsAdjusting()){
		            JList source = (JList)e.getSource();
		            Employe selectedEmploye = (Employe) source.getSelectedValue();
		            frame().setVisible(false);
		            InfoEmploye employe;
		            if(Histo==true)
		            	 employe = new InfoEmploye(gestionPersonnel, selectedEmploye, ligue, connectedEmploye,true);
		            else
		            	 employe = new InfoEmploye(gestionPersonnel, selectedEmploye, ligue, connectedEmploye,false);

		            employe.employeShow();
		        }
			}
		};
	}
	
	private JLabel titleLigue()
	{
		JLabel title = new JLabel();
		//nom de la ligue et de l'admin
		if(Histo == true)
			title.setText("Historique de la ligue :"+ligue.getNom());
		else
			title.setText(ligue.getNom() + " administrée par  " + ligue.getAdministrateur().getNom());
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 27));
		title.setForeground(Color.decode("#540b0e"));
		return  title;
	}
	
	private JPanel renameAndDelete()
	{
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(650,150));
		panel.setBackground(Color.decode("#9f9f9f"));
		FlowLayout layout = new FlowLayout();
		panel.setLayout(layout);
		
		Box delete = Box.createHorizontalBox();
		delete.add(deleteLigue());
		
		
		Box rename = Box.createHorizontalBox();
		rename.add(renameLigue());
		
		
		Box addEmploye = Box.createHorizontalBox();
		addEmploye.add(addEmploye());
		
		panel.add(delete);
		panel.add(rename);
		panel.add(addEmploye);
		panel.add(histo());
		return panel;
	}
	
	private JButton renameLigue()
	{
	    JButton renameLigue = new JButton("Renommer la ligue");
	    if(!gestionPersonnel.haveWritePermission(ligue, connectedEmploye) || Histo==true) {
	    	renameLigue.setEnabled(false);
	 }
	    renameLigue.setFont(new Font("Serif", Font.BOLD, 18));
	    renameLigue.setForeground(Color.decode("#fafafa"));
	    renameLigue.setBackground(Color.decode("#540b0e"));
	    renameLigue.setPreferredSize(new Dimension(200,35));
	    renameLigue.addActionListener(renameLigueAction());
	    return renameLigue;
	}
	private ActionListener renameLigueAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				employes.dispose();
				boolean cpt=false;
				while(cpt==false) {
					String inputValue = JOptionPane.showInputDialog("Nom de la ligue"); 
					if(inputValue == null) 
						cpt=true;
					else if(!inputValue.equals("") && inputValue != null && gestionPersonnel.isExistLigue(inputValue.toString()) == false) {
						cpt = true;
						ligue.setNom(inputValue);
					}
				}
				PageAcceuil pageLigues = new PageAcceuil(gestionPersonnel, connectedEmploye);
				pageLigues.frame().setVisible(true);
			}
		};
	}
	
	
	private JButton deleteLigue()
	{
		JButton deleteLigue = new JButton("Supprimer la ligue");
		 if(!gestionPersonnel.haveWritePermission(ligue, connectedEmploye) || Histo==true) {
			      deleteLigue.setEnabled(false);
		 }
		deleteLigue.setFont(new Font("Serif", Font.BOLD, 18));
		deleteLigue.setForeground(Color.decode("#fafafa"));
		deleteLigue.setBackground(Color.decode("#540b0e"));
		deleteLigue.setPreferredSize(new Dimension(200,35));
		deleteLigue.addActionListener(deletLigueAction());
		return deleteLigue;
	}
	private ActionListener deletLigueAction() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ligue.remove();
				} catch (SauvegardeImpossible e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "la ligue a été supprimée", "supprimer la ligue", JOptionPane.INFORMATION_MESSAGE);
				PageAcceuil pageLigues = new PageAcceuil(gestionPersonnel, connectedEmploye);
				frame().dispose();
				pageLigues.frame().setVisible(true);
			}
		};
	}
	
	private JButton back()
	{
		JButton btn = new JButton("Retour");
		btn.setBackground(Color.decode("#48cae4"));
		btn.setForeground(Color.decode("#fafafa"));
		btn.setPreferredSize(new Dimension(130,30));
		btn.setFont(new Font("Serif", Font.PLAIN, 22));
		btn.addActionListener(backAction());
		return btn;
	}
	private ActionListener backAction() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Histo == true) {
						listEmployesLigue pageLigues = new listEmployesLigue(gestionPersonnel,ligue, connectedEmploye,false);
						pageLigues.listEmployes();
				}
				else {
					PageAcceuil pageLigues = new PageAcceuil(gestionPersonnel, connectedEmploye);
					pageLigues.frame().setVisible(true);

				}
				employes.dispose();
			}
		};
	}
	
	private JButton addEmploye()
	{
		JButton addEmploye = new JButton("Ajouter un employé");
		if(!gestionPersonnel.haveWritePermission(ligue, connectedEmploye) || Histo==true) {
			addEmploye.setEnabled(false);
	 }
		addEmploye.setFont(new Font("Serif", Font.BOLD, 20));
		addEmploye.setBackground(Color.decode("#540b0e"));
		addEmploye.setForeground(Color.decode("#fafafa"));
		addEmploye.addActionListener(addEmployerAction());
		return addEmploye;
	}
	
	private ActionListener addEmployerAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddChangeEmploye add = new AddChangeEmploye(gestionPersonnel, ligue, connectedEmploye);
				employes.dispose();
				add.AddEmploye();
			}
		};
	}
	private JButton histo()
	{
		JButton addEmploye = new JButton("Historique ligue");
		if(!gestionPersonnel.haveWritePermission(ligue, connectedEmploye) || Histo==true) {
			addEmploye.setEnabled(false);
	 }
		addEmploye.setFont(new Font("Serif", Font.BOLD, 20));
		addEmploye.setBackground(Color.decode("#540b0e"));
		addEmploye.setForeground(Color.decode("#fafafa"));
		addEmploye.addActionListener(addhistoAction());
		return addEmploye;
	}
	
	private ActionListener addhistoAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listEmployesLigue add = new listEmployesLigue(gestionPersonnel, ligue, connectedEmploye,true);
				employes.dispose();
				add.listEmployes();
			}
		};
	}

}
