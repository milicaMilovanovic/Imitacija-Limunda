package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
public class PonudaTim3 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int idPon;
	
	private int cena;  //ponudjena vrednost 
	
	@ManyToOne
	private KorisnikTim3 korisnikP;
	@ManyToOne
	private AukcijaTim3 aukcijaP;
	
	@Override
	public String toString() {
		return "PonudaTim3 [idPon=" + idPon + ", cena=" + cena + ", korisnikP=" + korisnikP + ", aukcijaP=" + aukcijaP
				+ "]";
	}

	public int getIdPon() {
		return idPon;
	}

	public void setIdPon(int idPon) {
		this.idPon = idPon;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public KorisnikTim3 getKorisnikP() {
		return korisnikP;
	}

	public void setKorisnikP(KorisnikTim3 korisnikP) {
		this.korisnikP = korisnikP;
	}

	public AukcijaTim3 getAukcijaP() {
		return aukcijaP;
	}

	public void setAukcijaP(AukcijaTim3 aukcijaP) {
		this.aukcijaP = aukcijaP;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPon;
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
		PonudaTim3 other = (PonudaTim3) obj;
		if (idPon != other.idPon)
			return false;
		return true;
	}

	
	
}
