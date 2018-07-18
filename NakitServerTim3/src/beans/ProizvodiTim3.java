package beans;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.AukcijaTim3;
import entities.KomentarTim3;
import entities.KorisnikTim3;
import entities.PonudaTim3;

@Stateless
@Remote(ProizvodiTim3Remote.class)
public class ProizvodiTim3 implements ProizvodiTim3Remote {

	@PersistenceContext(name = "NakitServerTim3")
    private EntityManager em;

	@Override
	public List<AukcijaTim3> getAll() {
		TypedQuery<AukcijaTim3> q = em.createNamedQuery("AukcijaTim3.getAllProizvodi", AukcijaTim3.class);
		q.setParameter("aktuelna", true);
		return q.getResultList();
	}

	@Override
	public List<AukcijaTim3> getByTip(String tip) {
		TypedQuery<AukcijaTim3> q = em.createNamedQuery("AukcijaTim3.getByTip", AukcijaTim3.class);
		q.setParameter("tip", tip);
		return q.getResultList();
	}

	@Override
	public List<AukcijaTim3> getByCena(int cena) {
		TypedQuery<AukcijaTim3> q = em.createNamedQuery("AukcijaTim3.getByCena", AukcijaTim3.class);
		q.setParameter("cena", cena);
		return q.getResultList();
	}

	@Override
	public List<AukcijaTim3> getByMaterijal(String materijal) {
		TypedQuery<AukcijaTim3> q = em.createNamedQuery("AukcijaTim3.getByMaterijal", AukcijaTim3.class);
		q.setParameter("materijal", materijal);
		return q.getResultList();
	}

	@Override
	public List<AukcijaTim3> getByBoja(String boja) {
		TypedQuery<AukcijaTim3> q = em.createNamedQuery("AukcijaTim3.getByBoja", AukcijaTim3.class);
		q.setParameter("boja", boja);
		return q.getResultList();
	}

	@Override
	public List<AukcijaTim3> getByProdavac(String username) {
		TypedQuery<AukcijaTim3> q = em.createNamedQuery("AukcijaTim3.getByProdavac", AukcijaTim3.class);
		q.setParameter("prodavac", username);
		return q.getResultList();
	}
	

	
	@Override
	public AukcijaTim3 getAukcija(int id) {
		TypedQuery<AukcijaTim3> q = em.createNamedQuery("AukcijaTim3.getById", AukcijaTim3.class);
		q.setParameter("id", id);
		return q.getSingleResult();
	}

	@Override
	public void setPrice(int id,int novaCena) {
		Query q=em.createQuery("UPDATE AukcijaTim3 SET cena=:novaCena WHERE id=:id");
		q.setParameter("id", id);
		q.setParameter("novaCena", novaCena);
		q.executeUpdate();
		
	}

	@Override
	public void closeAukcija(int id) {
		Query q=em.createQuery("UPDATE AukcijaTim3 SET aktuelna=false WHERE id=:id");
		q.setParameter("id", id);
		q.executeUpdate();
	}

	@Override
	public PonudaTim3 getPonuda(int idAukcije, String korisnickoIme) {
		try {
			Query q=em.createQuery("SELECT p FROM PonudaTim3 p WHERE p.korisnikP.kIme= :kIme AND p.aukcijaP.id= :id");
			q.setParameter("kIme", korisnickoIme);
			q.setParameter("id", idAukcije);
			if (!q.getResultList().isEmpty())
				return (PonudaTim3) q.getResultList().get(0);
			else
				return null;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void addPonuda(int cena,AukcijaTim3 aukcija, String kIme) {
		try{
			PonudaTim3  p=getPonuda(aukcija.getId(),kIme);
			if(p==null){
				KorisnikTim3 korisnik=em.find(KorisnikTim3.class, kIme);
				p=new PonudaTim3();
		    	p.setAukcijaP(aukcija);
		    	p.setKorisnikP(korisnik);
		    	p.setCena(cena);
		    	em.persist(p);
			}else{
				Query q=em.createQuery("UPDATE PonudaTim3 SET cena= :cena WHERE id=:id");
				q.setParameter("cena", cena);
				q.setParameter("id", p.getIdPon());
				q.executeUpdate();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	@Override
	public List<KomentarTim3> komentariAukcije(int id) {
		Query q = em.createQuery("SELECT k FROM KomentarTim3 k WHERE k.aukcijaK.id=:id");
		q.setParameter("id", id);
		List<KomentarTim3> komentari = q.getResultList();
		return komentari;
	}

	@Override
	public KomentarTim3 komentarById(String idString) {
		if(idString.equals("")) {
			return null;
		}else {
			int id = Integer.parseInt(idString);
			Query q = em.createQuery("SELECT k FROM KomentarTim3 k WHERE k.idKom=:id");
			q.setParameter("id", id);
			KomentarTim3 kom = (KomentarTim3) q.getSingleResult();
			return kom;
		}
	}
	
}
