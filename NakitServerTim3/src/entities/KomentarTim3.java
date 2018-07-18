package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class KomentarTim3 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int idKom;
	
	@ManyToOne          
	private KorisnikTim3 korisnikK;
	
    @ManyToOne
	private AukcijaTim3 aukcijaK;
    
    @ManyToOne
    private KomentarTim3 nadredjeni;
    
	private String komentar;

	
	@Override
	public String toString() {
		return "KomentarTim3 [idKom=" + idKom + ", korisnikK=" + korisnikK + ", aukcijaK=" + aukcijaK + ", komentar="
				+ komentar + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((komentar == null) ? 0 : komentar.hashCode());
		result = prime * result + idKom;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KomentarTim3 other = (KomentarTim3) obj;
		if (komentar == null) {
			if (other.komentar != null)
				return false;
		} else if (!komentar.equals(other.komentar))
			return false;
		if (idKom != other.idKom) {
			return false;
		}
		return true;
	}


	public int getIdKom() {
		return idKom;
	}


	public void setIdKom(int idKom) {
		this.idKom = idKom;
	}


	public KorisnikTim3 getKorisnikK() {
		return korisnikK;
	}


	public void setKorisnikK(KorisnikTim3 korisnikK) {
		this.korisnikK = korisnikK;
	}


	public AukcijaTim3 getAukcijaK() {
		return aukcijaK;
	}


	public void setAukcijaK(AukcijaTim3 aukcijaK) {
		this.aukcijaK = aukcijaK;
	}


	public String getKomentar() {
		return komentar;
	}


	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}
	
	public KomentarTim3 getNadredjeni() {
		return nadredjeni;
	}
	
	public void setNadredjeni(KomentarTim3 nadredjeni) {
		this.nadredjeni=nadredjeni;
	}
	
}
