package beans;

import java.util.List;

import javax.ejb.Remote;

import entities.AukcijaTim3;
import entities.KomentarTim3;
import entities.KorisnikTim3;
import entities.PonudaTim3;

@Remote
public interface KorisniciTim3Remote {
	
	String getKorisnickoIme();
		
    //boolean logIn(String kIme, char[] lozinka);
	boolean logIn(String kIme, String lozinkaHash);
    
    boolean dodajAukciju(String tip, String materijal, String boja, int cena, byte[] slika, String korisnickoIme);

	PonudaTim3 getPonudaKorisnika(int idAukcije, String korisnickoIme);
	
	List<AukcijaTim3> getMojeAukcije(String username);
	
	List<AukcijaTim3> getUcestvovaoAukcije(String username);
	
    int mojaPoslednjaCena(AukcijaTim3 aukcija, String username);
    
    void logout();

	KorisnikTim3 getKorisnik(String korisnickoIme);
	
	boolean dodajKomentar(String komentar, AukcijaTim3 a, KorisnikTim3 korisnik, KomentarTim3 nadredjeni);
	
	/*List<PonudaTim3> getUcestvovaoPonude(String username);
	
	List<AukcijaTim3> getAukcijeByPonuda(List<PonudaTim3> ponude);*/
}
