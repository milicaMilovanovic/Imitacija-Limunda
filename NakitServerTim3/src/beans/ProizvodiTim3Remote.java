package beans;

import java.util.List;

import javax.ejb.Remote;

import entities.AukcijaTim3;
import entities.KomentarTim3;
import entities.PonudaTim3;

@Remote
public interface ProizvodiTim3Remote {
	
	List<AukcijaTim3> getAll();
	
	List<AukcijaTim3> getByTip(String tip);
	
	List<AukcijaTim3> getByCena(int cena);
	
	List<AukcijaTim3> getByMaterijal(String materijal);
	
	List<AukcijaTim3> getByBoja(String boja);
	
	List<AukcijaTim3> getByProdavac(String username); 
	
	//void removeProizvod(int id);
	
	AukcijaTim3 getAukcija(int id);

	void setPrice(int id,int novaCena);
	
	void closeAukcija(int idAukcije);

	PonudaTim3 getPonuda(int idAukcije, String korisnickoIme);

	void addPonuda(int cena, AukcijaTim3 aukcija, String kIme);
	
	List<KomentarTim3> komentariAukcije(int id);
	
	KomentarTim3 komentarById(String idString);
}