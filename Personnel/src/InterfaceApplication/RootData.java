package InterfaceApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.plaf.SeparatorUI;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.SauvegardeImpossible;



public class RootData {
	
	private GestionPersonnel gestionPersonnel;
	private Employe connectedEmploye;
	
	public RootData(GestionPersonnel gestionPersonnel, Employe connectedEmploye) {
		  this.gestionPersonnel = gestionPersonnel;
		  this.connectedEmploye = connectedEmploye;
	}
	
	public void AccountData()
	{
		frame().setVisible(true);
	}
	
	private JFrame frame()
	{
		JFrame account = new JFrame();
		account.getContentPane().setBackground(Color.decode("#cbc0d3"));
		account.setTitle("Le compte root");
		account.setLayout(new GridBagLayout());
		account.setSize(700,700);
		account.setLocationRelativeTo(null);
		account.setJMenuBar(menuBar());
		account.add(panelCobtainer());
		account.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return account;
	}
	
	private JMenuBar menuBar()
	 {
		 JMenuBar menubar = new JMenuBar();
		 menubar.setPreferredSize(new Dimension(50,50));
		 menubar.setBackground(Color.decode("#540b0e"));
		 JMenu menu = new JMenu("Compte root");
		 menu.setAlignmentX(SwingConstants.WEST);
		 menu.setFont(new Font("Serif", Font.BOLD, 20));
		 menu.setForeground(Color.decode("#fafafa"));
		 menu.setSize(80,80);
		 menubar.add(menu);
		return menubar;
	 }

	private JPanel panelCobtainer()
	{
		JPanel panelContainer = new JPanel();
		panelContainer.setBackground(Color.decode("#cbc0d3"));
		panelContainer.setPreferredSize(new Dimension(400,600));
		GridLayout layout = new GridLayout(3,1);
		layout.setVgap(30);
		panelContainer.setLayout(layout);
		JLabel titleAccount = new JLabel();
		if(connectedEmploye.estRoot()) {
			titleAccount.setText("Compte root");
		}
		else {
			titleAccount.setText("Mon compte");
		}
		titleAccount.setHorizontalAlignment(SwingConstants.CENTER);
		titleAccount.setFont(new Font("Serif", Font.BOLD, 25));
		titleAccount.setForeground(Color.decode("#540b0e"));
		panelContainer.add(titleAccount);
		panelContainer.add(panelLabels());
		panelContainer.add(btns());
		return panelContainer;
	}

	
	private JPanel panelLabels()
	{
		JPanel panelLabels = new JPanel();
		panelLabels.setBorder(BorderFactory.createLineBorder(Color.decode("#540b0e"), 1));
		panelLabels.setBackground(Color.decode("#feeafa"));
		panelLabels.setLayout(new GridLayout(0,2));
		panelLabels.setPreferredSize(new Dimension(700,550));
		ArrayList<JLabel> labels = new ArrayList<>();
		labels.add(new JLabel("Nom : "));
		labels.add(new JLabel(connectedEmploye.getNom()));
		labels.add(new JLabel("Prénom : "));
		labels.add(new JLabel(connectedEmploye.getPrenom()));
		labels.add(new JLabel("Email :"));
		labels.add(new JLabel(connectedEmploye.getMail()));
		labels.add(new JLabel("Password : "));
		labels.add(new JLabel(connectedEmploye.getPassword()));
		if(!connectedEmploye.estRoot()) {
			labels.add(new JLabel("Date arrivée :"));
			labels.add(new JLabel(String.valueOf(connectedEmploye.getDateArrivee())));
			labels.add(new JLabel("Date de départ : "));
			labels.add(new JLabel(String.valueOf(connectedEmploye.getPassword())));
		}
		for(JLabel jlabel : labels) 
		{
			panelLabels.add(jlabel);
			jlabel.setForeground(Color.decode("#540b0e"));
			jlabel.setFont(new Font("Serif", Font.PLAIN, 20));
		}
		return panelLabels;
	}
	
	private JPanel btns()
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#cbc0d3"));
		panel.setLayout(new FlowLayout());
		panel.add(editEmployeBtn());
		panel.add(back());
		return panel;
	}
	
	private JButton editEmployeBtn()
	{
		 JButton editEmpBtn = new JButton();
		 if(connectedEmploye.estRoot()) {
			 editEmpBtn.setText("Editer le root");
		 }else {
			 editEmpBtn.setText("Changer mon compte");
		 }
		 editEmpBtn.setFont(new Font("Serif", Font.PLAIN, 25));
		 editEmpBtn.setBackground(Color.decode("#540b0e"));
		 editEmpBtn.setForeground(Color.decode("#fafafa"));
		 editEmpBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditRoot edit = new EditRoot(gestionPersonnel, connectedEmploye);
				frame().setVisible(false);
				frame().dispose();
				edit.frame().setVisible(true);
				
			}
		});
		 return editEmpBtn;
	}
	
	
	
	private JButton back()
	{
		JButton back = new JButton("Retour");
		back.setFont(new Font("Serif", Font.PLAIN, 25));
		back.setBackground(Color.decode("#540b0e"));
		back.setForeground(Color.decode("#fafafa"));
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame().setVisible(false);
				HomePage home = new HomePage(gestionPersonnel, connectedEmploye);
				home.frame().setVisible(true);
				
			}
		});
		return back;
	}
	
}
