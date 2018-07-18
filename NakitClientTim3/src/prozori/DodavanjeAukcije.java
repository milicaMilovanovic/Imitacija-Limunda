package prozori;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import beans.KorisniciTim3Remote;


public class DodavanjeAukcije extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField tftip;
	private JTextField tfmaterijal;
	private JTextField tfboja;
	private JTextField tfcena;
	private FileChooser fc;
	private byte[] bajtovi;
	private String tip;
	private String materijal;
	private String boja;
	private int cena;
	private byte[] slika;
	private String korisnickoIme;
	

	
	public DodavanjeAukcije(KorisniciTim3Remote k, KorisnikovProzor kp) {
		korisnickoIme = k.getKorisnickoIme();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500,300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		JPanel centar = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.HORIZONTAL;
		
		GridBagLayout gbl_centar = new GridBagLayout();
		gbl_centar.columnWidths = new int[] {this.getWidth()/4,this.getWidth()/4,this.getWidth()/4,this.getWidth()/4};
		gbl_centar.columnWeights = new double[]{5.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_centar.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		centar.setLayout(gbl_centar);
		
		contentPane.add(centar,BorderLayout.CENTER);
		
		JLabel ltip = new JLabel("Tip:");
		GridBagConstraints gbc_ltip = new GridBagConstraints();
		gbc_ltip.anchor = GridBagConstraints.WEST;
		gbc_ltip.insets = new Insets(0, 0, 5, 5);
		gbc_ltip.gridx = 1;
		gbc_ltip.gridy = 0;
		centar.add(ltip, gbc_ltip);
		
		tftip = new JTextField();
		GridBagConstraints gbc_tftip = new GridBagConstraints();
		gbc_tftip.insets = new Insets(0, 0, 5, 5);
		gbc_tftip.fill = GridBagConstraints.HORIZONTAL;
		gbc_tftip.gridwidth = 1;
		gbc_tftip.gridx = 2;
		gbc_tftip.gridy = 0;
		centar.add(tftip, gbc_tftip);
		tftip.setColumns(10);
		
		JLabel lmaterijal = new JLabel("Materijal:");
		GridBagConstraints gbc_lmaterijal = new GridBagConstraints();
		gbc_lmaterijal.anchor = GridBagConstraints.WEST;
		gbc_lmaterijal.insets = new Insets(0, 0, 5, 5);
		gbc_lmaterijal.gridx = 1;
		gbc_lmaterijal.gridy = 2;
		centar.add(lmaterijal, gbc_lmaterijal);
		
		tfmaterijal = new JTextField();
		GridBagConstraints gbc_tfmaterijal = new GridBagConstraints();
		gbc_tfmaterijal.insets = new Insets(0, 0, 5, 5);
		gbc_tfmaterijal.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfmaterijal.gridwidth = 1;
		gbc_tfmaterijal.gridx = 2;
		gbc_tfmaterijal.gridy = 2;
		centar.add(tfmaterijal, gbc_tfmaterijal);
		tfmaterijal.setColumns(10);
		
		JLabel lboja = new JLabel("Boja:");
		GridBagConstraints gbc_lboja = new GridBagConstraints();
		gbc_lboja.anchor = GridBagConstraints.WEST;
		gbc_lboja.insets = new Insets(0, 0, 5, 5);
		gbc_lboja.gridx = 1;
		gbc_lboja.gridy = 4;
		centar.add(lboja, gbc_lboja);
		
		tfboja = new JTextField();
		GridBagConstraints gbc_tfboja = new GridBagConstraints();
		gbc_tfboja.insets = new Insets(0, 0, 5, 5);
		gbc_tfboja.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfboja.gridwidth = 1;
		gbc_tfboja.gridx = 2;
		gbc_tfboja.gridy = 4;
		centar.add(tfboja, gbc_tfboja);
		tfboja.setColumns(10);
		
		JLabel lcena = new JLabel("Cena:");
		GridBagConstraints gbc_lcena = new GridBagConstraints();
		gbc_lcena.anchor = GridBagConstraints.WEST;
		gbc_lcena.insets = new Insets(0, 0, 5, 5);
		gbc_lcena.gridx = 1;
		gbc_lcena.gridy = 6;
		centar.add(lcena, gbc_lcena);
		
		tfcena = new JTextField();
		GridBagConstraints gbc_tfcena = new GridBagConstraints();
		gbc_tfcena.insets = new Insets(0, 0, 5, 5);
		gbc_tfcena.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfcena.gridwidth = 1;
		gbc_tfcena.gridx = 2;
		gbc_tfcena.gridy = 6;
		centar.add(tfcena, gbc_tfcena);
		tfcena.setColumns(10);
		gbc_lcena.anchor = GridBagConstraints.WEST;
		gbc_lcena.insets = new Insets(0, 0, 5, 5);
		gbc_lcena.gridx = 1;
		gbc_lcena.gridy = 9;
		
		JButton bslika = new JButton("Dodaj sliku");

		
		GridBagConstraints gbc_slika = new GridBagConstraints();
		gbc_slika.insets = new Insets(0, 0, 5, 5);
		gbc_slika.gridwidth = 2;
		gbc_slika.gridx = 1;
		gbc_slika.gridy = 8;
		centar.add(bslika, gbc_slika);
		
		JLabel lpath = new JLabel("");
		GridBagConstraints gbc_lpath = new GridBagConstraints();
		gbc_lpath.gridwidth = 2;
		gbc_lpath.insets = new Insets(0, 0, 0, 5);
		gbc_lpath.gridx = 1;
		gbc_lpath.gridy = 9;
		centar.add(lpath, gbc_lpath);
		bslika.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fc = new FileChooser();
				JFileChooser file=new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter=new FileNameExtensionFilter("*.Images","jpg","gif","png");
				file.addChoosableFileFilter(filter);
				int result=file.showSaveDialog(null);
				if(result==JFileChooser.APPROVE_OPTION){
					//treba proveriti ekstenziju
					
					File selectedFile=file.getSelectedFile();
					lpath.setText(selectedFile.getAbsolutePath());
					String extension = fc.getExtension(selectedFile);
				    if(fc.isImage(extension)){
					try {
						bajtovi=fc.prebaciUBajtove(selectedFile); //ovu promenljvu hoces da setujes i onda posle kazes byte[] slika=bajtovi;
						System.out.println(bajtovi);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null,"Greska pri uploadovanju slike, molimo Vas pokusajte opet");
						e1.printStackTrace();
					}
				   }
				    else{
				    	JOptionPane.showMessageDialog(null,"Odaberite fajl koji je slika");
				    }
				}

				else if(result==JFileChooser.CANCEL_OPTION){
					JOptionPane.showMessageDialog(null,"No file selected");
					
				}
			}});
		
		JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton dodaj = new JButton("Dodaj");
		dodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tip = tftip.getText();
				materijal = tfmaterijal.getText();
				boja = tfboja.getText();
				String cenas = tfcena.getText();
				slika = bajtovi;
	
				
					if(!tip.equals("") && !materijal.equals("") && !boja.equals("") && !cenas.equals("") && slika!=null) {
						try {
							System.out.println(slika+"  to je slika");
							cena = Integer.parseInt(cenas);
							if(k.dodajAukciju(tip, materijal, boja, cena, slika, korisnickoIme)) {
								JOptionPane.showMessageDialog(null, "Uspesno ste dodali aukciju");
								dispose();
							}else {
								JOptionPane.showMessageDialog(null, "Niste ulogovani, ne mozete dodati aukciju");
							}
						
						}catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "Morate uneti broj za cenu");
						}

					}else {
						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja");
					}
					SwingUtilities.updateComponentTreeUI(kp);
					try {
						kp.updateTables();
					} catch (NamingException e1) {
						e1.printStackTrace();
					}
				
			}
		});
		
		JButton odustani = new JButton("Odustani");
		odustani.addActionListener(e->dispose());

		south.add(dodaj);
		south.add(odustani);
		contentPane.add(south, BorderLayout.SOUTH);
	}
	
    /*private KorisniciTim3Remote getKorisniciRemote() throws NamingException {
		
		if (bean == null)
		{
			InitialContext ctx = new InitialContext();
			String name = "ejb:/" + SERVER_PROJECT + "//" + KorisniciTim3.class.getSimpleName()
					+ "!" + KorisniciTim3Remote.class.getName() + "?stateful";
			bean = (KorisniciTim3Remote) ctx.lookup(name);
		}
		return bean;
		
	}*/
}
