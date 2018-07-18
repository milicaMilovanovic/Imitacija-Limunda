package beans;


import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.AukcijaTim3;
import entities.KomentarTim3;
import entities.KorisnikTim3;
import entities.PonudaTim3;

@Stateful
@Remote(KorisniciTim3Remote.class)
public class KorisniciTim3 implements KorisniciTim3Remote {

	private String korisnickoIme;
	
	
	private boolean ok; 
		
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	@Override
	public boolean dodajAukciju(String tip, String materijal, String boja, int cena, byte[] slika, String korisnickoIme) {
		System.out.println("ok unutar dodajAukciju " + isOk());
		if(isOk()) { 
			KorisnikTim3 korisnik = em.find(KorisnikTim3.class, korisnickoIme);
			AukcijaTim3 a = new AukcijaTim3();
			String prvoSlovo = tip.substring(0,1).toUpperCase();
			String tip1 = tip.toLowerCase().substring(1, tip.length());
			tip = prvoSlovo + tip1;
	    	a.setTip(tip);
	    	a.setMaterijal(materijal.toLowerCase());
	    	a.setBoja(boja.toLowerCase());
	    	a.setCena(cena);
	    	a.setSlika(slika);
	    	a.setKorisnik(korisnik);
	    	a.setAktuelna(true);
	    	em.persist(a);
	    	return true;
		}else
			return false;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	@PersistenceContext(name = "NakitServerTim3")
	private EntityManager em;
	

	@Override
	public boolean logIn(String kIme, String lozinkaHash) {
		KorisnikTim3 korisnik = em.find(KorisnikTim3.class, kIme);
		if(korisnik == null) {
			return false;
		}
		String lozinkaBaza = korisnik.getLozinka();

		if(!lozinkaBaza.equals(lozinkaHash)) {
			return false;
		}
		setOk(true);
		korisnickoIme=kIme;
		System.out.println("Ok je postavljen na : " + isOk());
		return true;
	}


	@Override
	public PonudaTim3 getPonudaKorisnika(int idAukcije, String korisnickoIme) {
		TypedQuery<PonudaTim3> q = em.createNamedQuery("PonudaTim3.getPonudaKorisnika", PonudaTim3.class);
		q.setParameter("id", idAukcije);
		q.setParameter("korisnickoIme", korisnickoIme);
		return q.getSingleResult();
	}
	
	@Override
	public List<AukcijaTim3> getMojeAukcije(String username) {
		TypedQuery<AukcijaTim3> q = em.createNamedQuery("AukcijaTim3.getByProdavac", AukcijaTim3.class);
		q.setParameter("prodavac", username);
		return q.getResultList();
	}

	@Override
	public List<AukcijaTim3> getUcestvovaoAukcije(String username) {
		Query q = em.createQuery("SELECT DISTINCT a FROM AukcijaTim3 a JOIN FETCH a.ponude p "
				+ "WHERE p.korisnikP.kIme = :username");
		q.setParameter("username", username);
		return q.getResultList();
	}
	
	
	@Override
	public int mojaPoslednjaCena(AukcijaTim3 aukcija, String username) {
		Query q = em.createQuery("SELECT p FROM PonudaTim3 p WHERE p.aukcijaP.id = :idA AND "
				+ "korisnikP.kIme = :username");
		q.setParameter("idA", aukcija.getId());
		q.setParameter("username", username);
		List<PonudaTim3> ponude = q.getResultList();
		int maxPonudaKorisnika=0;
		for (PonudaTim3 p : ponude) {
			if (p.getCena() > maxPonudaKorisnika) {
				maxPonudaKorisnika=p.getCena();
			}
		}
		return maxPonudaKorisnika;
	}
	
	@Override
	@Remove
	public void logout() {
		ok = false;
		korisnickoIme="";
	}

	@Override
	public KorisnikTim3 getKorisnik(String korisnickoIme) {
		TypedQuery<KorisnikTim3> q = em.createNamedQuery("KorisnikTim3.getKorisnikKIme", KorisnikTim3.class);
		q.setParameter("kIme", korisnickoIme);
		return q.getSingleResult();
	}

	@Override
	public boolean dodajKomentar(String komentar, AukcijaTim3 a, KorisnikTim3 korisnik, KomentarTim3 nadredjeni) {
		KomentarTim3 k = new KomentarTim3();
		k.setAukcijaK(a);
		k.setKomentar(komentar);
		k.setKorisnikK(korisnik);
		k.setNadredjeni(nadredjeni);
		em.persist(k);
		return true;
	}
	
}
