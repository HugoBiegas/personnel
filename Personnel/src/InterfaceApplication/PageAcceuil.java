package InterfaceApplication;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;
import javax.swing.UIManager; 




/**
 * @author 33782
 *
 */
public class PageAcceuil {
	
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	Color selectCouleur = Color.RED;
    public JTextField inputName;
    public JDialog add;
    public JFrame home = new JFrame();
    private  Employe connectedEmploye;
    
    
    
	 public PageAcceuil(GestionPersonnel gestionPersonnel, Employe connectedEmploye) {
		    this.gestionPersonnel = gestionPersonnel;
		    this.connectedEmploye = connectedEmploye;
	 }
	 
	public JFrame frame()
	{
		//background  
		home.getContentPane().setBackground(Color.decode("#0080ff"));
		//taille
		home.setSize(700,700);
		//titre
		home.setTitle("Page d'acueil");
		//menu
		home.setJMenuBar(menuBar());
   	 	//center la fenettre
   	 	home.setLocationRelativeTo(null);
		//la grille
		home.setLayout(new GridBagLayout());
		//la panel aux milieux
		home.add(panelContainer());
		//quiter
		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pour pas qu'il puisse modifier
		home.setResizable(false);
		//icone
   	 	Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");  
   	 	home.setIconImage(icon); 
		 return home;
	}
	
	private JPanel panelContainer()
	 {
		 JPanel panelContainer = new JPanel();
		 panelContainer.setBackground(Color.decode("#9f9f9f"));
		 panelContainer.setMinimumSize(new Dimension(500,350));
		 BorderLayout layout = new BorderLayout();
		 layout.setVgap(30);
		 panelContainer.setLayout(layout);
		 Box boxaddLigueBtn = Box.createHorizontalBox();
		 //center les composent
	     boxaddLigueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	     //ajouter le bouton
		 boxaddLigueBtn.add(addLigueBtn());
		 	 
		 panelContainer.add(title(), BorderLayout.NORTH);
		 if(connectedEmploye.estRoot()) {
			 panelContainer.add(boxaddLigueBtn, BorderLayout.SOUTH);
		 }
		 panelContainer.add(scrollListPanel(), BorderLayout.CENTER);
		 return panelContainer;
	 }
	
	
	 private JLabel title()
	 {
		 JLabel title = new JLabel("Liste des ligues");
		 title.setHorizontalAlignment(SwingConstants.CENTER);
		 title.setFont(new Font("Serif", Font.BOLD, 25));
		 return title;
	 }
	 
	 private JButton addLigueBtn()
	 {
		 JButton addLigueBtn = new JButton("Ajouter une ligue");
		 addLigueBtn.setFont(new Font("Serif", Font.BOLD, 20));
		 addLigueBtn.setBackground(Color.decode("#540b0e"));
		 addLigueBtn.setForeground(Color.decode("#fafafa"));
		 addLigueBtn.addActionListener(actionAjou());
		 return addLigueBtn;
	 }
	 
	 private ActionListener actionAjou() {
		 return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					home.dispose();
					try {						
					boolean cpt=false;
					//boucle pour avoir des ligue coérente
					while(cpt==false) {
						String inputValue = JOptionPane.showInputDialog("Nom de la ligue"); 
						if(inputValue == null) 
							cpt=true;
						else if(!inputValue.equals("") && inputValue != null && gestionPersonnel.isExistLigue(inputValue.toString()) == false) {
							cpt = true;
							gestionPersonnel.addLigue(inputValue);
						}
					}
					} catch (SauvegardeImpossible e1) {
						e1.printStackTrace();
					}
					PageAcceuil home = new PageAcceuil(gestionPersonnel, connectedEmploye);
					home.frame().setVisible(true);
				}
			};
	 }
	 
	 
	 public  JTextField ligueNameInput()
	 {
		 inputName = new JTextField();
		 return inputName;
	 }
	 
	 private JMenuBar menuBar()
	 {
		 JMenuBar menubar = new JMenuBar();
		 menubar.setPreferredSize(new Dimension(60,60));
		 JMenu menu = new JMenu("Compte root");
		 //itéme quand cliquer sur le btn
		 menu.add(menuItem());
		 menu.add(deco());
		 //stuyle btn
		 menu.setFont(new Font("Serif", Font.BOLD, 20));
		 menu.setForeground(Color.decode("#fafafa"));
		 menubar.setBackground(Color.decode("#9f9f9f"));
		 menubar.add(menu);
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
						home.dispose();
						Connexions account = new Connexions(gestionPersonnel);
						account.signIn();
				}
			};
	 }

	 
	 
	 private JMenuItem menuItem()
	 {
		 JMenuItem itemMenu = new JMenuItem("Gérer le compte root");
		 itemMenu.setFont(new Font("Serif", Font.PLAIN, 20));
		 itemMenu.setBackground(Color.decode("#9f9f9f"));
		 itemMenu.setForeground(Color.decode("#fafafa"));
		 //actions pour chaque itéme
		 itemMenu.addActionListener(itemAction());
		 return itemMenu;
	 }
	 
	 private ActionListener itemAction() {
		 return new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//on regarde si la personne qui clique ces le root ou pas 
					if(connectedEmploye.equals(gestionPersonnel.getRoot())) {
						//envoi des donnée
						RootData root = new RootData(gestionPersonnel, connectedEmploye);
						//rendre visible la page
						root.AccountData();
						home.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Vous n'avez pas l'accés à cette page", "droits insuffisants", JOptionPane.ERROR_MESSAGE);
					}
				}
			};
	 }
	 
	  
	 public JList<Ligue>  listLigues()
	 {
		 //récupérations des ligue
		 SortedSet<Ligue> choix = gestionPersonnel.getLigues();
		 JList<Ligue> listLigues = new JList<>();
		 listLigues.setOpaque(true);
		 DefaultListModel<Ligue> listLigue = new DefaultListModel<>();
		 listLigues.setFont(new Font("Serif", Font.BOLD, 22));
		 //permet de prendre la model
		 listLigues.setModel(listLigue);
		 //si il est root il vois tout les ligues
		 //sinon il vois que les ligues que le root lui as mis
		 if(connectedEmploye.equals(gestionPersonnel.getRoot())) {
		 for (Ligue ligue : choix) {
			   listLigue.addElement(ligue);
			}			 
		 }else {
			 for (Ligue ligue : choix) {
				 if(ligue.getNom().equals(connectedEmploye.getLigue().getNom()))
					 listLigue.addElement(ligue);
				} 
		 }

		 listLigues.addListSelectionListener(ItemAction());
		 //permet de prendre la model
		 listLigues.setModel(listLigue);
		 listLigues.setBackground(Color.decode("#9f9f9f"));
		 listLigues.setForeground(Color.decode("#540b0e"));
		 DefaultListCellRenderer renderer =  (DefaultListCellRenderer)listLigues.getCellRenderer();  
		 renderer.setHorizontalAlignment(JLabel.CENTER);
		 listLigues.setFixedCellHeight(50);
		 return listLigues;
	 }
	 private ListSelectionListener ItemAction() {
		 return new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()){
						//récupérations de la ou on a cliquer
			            JList source = (JList)e.getSource();
			            //récupérations du nom de la ou on a cliquer 
			            Ligue selected = (Ligue) source.getSelectedValue();
			            //créations de la prochaine fenettre
			            listEmployesLigue ligue = new listEmployesLigue(gestionPersonnel,selected, connectedEmploye);
			            //
			            ligue.listEmployes();
			            home.dispose();
			        }
					
				}
			}; 
	 }
	 
	 private JScrollPane scrollList()
	 {
	    JScrollPane scrollpane = new JScrollPane(listLigues());
	    //taille de la liste des ligue
	    scrollpane.setPreferredSize(new Dimension(450,300));
	    scrollpane.getViewport().setBackground(Color.decode("#9f9f9f"));
	    return scrollpane;
	 }
	 
	 private JPanel scrollListPanel()
	 {
		 JPanel panel = new JPanel();
		 panel.add(scrollList());
		 return panel;
	 }
	 
}

