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




public class listEmployes {
	
	private  GestionPersonnel gestionPersonnel;
	private  Ligue ligue;
	private Employe connectedEmploye;
	private JFrame employes = new JFrame();
	
	 public listEmployes(GestionPersonnel gestionPersonnel, Ligue ligue, Employe connectedEmploye) {
		    this.gestionPersonnel = gestionPersonnel;
		    this.ligue = ligue;
		    this.connectedEmploye = connectedEmploye;
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
		JLabel label = new JLabel("Il y a aucun employé dans cette ligue");
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
	
	private JMenu menuLigues()
	{
		JMenu ligues = new JMenu("Mon compte");
		 ligues.setFont(new Font("Serif", Font.BOLD, 20));
		 ligues.setForeground(Color.decode("#fafafa"));
		 ligues.addSeparator();
         ligues.add(menuItem());
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
				if (!e.getValueIsAdjusting()){
		            JList source = (JList)e.getSource();
		            Employe selectedEmploye = (Employe) source.getSelectedValue();
		            frame().setVisible(false);
		            showEmploye employe = new showEmploye(gestionPersonnel, selectedEmploye, ligue, connectedEmploye);
		            employe.employeShow();
		        }
			}
		};
	}
	
	private JLabel titleLigue()
	{
		JLabel title = new JLabel();
		//nom de la ligue et de l'admin
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
		return panel;
	}
	
	private JButton renameLigue()
	{
	    JButton renameLigue = new JButton("Renommer la ligue");
	    if(!gestionPersonnel.haveWritePermission(ligue, connectedEmploye)) {
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
				while(cpt== false) {
					String inputValue = JOptionPane.showInputDialog("Nom de la ligue"); 
				if(inputValue == null)
					cpt =true;
				else if(inputValue != null && !inputValue.equals("")) {
						cpt = true;
						ligue.setNom(inputValue);
					}
				}
				HomePage pageLigues = new HomePage(gestionPersonnel, connectedEmploye);
				pageLigues.frame().setVisible(true);
			}
		};
	}
	
	
	private JButton deleteLigue()
	{
		JButton deleteLigue = new JButton("Supprimer la ligue");
		 if(!gestionPersonnel.haveWritePermission(ligue, connectedEmploye)) {
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
				HomePage pageLigues = new HomePage(gestionPersonnel, connectedEmploye);
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
				HomePage pageLigues = new HomePage(gestionPersonnel, connectedEmploye);
				employes.dispose();
				pageLigues.frame().setVisible(true);
			}
		};
	}
	
	private JButton addEmploye()
	{
		JButton addEmploye = new JButton("Ajouter un employé");
		if(!gestionPersonnel.haveWritePermission(ligue, connectedEmploye)) {
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

}
