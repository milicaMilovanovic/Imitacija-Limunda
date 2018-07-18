package beans;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.AukcijaTim3;
import entities.KorisnikTim3;


@Singleton
@LocalBean
@Startup
public class PodaciSingleton implements PodaciSingletonLocal {
	
	@PersistenceContext(name = "NakitServerTim3")
	EntityManager em;

    public PodaciSingleton() {        
    }
    
    @PostConstruct
    public void popuniBazu() {
    	
    	AukcijaTim3 a1 = dodajAukciju("Prsten", "srebrni", "srebrna", 1000, true, "srebrniprsten");
    	AukcijaTim3 a2 = dodajAukciju("Mindjuse", "opal", "plava", 5000, true, "plavemindjuse");
    	AukcijaTim3 a3 = dodajAukciju("Lancic", "zlatni", "zlatna", 7000, true, "zlatnilancic");
    	AukcijaTim3 a4 = dodajAukciju("Privezak", "cirkoni", "roze", 1500, true, "privezakroze");
    	
    	KorisnikTim3 k1 = dodajKorisnika("Pera", "Petar", "Petrovic", "pera@gmail.com", "pera", "Zlatar", "avatar");
    	KorisnikTim3 k2 = dodajKorisnika("Sanja", "Sanja", "Peric", "sanja@gmail.com", "sanja", "Preprodavac", "avatar3");
    	
    	a1.setKorisnik(k1);
    	a2.setKorisnik(k1);
    	a3.setKorisnik(k2);
    	a4.setKorisnik(k2);
    	
    	em.merge(a1);
    	em.merge(a2);
    	em.merge(a3);
    	em.merge(a4);
    	
    	
    }
    
    public KorisnikTim3 dodajKorisnika(String kIme, String ime, String prezime, String email, String lozinka, String opis, String imeSlike) {
    	KorisnikTim3 k = em.find(KorisnikTim3.class, kIme);
		if (k != null)
			return k;
    	k = new KorisnikTim3();
    	k.setkIme(kIme);
    	k.setIme(ime);
    	k.setPrezime(prezime);
    	k.setEmail(email);
    	int hash = new String(lozinka).hashCode();
		String lozinkaHash = Integer.toString(hash);
    	k.setLozinka(lozinkaHash);
    	k.setOpis(opis);
    	ClassLoader cl = this.getClass().getClassLoader();
    	String path = "slike/" + imeSlike + ".png";
    	try {
			try (InputStream slikaStream = cl.getResourceAsStream(path)) {
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
    	em.persist(k);
    	return k;
    }
    
   
    public AukcijaTim3 dodajAukciju(String tip, String materijal, String boja, int cena, boolean aktuelna, String imeSlike) {
    	
    	Query query = em.createQuery("select a from AukcijaTim3 a where a.tip=:x and a.materijal=:y"); 
		query.setParameter("x", tip);
		query.setParameter("y", materijal);
    	List<AukcijaTim3> result = query.getResultList();
		if(result.size()==0) { 
	        AukcijaTim3 a = new AukcijaTim3();
	    	a.setTip(tip);
	    	a.setMaterijal(materijal);
	    	a.setBoja(boja);
	    	a.setCena(cena);
	    	a.setAktuelna(aktuelna);
	    	
	    	ClassLoader cl = this.getClass().getClassLoader();
	    	String path = "slike/" + imeSlike + ".jpg";
	    	try {
				try (InputStream slikaStream = cl.getResourceAsStream(path)) {
					byte[] content = new byte[0];
					int nRead;
					byte[] buff = new byte[16384];
					while ((nRead = slikaStream.read(buff, 0, buff.length)) != -1) {
						byte[] newContent = new byte[content.length + buff.length];
						System.arraycopy(content, 0, newContent, 0, content.length);
						System.arraycopy(buff, 0, newContent, content.length, nRead);
						content = newContent;
					}
					a.setSlika(content);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	em.persist(a);
	    	return a;
		} else {
			AukcijaTim3 a = (AukcijaTim3) query.getSingleResult(); 
			System.out.println("Nasao sam:"+a.getId());
			return a;
		} 
    	
    }

}
