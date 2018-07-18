package prozori;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import beans.KorisniciTim3Remote;
import beans.ProizvodiTim3;
import beans.ProizvodiTim3Remote;
import entities.AukcijaTim3;
import entities.KomentarTim3;
import entities.KorisnikTim3;
import entities.PonudaTim3;

public class PrikazProizvod extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextArea txtCena;
	private static final String SERVER_PROJECT = "NakitServerTim3";
	private AukcijaTim3 a;
	private KorisnikTim3 k;
	private int idAukcije;
	private String tipAukcije;
	private String materijalAukcije;
	private String bojaAukcije;
	private int cenaAukcije;
	private boolean aktuelnaAukcija;
	private byte[] slika;
	private ProizvodiTim3Remote beanProizvod;
	private KorisniciTim3Remote beanKorisnika;
	private int novaCena;
	private int korisnikovaCena;
	private String korisnickoIme;
	private PonudaTim3 p;
	private String korisnik;
	private ImageSetup is=new ImageSetup();
	private List<KomentarTim3> komentari;
	private JPanel prikazKomentara;
	private JScrollPane spkom;
	private PrikazProizvod prozor;

	public PrikazProizvod(AukcijaTim3 a, KorisniciTim3Remote beanKorisnika1, KorisnikovProzor kp, KorisnikTim3 k) throws NamingException {
		this.a=a;
		this.k=k;
		this.beanKorisnika=beanKorisnika1;
		beanProizvod=getProizvodiRemote();
		idAukcije=a.getId();
		tipAukcije=a.getTip();
		materijalAukcije=a.getMaterijal();
		bojaAukcije=a.getBoja();
		cenaAukcije=a.getCena();
		slika=a.getSlika();
		aktuelnaAukcija=a.isAktuelna();
		korisnik=a.getKorisnik().getkIme();
		
				
		korisnickoIme=beanKorisnika.getKorisnickoIme();
		
		try {
			p=beanProizvod.getPonuda(idAukcije,korisnickoIme);
			if (p != null) {
				korisnikovaCena=p.getCena();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		JLabel canvas;
		if (slika==null) {
			canvas=new JLabel("Ne postoji slika za ovu aukciju!");
		}
		else {
			BufferedImage img=is.createRGBImage(slika);
			BufferedImage img2=is.resize(img,400);
			canvas=new JLabel(new ImageIcon(img2));
		}

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(0, 0, 800, 700);
		setTitle("Prikaz aukcije");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {0, 159, 62, 50, 0, 0, 4};
		gbl_contentPane.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 4.9E-324};
		gbl_contentPane.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_lblSlika = new GridBagConstraints();
		gbc_lblSlika.gridwidth = 4;
		gbc_lblSlika.insets = new Insets(0, 0, 5, 5);
		gbc_lblSlika.gridx = 1;
		gbc_lblSlika.gridy = 0;
		contentPane.add(canvas, gbc_lblSlika);
		
		JLabel lblTip = new JLabel("Tip: " + tipAukcije);
		GridBagConstraints gbc_lblTip = new GridBagConstraints();
		gbc_lblTip.anchor = GridBagConstraints.WEST;
		gbc_lblTip.insets = new Insets(0, 0, 5, 5);
		gbc_lblTip.gridx = 1;
		gbc_lblTip.gridy = 1;
		contentPane.add(lblTip, gbc_lblTip);
		
		JLabel lblMaterijal = new JLabel("Materijal: " + materijalAukcije);
		GridBagConstraints gbc_lblMaterijal = new GridBagConstraints();
		gbc_lblMaterijal.anchor = GridBagConstraints.WEST;
		gbc_lblMaterijal.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaterijal.gridx = 1;
		gbc_lblMaterijal.gridy = 2;
		contentPane.add(lblMaterijal, gbc_lblMaterijal);
		
		JLabel lblBoja = new JLabel("Boja: " + bojaAukcije);
		GridBagConstraints gbc_lblBoja = new GridBagConstraints();
		gbc_lblBoja.anchor = GridBagConstraints.WEST;
		gbc_lblBoja.insets = new Insets(0, 0, 5, 5);
		gbc_lblBoja.gridx = 1;
		gbc_lblBoja.gridy = 3;
		contentPane.add(lblBoja, gbc_lblBoja);
		
		JLabel lblCena = new JLabel("Trenutna cena: " + refreshCena(a.getId()) + ",00 RSD");
		GridBagConstraints gbc_lblCena = new GridBagConstraints();
		gbc_lblCena.anchor = GridBagConstraints.WEST;
		gbc_lblCena.insets = new Insets(0, 0, 5, 5);
		gbc_lblCena.gridx = 1;
		gbc_lblCena.gridy = 4;
		contentPane.add(lblCena, gbc_lblCena);
		prozor =this;
		if (!korisnickoIme.equals(korisnik)) {
			JLabel lblPonuda;
			if (p != null) {
				lblPonuda = new JLabel("Moja poslednja ponuda: " + korisnikovaCena + ",00 RSD");
			}
			else {
				lblPonuda = new JLabel("Moja poslednja ponuda: 0,00 RSD");
			}
			GridBagConstraints gbc_lblPonuda = new GridBagConstraints();
			gbc_lblPonuda.insets = new Insets(0, 0, 5, 5);
			gbc_lblPonuda.anchor = GridBagConstraints.WEST;
			gbc_lblPonuda.gridx = 1;
			gbc_lblPonuda.gridy = 5;
			contentPane.add(lblPonuda, gbc_lblPonuda);
			
			if (aktuelnaAukcija) {
				JLabel lblNovaCena = new JLabel("Unesite novu cenu:");
				GridBagConstraints gbc_lblNovaCena = new GridBagConstraints();
				gbc_lblNovaCena.insets = new Insets(0, 0, 5, 5);
				gbc_lblNovaCena.anchor = GridBagConstraints.WEST;
				gbc_lblNovaCena.gridx = 1;
				gbc_lblNovaCena.gridy = 6;
				contentPane.add(lblNovaCena, gbc_lblNovaCena);
				
				txtCena = new JTextArea();
				GridBagConstraints gbc_txtCena = new GridBagConstraints();
				gbc_txtCena.anchor = GridBagConstraints.WEST;
				gbc_txtCena.insets = new Insets(0, 0, 5, 5);
				gbc_txtCena.gridx = 2;
				gbc_txtCena.gridy = 6;
				txtCena.setMinimumSize(new Dimension(80,30));
				contentPane.add(txtCena, gbc_txtCena);
				
				
				JButton btnUnesi = new JButton("Unesi cenu");
				GridBagConstraints gbc_btnUnesi = new GridBagConstraints();
				gbc_btnUnesi.anchor = GridBagConstraints.WEST;
				gbc_btnUnesi.gridx = 3;
				gbc_btnUnesi.gridy = 6;
				btnUnesi.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							novaCena=Integer.parseInt(txtCena.getText());
							if (novaCena>cenaAukcije) {
								beanProizvod.setPrice(idAukcije,novaCena);
								String nc=Integer.toString(novaCena);
								beanProizvod.addPonuda(novaCena,a,korisnickoIme);
								lblCena.setText("Trenutna cena: " + nc + ",00 RSD");
								lblPonuda.setText("Moja poslednja ponuda: "+nc+",00 RSD");
								txtCena.setText("");
								JOptionPane.showMessageDialog(null, "Uspesno uneta cena!");
							}
							else {
								JOptionPane.showMessageDialog(null, "Nova cena mora biti veca od stare!");
							}
						} catch(NumberFormatException e){
							JOptionPane.showMessageDialog(null, "Niste dobro uneli cenu!");
						}
						try {
							kp.updateTables();
						} catch (NamingException e) {
							e.printStackTrace();
						}
					}
				});
				contentPane.add(btnUnesi, gbc_btnUnesi);
			}
			else {
				JLabel lblNovaCena = new JLabel("Aukcija je zatvorena");
				GridBagConstraints gbc_lblNovaCena = new GridBagConstraints();
				gbc_lblNovaCena.insets = new Insets(0, 0, 5, 5);
				gbc_lblNovaCena.anchor = GridBagConstraints.WEST;
				gbc_lblNovaCena.gridx = 1;
				gbc_lblNovaCena.gridy = 6;
				contentPane.add(lblNovaCena, gbc_lblNovaCena);
			}
		}
		else {
			if (!aktuelnaAukcija) {
				JLabel lblNovaCena = new JLabel("Aukcija je zatvorena");
				GridBagConstraints gbc_lblNovaCena = new GridBagConstraints();
				gbc_lblNovaCena.insets = new Insets(0, 0, 5, 5);
				gbc_lblNovaCena.anchor = GridBagConstraints.WEST;
				gbc_lblNovaCena.gridx = 1;
				gbc_lblNovaCena.gridy = 6;
				contentPane.add(lblNovaCena, gbc_lblNovaCena);
			}
			else {
				JButton btnZatvori = new JButton("Zatvori aukciju");
				GridBagConstraints gbc_btnZatvori = new GridBagConstraints();
				gbc_btnZatvori.anchor = GridBagConstraints.WEST;
				gbc_btnZatvori.gridx = 1;
				gbc_btnZatvori.gridy = 6;
				btnZatvori.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						beanProizvod.closeAukcija(idAukcije);
						dispose();
						JOptionPane.showMessageDialog(null, "Uspesno zatvorena aukcija!");
						try {
							kp.updateTables();
						} catch (NamingException e) {
							e.printStackTrace();
						}
					}
				});
				contentPane.add(btnZatvori, gbc_btnZatvori);
			}
		}
		JPanel pkomentari = new JPanel();
		GridBagConstraints gbc_pkomentari = new GridBagConstraints();
		gbc_pkomentari.gridwidth = 6;
		gbc_pkomentari.insets = new Insets(0, 0, 0, 5);
		gbc_pkomentari.fill = GridBagConstraints.BOTH;
		gbc_pkomentari.gridx = 0;
		gbc_pkomentari.gridy = 7;
		contentPane.add(pkomentari, gbc_pkomentari);
		pkomentari.setLayout(new BorderLayout(0, 0));
		
		spkom = new JScrollPane();
		pkomentari.add(spkom, BorderLayout.CENTER);
		
		staviKomentare();
		
		JPanel pnorth = new JPanel(new BorderLayout());
		pkomentari.add(pnorth, BorderLayout.NORTH);
		
		JLabel lblKomentari = new JLabel("Komentari:");
		pnorth.add(lblKomentari, BorderLayout.WEST);
		
		JPanel northR= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnKomentarisi = new JButton("Komentarisi");
		JButton btnOsvezi = new JButton("Osvezi");
			
		northR.add(btnOsvezi);
		northR.add(btnKomentarisi);
		pnorth.add(northR, BorderLayout.EAST);
		btnKomentarisi.setActionCommand("");
				
		btnKomentarisi.addActionListener(e->{
			KomentarProzor komp = new KomentarProzor(prozor,beanKorisnika,btnKomentarisi.getActionCommand(), a, k);
		});
			
			
		btnOsvezi.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				komentari.clear();
				staviKomentare();
				try {
					lblCena.setText("Trenutna cena: " + refreshCena(a.getId()) + ",00 RSD");
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		if(!aktuelnaAukcija) {
			btnKomentarisi.setEnabled(false);
		}
		
	}

	private static ProizvodiTim3Remote getProizvodiRemote() throws NamingException {
		InitialContext ctx = new InitialContext();
		String name = "ejb:/" + SERVER_PROJECT + "//"
			+ ProizvodiTim3.class.getSimpleName() + "!" + ProizvodiTim3Remote.class.getName();
		ProizvodiTim3Remote bean = (ProizvodiTim3Remote) ctx.lookup(name);
		return bean;
		
	}
	public int refreshCena(int id) throws NamingException{
		AukcijaTim3 a = getProizvodiRemote().getAukcija(id);
		return a.getCena();
	}

	public void staviKomentare() {
		prikazKomentara = new JPanel();
		prikazKomentara.setLayout(new BoxLayout(prikazKomentara, BoxLayout.Y_AXIS));
		spkom.setViewportView(prikazKomentara);
		komentari = beanProizvod.komentariAukcije(a.getId());
		for(KomentarTim3 kom: komentari) {
			JTextArea takom;
			if (kom.getNadredjeni()!=null) {
				takom = new JTextArea("Odgovor korisniku: " + kom.getNadredjeni().getKorisnikK().getkIme() + "\nNa komentar: " + kom.getNadredjeni().getKomentar() + "\nKorisnik:" + kom.getKorisnikK().getkIme() + "\nKomentar:" + kom.getKomentar());
			}
			else {
				takom = new JTextArea("Korisnik: " + kom.getKorisnikK().getkIme() + "\nKomentar: " + kom.getKomentar());
			}
			JButton btnKomentar = new JButton("Komentarisi");
			btnKomentar.setActionCommand(kom.getIdKom()+"");
			prikazKomentara.add(takom);
			prikazKomentara.add(btnKomentar);
			if(!aktuelnaAukcija) {
				btnKomentar.setEnabled(false);
			}
			btnKomentar.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					KomentarProzor komp = new KomentarProzor(prozor,beanKorisnika,btnKomentar.getActionCommand(), a, k);				
				}});
			
		}
	}

} 
