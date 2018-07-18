package prozori;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import beans.KorisniciTim3Remote;
import beans.ProizvodiTim3;
import beans.ProizvodiTim3Remote;
import entities.AukcijaTim3;
import entities.KomentarTim3;
import entities.KorisnikTim3;

public class KomentarProzor extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final String SERVER_PROJECT = "NakitServerTim3";
	private JLabel lblKomentar;
	private JButton potvrdi;
	private JButton otkazi;
	private ProizvodiTim3Remote bean;
	private KomentarTim3 nadredjeni;
	
	public KomentarProzor(PrikazProizvod prozor,KorisniciTim3Remote beanKorisnici,String dugme, AukcijaTim3 a, KorisnikTim3 k) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Komentar");
		setSize(400,300);
		getContentPane().setLayout(new BorderLayout());
		setVisible(true);
		
		lblKomentar = new JLabel("Vas komentar:");
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		north.add(lblKomentar);
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		
		potvrdi=new JButton("Potvrdi");
		otkazi = new JButton("Otkazi");
		
		JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		south.add(potvrdi);
		south.add(otkazi);
		
		JTextArea taKom = new JTextArea();
		taKom.setEditable(true);
		center.add(taKom, BorderLayout.CENTER);
		
		otkazi.addActionListener(e->dispose());
		
		
		potvrdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(dugme=="") {
					nadredjeni =null;
				}else {
					try {
						bean= doLookup();
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					nadredjeni = bean.komentarById(dugme.trim());
				}
					String komentar = taKom.getText();
					if(beanKorisnici.dodajKomentar(komentar, a, k, nadredjeni)) {
						JOptionPane.showMessageDialog(null, "Uspesno ste ostavili komentar");
						Container c = prozor.getContentPane();
						prozor.staviKomentare();
						c.invalidate();
						c.validate();
						c.repaint();
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Greska");
					}
			}
		});
		

		
		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(center, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);
		
	}
	public static ProizvodiTim3Remote doLookup() throws NamingException {
		InitialContext ctx = new InitialContext();
		String name = "ejb:/" + SERVER_PROJECT + "//"
			+ ProizvodiTim3.class.getSimpleName() + "!" + ProizvodiTim3Remote.class.getName();
		ProizvodiTim3Remote bean = (ProizvodiTim3Remote) ctx.lookup(name);
		return bean;
	}


}
