package prozori;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import beans.KorisniciTim3Remote;
import beans.ProizvodiTim3;
import beans.ProizvodiTim3Remote;
import entities.AukcijaTim3;
import entities.KorisnikTim3;
import tableModeli.MojeAukcijeTableModel;
import tableModeli.ProizvodTableModel;
import tableModeli.UcestvovaoAukcijeTableModel;

public class KorisnikovProzor extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private String korisnickoIme;
	private KorisniciTim3Remote bean;
	private ProizvodiTim3Remote proizvodi;
	private static final String SERVER_PROJECT = "NakitServerTim3";
	
	private JTable tableProizvodi;
	private JTable tableMojeAukcije;
	private JTable tableUcestvovaoAukcije;
	
	private ProizvodTableModel modelProizvodi;
	private MojeAukcijeTableModel modelMojeAukcije;
	private UcestvovaoAukcijeTableModel modelUcestvovaoAukcije;
	
	//polja za pretrazivanje tabele proizvoda
	private JComboBox<String> comboProizvodi;
	private int indeksZaPretragu; 
	private JTextField vrednostZaPretragu;
	private JButton btnPretrazi;
	private String parametar;
	
	private byte[] avatar;
	private KorisnikTim3 korisnik;
	private ImageSetup is=new ImageSetup();
	
	private JButton btnOsvezi;
	

	public KorisnikovProzor(KorisniciTim3Remote bean) throws NamingException {
		KorisnikovProzor kp = this;
		this.korisnickoIme = bean.getKorisnickoIme();
		this.bean = bean;
		korisnik=bean.getKorisnik(korisnickoIme);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		
		JLabel labelaProizvodi = new JLabel("<html> <h2> Svi proizvodi </h2> </html> ");
		JLabel labelaAukcije = new JLabel("<html> <h2> Moje aukcije </h2> </html> ");
		JLabel labelaUcestvovao = new JLabel("<html> <h2> Aukcije u kojima sam ucestvovao </h2> </html> ");
		
		labelaProizvodi.setHorizontalAlignment(SwingConstants.CENTER);
		labelaAukcije.setHorizontalAlignment(SwingConstants.CENTER);
		labelaUcestvovao.setHorizontalAlignment(SwingConstants.CENTER);
		
		modelProizvodi = new ProizvodTableModel();
		modelMojeAukcije = new MojeAukcijeTableModel();
		modelUcestvovaoAukcije = new UcestvovaoAukcijeTableModel();
		
		//dodavanje u model
		modelProizvodi.addProizvodi(getProizvodiZaTabelu());
		modelMojeAukcije.addAukcije(getMojeAukcijeTabela());
		modelUcestvovaoAukcije.addAukcije(getUcestvovaoAukcijeTabela());
		
		//dodavanje poslednje korisnikove poslednje cene u ponudi
		List<Integer> poslednjeCene = getPoslednjeCeneAukcija(getUcestvovaoAukcijeTabela());
		modelUcestvovaoAukcije.addPoslednjeCene(poslednjeCene);
		
		tableProizvodi = new JTable(modelProizvodi);
		tableMojeAukcije = new JTable(modelMojeAukcije);
		tableUcestvovaoAukcije = new JTable(modelUcestvovaoAukcije);
		
		//scroll-ovi za tabele (da ne bi zauzimale puno mesta)
		JScrollPane scrolProizvodi = new JScrollPane(tableProizvodi);
		scrolProizvodi.setPreferredSize(new Dimension(200,200));
		JScrollPane scrolMojeAukcije = new JScrollPane(tableMojeAukcije);
		scrolMojeAukcije.setPreferredSize(new Dimension(200,200));
		JScrollPane scrolUcestvovaoAukcije = new JScrollPane(tableUcestvovaoAukcije);
		scrolUcestvovaoAukcije.setPreferredSize(new Dimension(200,200));
		
		//za tabelu tableProizvodi
		TableColumn buttonColumn = tableProizvodi.getColumnModel().getColumn(5);

		TableButton buttons = new TableButton();
        buttons.addHandler(new TableButton.TableButtonPressedHandler() {
			
			@Override
			public void onButtonPress(int row, int column) {
				AukcijaTim3 aukcija = modelProizvodi.getAukcija(row);
				PrikazProizvod pp;
				try {
					pp = new PrikazProizvod(aukcija,bean,kp, korisnik);
					pp.setVisible(true);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        buttonColumn.setCellRenderer(buttons);
        buttonColumn.setCellEditor(buttons);
        
        //za tabelu tableMojeAukcije
        TableColumn buttonColumnMojeAukcije = tableMojeAukcije.getColumnModel().getColumn(5);

		TableButton buttonsMojeAukcije = new TableButton();
        buttonsMojeAukcije.addHandler(new TableButton.TableButtonPressedHandler() {
			
			@Override
			public void onButtonPress(int row, int column) {
				AukcijaTim3 aukcija = modelMojeAukcije.getAukcija(row);
				PrikazProizvod pp;
				try {
					pp = new PrikazProizvod(aukcija,bean,kp, korisnik);
					pp.setVisible(true);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        buttonColumnMojeAukcije.setCellRenderer(buttonsMojeAukcije);
        buttonColumnMojeAukcije.setCellEditor(buttonsMojeAukcije);

		//za tabelu ucestvovao aukcije
        TableColumn buttonColumnUcestvovao = tableUcestvovaoAukcije.getColumnModel().getColumn(7);

		TableButton buttonsUcestvovao = new TableButton();
        buttonsUcestvovao.addHandler(new TableButton.TableButtonPressedHandler() {
			
			@Override
			public void onButtonPress(int row, int column) {
				AukcijaTim3 aukcija = modelUcestvovaoAukcije.getAukcija(row);
				PrikazProizvod pp;
				try {
					pp = new PrikazProizvod(aukcija,bean,kp, korisnik);
					pp.setVisible(true);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        buttonColumnUcestvovao.setCellRenderer(buttonsUcestvovao);
        buttonColumnUcestvovao.setCellEditor(buttonsUcestvovao);
        
        
		JPanel north = new JPanel(new BorderLayout());
		
		
		avatar=korisnik.getAvatar();
		JLabel canvas;
		if(avatar==null){
			canvas=new JLabel("");
		}
		else{
			BufferedImage img=is.createRGBImage(avatar);
			BufferedImage img2=is.resize(img,40);
			canvas=new JLabel(new ImageIcon(img2));
		}
		JLabel lblDobrodosli = new JLabel(korisnickoIme); 
		
		JButton dodavanje = new JButton("Dodaj aukciju");
		DodavanjeAukcije dodavanjeAukcijeFrame = new DodavanjeAukcije(bean, this);
		dodavanje.addActionListener(e->dodavanjeAukcijeFrame.setVisible(true));
		
		JPanel nortL = new JPanel(new FlowLayout());
		nortL.add(canvas);
		nortL.add(lblDobrodosli);
		nortL.add(dodavanje);
		north.add(nortL, BorderLayout.WEST);
		
		JPanel center = new JPanel(new BorderLayout());
		contentPane.add(new JScrollPane(center), BorderLayout.CENTER); 
		
		JPanel tabelaProizvodiPanel = new JPanel(new BorderLayout());
		
		JPanel pomocniPanel = new JPanel(new BorderLayout());
		pomocniPanel.add(scrolProizvodi, BorderLayout.CENTER);
		pomocniPanel.add(labelaProizvodi, BorderLayout.NORTH);
		
		tabelaProizvodiPanel.add(pomocniPanel, BorderLayout.SOUTH);
		center.add(tabelaProizvodiPanel, BorderLayout.NORTH);
		
		
		JSplitPane splitTabele=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		center.add(splitTabele, BorderLayout.SOUTH);
		
		
		//dodavanje skrolTabela u split, ali pre toga panel u koji se dodaje naslov i skrol
		JPanel panelTabela2 = new JPanel(new BorderLayout());
		JPanel panelTabela3 = new JPanel(new BorderLayout());
		
		panelTabela2.add(scrolMojeAukcije, BorderLayout.CENTER);
		panelTabela3.add(scrolUcestvovaoAukcije, BorderLayout.CENTER);
		
		panelTabela2.add(labelaAukcije, BorderLayout.NORTH);
		panelTabela3.add(labelaUcestvovao, BorderLayout.NORTH);
		
		splitTabele.setTopComponent(panelTabela2);
		splitTabele.setBottomComponent(panelTabela3);
		
		JPanel northR = new JPanel();
		JButton logout = new JButton("Log out");
		
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GlavniProzor p;
				try {
					p = new GlavniProzor();
					p.setVisible(true);
					p.setSize(640, 480);
					setVisible(false);
					bean.logout();
				} catch (NamingException e1) {
					e1.printStackTrace();
				}
				
			}
				
			});
		northR.add(logout);
		north.add(northR, BorderLayout.EAST);
		
		contentPane.add(north, BorderLayout.NORTH);
		
		
		
		
		//pretraga
		JSplitPane splitTop = new JSplitPane();
		tabelaProizvodiPanel.add(splitTop, BorderLayout.NORTH);
		
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
		tabelaProizvodiPanel.add(btnPretrazi, BorderLayout.EAST);
		
		btnOsvezi = new JButton("Osvezi");
		tabelaProizvodiPanel.add(btnOsvezi, BorderLayout.WEST);
				
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
	
	private List<AukcijaTim3> getMojeAukcijeTabela()throws NamingException {
		
		//ovo menjati ako budemo cuvali korisnicko ime u beanu
		List<AukcijaTim3> lista = bean.getMojeAukcije(korisnickoIme);
		return lista;
		
	}
	
	private List<AukcijaTim3> getUcestvovaoAukcijeTabela() throws NamingException {
		
		List<AukcijaTim3> lista = bean.getUcestvovaoAukcije(korisnickoIme);
		return lista;
		
	}
	
	private List<Integer> getPoslednjeCeneAukcija(List<AukcijaTim3> aukcije) throws NamingException {
		
		List<Integer> lista = new ArrayList<>();
		for (AukcijaTim3 aukcija : aukcije) {
			lista.add(bean.mojaPoslednjaCena(aukcija, korisnickoIme));
		}
		return lista;
				
	}
	
	public void updateTables() throws NamingException{
		List<AukcijaTim3> sve =new ArrayList<>();
		sve = getProizvodiRemote().getAll();
		modelProizvodi.addProizvodi(sve);
		
		List<AukcijaTim3> prodavac=new ArrayList<>();
		prodavac = getProizvodiRemote().getByProdavac(korisnickoIme);
		modelMojeAukcije.addAukcije(prodavac);
		
		List<AukcijaTim3> ucestvovao = new ArrayList<>();
		ucestvovao = bean.getUcestvovaoAukcije(korisnickoIme);
		modelUcestvovaoAukcije.addAukcije(ucestvovao);
		
		List<Integer> poslednjeC = getPoslednjeCeneAukcija(getUcestvovaoAukcijeTabela());
		modelUcestvovaoAukcije.addPoslednjeCene(poslednjeC);
		
	}

}
