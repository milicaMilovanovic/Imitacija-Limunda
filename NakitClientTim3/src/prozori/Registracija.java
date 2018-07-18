package prozori;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import beans.RegistracijaTim3Bean;
import beans.RegistracijaTim3Remote;

public class Registracija extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static final String SERVER_PROJECT = "NakitServerTim3";
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textKorisnickoIme;
	private JTextField textIme;
	private JTextField textPrezime;
	private JTextField textMail;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirm;
	
	
	public static RegistracijaTim3Remote doLookup() throws NamingException {
		InitialContext ctx = new InitialContext();
		String name = "ejb:/" + SERVER_PROJECT + "//"
			+ RegistracijaTim3Bean.class.getSimpleName() + "!" + RegistracijaTim3Remote.class.getName();
		RegistracijaTim3Remote bean = (RegistracijaTim3Remote) ctx.lookup(name);
		return bean;
	}

	
	public Registracija() {
		setBounds(100, 100, 450, 300);
		setSize(450, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		
		JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
		
		JLabel lblIme = new JLabel("Ime:");
		
		JLabel lblPrezime = new JLabel("Prezime:");
		
		JLabel lblEmail = new JLabel("E-mail:");
		
		JLabel lblLozinka = new JLabel("Lozinka:");
		
		JLabel lblPotvrditeLozinku = new JLabel("Potvrdite lozinku:");
		
		JLabel lblPol = new JLabel("Pol:");
		
		JLabel lblOpis = new JLabel("Opis:");
		
		textKorisnickoIme = new JTextField();
		textKorisnickoIme.setColumns(10);
		
		textIme = new JTextField();
		textIme.setColumns(10);
		
		textPrezime = new JTextField();
		textPrezime.setColumns(10);
		
		textMail = new JTextField();
		textMail.setColumns(10);
		
		passwordField = new JPasswordField();
		
		passwordFieldConfirm = new JPasswordField();
		
		JRadioButton rdbtnM = new JRadioButton("M");
		
		JRadioButton rdbtnZ = new JRadioButton("Z");
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnZ);
		btnGroup.add(rdbtnM);
		
		JTextArea textArea = new JTextArea();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(lblLozinka)
							.addComponent(lblKorisnickoIme, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
							.addComponent(lblPrezime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblIme, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
							.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblPotvrditeLozinku, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(lblOpis, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblPol, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGap(25)
								.addComponent(rdbtnM)
								.addGap(32)
								.addComponent(rdbtnZ))
							.addComponent(passwordField, 166, 166, 166)
							.addComponent(textMail, 166, 166, 166)
							.addComponent(textPrezime, 166, 166, 166)
							.addComponent(textIme, 166, 166, 166)
							.addComponent(textKorisnickoIme, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
							.addComponent(passwordFieldConfirm, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addGap(92))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKorisnickoIme)
						.addComponent(textKorisnickoIme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblIme)
						.addComponent(textIme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrezime)
						.addComponent(textPrezime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(textMail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLozinka)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPotvrditeLozinku)
						.addComponent(passwordFieldConfirm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPol)
						.addComponent(rdbtnM)
						.addComponent(rdbtnZ))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblOpis)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registruj se");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					
	
					@Override
					public void actionPerformed(ActionEvent e) {
						
						String kIme = textKorisnickoIme.getText();
						if(kIme.equals("")) {
							JOptionPane.showMessageDialog(null, "Unesite korisnicko ime");
							return;
						}
						String ime = textIme.getText();
						if(ime.equals("")) {
							JOptionPane.showMessageDialog(null, "Unesite ime");
							return;
						}
						String prezime = textPrezime.getText();
						if(prezime.equals("")) {
							JOptionPane.showMessageDialog(null, "Unesite prezime");
							return;
						}
						String mail = textMail.getText();
						if(mail.equals("")) {
							JOptionPane.showMessageDialog(null, "Unesite e-mail adresu");
							return;
						}
						char[] lozinka = passwordField.getPassword(); 
						int hash = new String(lozinka).hashCode();
						String lozinkaHash = Integer.toString(hash);
						if(lozinka.length==0) {
							JOptionPane.showMessageDialog(null, "Unesite lozinku");
							return;
						}
						char[] lozinka2 = passwordFieldConfirm.getPassword(); 
						if(lozinka2.length==0) {
							JOptionPane.showMessageDialog(null, "Ponovite lozinku");
							return;
						}
					
						boolean iste = proveriLozinke(lozinka, lozinka2);
						if(!iste) {
							JOptionPane.showMessageDialog(null, "Lozinke se ne poklapaju");
							return;
						}
						
						String pol = getSelectedButtonText(btnGroup);
						if(pol == null) {
							JOptionPane.showMessageDialog(null, "Odaberite pol");
							return;
						}
						
						String opis = textArea.getText();
						if(opis.equals("")) {
							JOptionPane.showMessageDialog(null, "Napisite kratak opis"); 
							return;
						}
						
						try {
							RegistracijaTim3Remote bean = doLookup();
							if(bean.registrujSe(kIme, ime, prezime, mail, lozinkaHash, opis, pol)) {
								JOptionPane.showMessageDialog(null, "Uspesno ste se registrovali"); 
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "Korisnicko ime je zauzeto, pokusajte drugo"); 
								return;
							}
						} catch (NamingException e1) {
							e1.printStackTrace();
						}
						
					}

					private boolean proveriLozinke(char[] lozinka, char[] lozinka2) {
						if(lozinka.length != lozinka2.length) {
							return false;
						}
						for(int i = 0; i < lozinka.length; i++) {
							if(lozinka[i] != lozinka2[i]) { 
								return false;
							}
						}
						return true;
					}
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
			{
				JButton cancelButton = new JButton("Odustani");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(e -> dispose());
				buttonPane.add(cancelButton);
			}
		}
	}
	public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
}
