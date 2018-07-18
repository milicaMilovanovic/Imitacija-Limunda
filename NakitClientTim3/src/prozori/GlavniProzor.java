package prozori;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import beans.ProizvodiTim3;
import beans.ProizvodiTim3Remote;
import entities.AukcijaTim3;
import tableModeli.GlavniProzorTableModel;


public class GlavniProzor extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private static final String SERVER_PROJECT = "NakitServerTim3";
	private GlavniProzorTableModel modelProizvodi;
	private JTable tableProizvodi;
	private ProizvodiTim3Remote proizvodi;
	
	private JComboBox<String> comboProizvodi;
	private int indeksZaPretragu;
	private JTextField vrednostZaPretragu;
	private JButton btnPretrazi;
	private String parametar;
	
	private JButton btnOsvezi;
	
	public GlavniProzor() throws NamingException {
		
		modelProizvodi = new GlavniProzorTableModel();
		modelProizvodi.addProizvodi(getProizvodiZaTabelu());
		tableProizvodi = new JTable(modelProizvodi);
		tableProizvodi.setFillsViewportHeight(true);
		
		JPanel tabelaPanel = new JPanel(new BorderLayout());
		tabelaPanel.add(new JScrollPane(tableProizvodi), BorderLayout.CENTER);
		
		getContentPane().add(tabelaPanel);
		
		setBounds(0, 0, 800, 700);
		JPanel panel = new JPanel(new BorderLayout()); 
		getContentPane().add(panel, BorderLayout.NORTH);
		
		GridLayout topGridLayout = new GridLayout(2,0); 
		JPanel topGrid = new JPanel(topGridLayout);
		
		JPanel topPartGrid = new JPanel();
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LogIn l = new LogIn(GlavniProzor.this);
				l.setVisible(true);
			}
		});
		
		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Registracija r = new Registracija();
				r.setVisible(true);
			}
			
		});
		topPartGrid.add(loginBtn);
		topPartGrid.add(register);
		
		topGrid.add(topPartGrid);
		
		JSplitPane splitTop = new JSplitPane(); 
		topGrid.add(splitTop);
		panel.add(topGrid, BorderLayout.NORTH);
		
		indeksZaPretragu=0;
		parametar="";
		
		comboProizvodi = new JComboBox<>(new String[] { "Bez pretrage", "Pretrazi po tipu", "Pretrazi po ceni", "Pretrazi po materijalu", "Pretrazi po boji", "Pretrazi po prodavcu" });
		splitTop.setLeftComponent(comboProizvodi);
		
		comboProizvodi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (comboProizvodi.getSelectedIndex())
				{
				case 0:
					indeksZaPretragu=0;
					List<AukcijaTim3> lista = new ArrayList<>();
					try {
						lista = getProizvodiRemote().getAll();
						modelProizvodi.addProizvodi(lista);
						vrednostZaPretragu.setText("");
						break;
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Greska u konekciji sa serverom");
						return;
					}
				case 1:
					indeksZaPretragu=1;
					break;
				case 2:
					indeksZaPretragu=2;
					break;
				case 3:
					indeksZaPretragu=3;
					break;
				case 4:
					indeksZaPretragu=4;
					break;
				default:
					indeksZaPretragu=5;
					break;
				}
			}
		});
		
		vrednostZaPretragu=new JTextField();
		splitTop.setRightComponent(vrednostZaPretragu);
		
		vrednostZaPretragu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parametar = vrednostZaPretragu.getText();
				if (parametar.equals("") && indeksZaPretragu != 0) {
					JOptionPane.showMessageDialog(null, "Niste uneli parametar za pretragu");
					return;
				}
				List<AukcijaTim3> lista = null;
				try {
					switch(indeksZaPretragu)
					{
					case 0:
						lista = getProizvodiRemote().getAll();
						break;
					case 1:
						lista = getProizvodiRemote().getByTip(parametar);
						break;
					case 2:
						try {
							int paramInt = Integer.parseInt(parametar); 
							lista = getProizvodiRemote().getByCena(paramInt);
							break;
						} catch (IllegalArgumentException exc) {
							JOptionPane.showMessageDialog(null, "Niste uneli ispravan broj za cenu");
							return;
						}
					case 3:
						lista = getProizvodiRemote().getByMaterijal(parametar);
						break;
					case 4:
						lista = getProizvodiRemote().getByBoja(parametar);
						break;
					default:
						lista = getProizvodiRemote().getByProdavac(parametar);
						break;
					}
					modelProizvodi.addProizvodi(lista);
				} catch(NamingException ex) {
					JOptionPane.showMessageDialog(null, "Greska u konekciji sa serverom");
					return;
				}
			}
		});
		
		btnPretrazi = new JButton("Pretrazi");
		panel.add(btnPretrazi, BorderLayout.EAST);
		
		btnOsvezi = new JButton("Osvezi");
		panel.add(btnOsvezi, BorderLayout.WEST);
				
		btnOsvezi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					updateTables();
				}
				catch(Exception ex) {
							
				}
			}
		}); 
		
		btnPretrazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parametar = vrednostZaPretragu.getText();
				if (parametar.equals("") && indeksZaPretragu != 0) {
					JOptionPane.showMessageDialog(null, "Niste uneli parametar za pretragu");
					return;
				}
				List<AukcijaTim3> lista = null;
				try {
					switch(indeksZaPretragu)
					{
					case 0:
						lista = getProizvodiRemote().getAll();
						break;
					case 1:
						lista = getProizvodiRemote().getByTip(parametar);
						break;
					case 2:
						try {
							int paramInt = Integer.parseInt(parametar); 
							lista = getProizvodiRemote().getByCena(paramInt);
							break;
						} catch (IllegalArgumentException exc) {
							JOptionPane.showMessageDialog(null, "Niste uneli ispravan broj za cenu");
							return;
						}
					case 3:
						lista = getProizvodiRemote().getByMaterijal(parametar);
						break;
					case 4:
						lista = getProizvodiRemote().getByBoja(parametar);
						break;
					default:
						lista = getProizvodiRemote().getByProdavac(parametar);
						break;
					}
					modelProizvodi.addProizvodi(lista);
				} catch(NamingException ex) {
					JOptionPane.showMessageDialog(null, "Greska u konekciji sa serverom");
					return;
				}
			}
		});
		
		JLabel labelNaslov = new JLabel("<html> <h2> Svi proizvodi </h2> </html> ");
		tabelaPanel.add(labelNaslov, BorderLayout.NORTH);
		labelNaslov.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
	
	
	private ProizvodiTim3Remote getProizvodiRemote() throws NamingException {
		
		if (proizvodi == null)
		{
			InitialContext ctx = new InitialContext();
			String name = "ejb:/" + SERVER_PROJECT + "//"
				+ ProizvodiTim3.class.getSimpleName() + "!" + ProizvodiTim3Remote.class.getName();
			proizvodi = (ProizvodiTim3Remote) ctx.lookup(name);
		}
		return proizvodi;
		
	}
	
	private List<AukcijaTim3> getProizvodiZaTabelu() throws NamingException {
		
		final List<AukcijaTim3> lista = getProizvodiRemote().getAll();
		return lista;
		
	}
	
	public static void main(String [] args) throws NamingException {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // Ako nije instaliran, ostavimo trenutni
		}
		JFrame glavniProzor = new GlavniProzor();
		glavniProzor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		glavniProzor.setTitle("Pocetna");
		glavniProzor.setVisible(true);
	}
	
	public void updateTables() throws NamingException{
		List<AukcijaTim3> sve =new ArrayList<>();
		sve = getProizvodiRemote().getAll();
		modelProizvodi.addProizvodi(sve);
	}
	
}
