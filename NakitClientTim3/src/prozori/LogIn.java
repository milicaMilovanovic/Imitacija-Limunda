package prozori;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import beans.KorisniciTim3;
import beans.KorisniciTim3Remote;


public class LogIn extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField korisnickoImeTxt;
	private JPasswordField lozinkaPass;
	
	KorisniciTim3Remote bean;
	
	private boolean ulogovan = false;
	
	private static final String SERVER_PROJECT = "NakitServerTim3";
	
	public static KorisniciTim3Remote doLookup() throws NamingException {
		InitialContext ctx = new InitialContext();
		String name = "ejb:/" + SERVER_PROJECT + "//"
			+ KorisniciTim3.class.getSimpleName() + "!" + KorisniciTim3Remote.class.getName()  + "?stateful";
		KorisniciTim3Remote bean = (KorisniciTim3Remote) ctx.lookup(name);
		return bean;
	}
	
	public boolean getUlogovan() {
		return this.ulogovan;
	}
	
	public void setUlogovan(boolean ulogovan) {
		this.ulogovan = ulogovan;
	}

		
	public LogIn(GlavniProzor p) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		
		JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
		
		JLabel lblLozinka = new JLabel("Lozinka:");
		
		korisnickoImeTxt = new JTextField();
		korisnickoImeTxt.setColumns(10);
		
		lozinkaPass = new JPasswordField();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblKorisnickoIme, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLozinka, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lozinkaPass)
						.addComponent(korisnickoImeTxt, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
					.addContainerGap(155, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(korisnickoImeTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKorisnickoIme))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLozinka)
						.addComponent(lozinkaPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(132, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Uloguj se");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String korisnickoIme = korisnickoImeTxt.getText();
						if(korisnickoIme.equals("")) {
							JOptionPane.showMessageDialog(null, "Unesite korisnicko ime");
							return;
						}
						char[] lozinka = lozinkaPass.getPassword(); 
						int hash = new String(lozinka).hashCode();
						String lozinkaHash = Integer.toString(hash);
						if(lozinka.length==0) {
							JOptionPane.showMessageDialog(null, "Unesite lozinku");
							return;
						}
						
						try {
							bean = doLookup();
							boolean uspesnoLogovanje = bean.logIn(korisnickoIme, lozinkaHash); 
							if(!uspesnoLogovanje) {
								JOptionPane.showMessageDialog(null, "Pogresno korisnicko ime i lozinka");
							} else {
								dispose();
								KorisnikovProzor kp = new KorisnikovProzor(bean); 
								kp.setVisible(true);
								p.dispose();
								setUlogovan(true);
							}
						} catch (NamingException e1) {
							e1.printStackTrace();
						}
						
					}
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Odustani");
				cancelButton.addActionListener(e -> dispose());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
