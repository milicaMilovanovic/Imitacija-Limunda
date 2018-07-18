package beans;

import java.io.IOException;
import java.io.InputStream;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.KorisnikTim3;

/**
 * Session Bean implementation class RegistracijaBean
 */
@Stateless
@LocalBean
public class RegistracijaTim3Bean implements RegistracijaTim3Remote {
	
	@PersistenceContext(name = "NakitServerTim3")
    private EntityManager em;

 
    public RegistracijaTim3Bean() {
    }

	@Override
	public boolean registrujSe(String kIme, String ime, String prezime, String email, String lozinka, String opis,
			String pol) {
	
		KorisnikTim3 k = em.find(KorisnikTim3.class, kIme);
		if(k != null) {
			return false;
		}
		k = new KorisnikTim3();
		k.setkIme(kIme);
		k.setPrezime(prezime);
		k.setIme(ime);
		k.setEmail(email);
		k.setLozinka(lozinka); 
		k.setOpis(opis);
		ClassLoader cl = this.getClass().getClassLoader();
		if(pol.equals("M")) {
			try {
				try (InputStream slikaStream = cl.getResourceAsStream("slike/avatar.png")) {
					byte[] content = new byte[0];
					int nRead;
					byte[] buff = new byte[16384];
					while ((nRead = slikaStream.read(buff, 0, buff.length)) != -1) {
						byte[] newContent = new byte[content.length + buff.length];
						System.arraycopy(content, 0, newContent, 0, content.length);
						System.arraycopy(buff, 0, newContent, content.length, nRead);
						content = newContent;
					}
					k.setAvatar(content);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				try (InputStream slikaStream = cl.getResourceAsStream("slike/avatar3.png")) {
					byte[] content = new byte[0];
					int nRead;
					byte[] buff = new byte[16384];
					while ((nRead = slikaStream.read(buff, 0, buff.length)) != -1) {
						byte[] newContent = new byte[content.length + buff.length];
						System.arraycopy(content, 0, newContent, 0, content.length);
						System.arraycopy(buff, 0, newContent, content.length, nRead);
						content = newContent;
					}
					k.setAvatar(content);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		em.persist(k);
		return true;
	}

}
