package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	
	@NamedQuery(name = "KorisnikTim3.getKorisnik", query = "SELECT k from KorisnikTim3 k where k.kIme = :kIme and k.lozinka = :lozinka"),
	@NamedQuery(name = "KorisnikTim3.getKorisnikKIme", query = "SELECT k from KorisnikTim3 k where k.kIme = :kIme"),
			
})
public class KorisnikTim3 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String kIme;
	private String ime;
	private String prezime;
	private String email;
	private String lozinka;
	private String opis;
	@Lob
	private byte[] avatar;
	
	
	@OneToMany (mappedBy = "korisnik")
	private List<AukcijaTim3> aukcije;
	
	@OneToMany (mappedBy = "korisnikK")
	private List<KomentarTim3> komentari;
	
	@OneToMany (mappedBy = "korisnikP")
	private List<PonudaTim3> ponude;
	
	
	@Override
	public String toString() {
		return "KorisnikTim3 [kIme=" + kIme + ", ime=" + ime + ", prezime=" + prezime + ", email=" + email
				+ ", lozinka=" + lozinka + ", opis=" + opis + "]";
	}

	public String getkIme() {
		return kIme;
	}
	
	public void setkIme(String kIme) {
		this.kIme = kIme;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getPrezime() {
		return prezime;
	}
	
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLozinka() {
		return lozinka;
	}
	
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	public String getOpis() {
		return opis;
	}
	
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public byte[] getAvatar() {
		return avatar;
	}
	
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	
}
