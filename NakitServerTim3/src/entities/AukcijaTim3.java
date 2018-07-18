package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity

@NamedQueries({
	
	//u ovu tabelu stavljam samo one koji su aktuelni
	@NamedQuery(name = "AukcijaTim3.getAllProizvodi", query = "SELECT a from AukcijaTim3 a where a.aktuelna = :aktuelna"),
	
	@NamedQuery(name = "AukcijaTim3.getById", query = "SELECT a from AukcijaTim3 a WHERE a.id = :id"),
	
	@NamedQuery(name = "AukcijaTim3.getByTip", query = "SELECT a from AukcijaTim3 a WHERE UPPER(a.tip) = UPPER(:tip)"),
	
	@NamedQuery(name = "AukcijaTim3.getByCena", query = "SELECT a from AukcijaTim3 a WHERE a.cena = :cena"),
	
	@NamedQuery(name = "AukcijaTim3.getByMaterijal", query = "SELECT a from AukcijaTim3 a WHERE UPPER(a.materijal) = UPPER(:materijal)"),
	
	@NamedQuery(name = "AukcijaTim3.getByBoja", query = "SELECT a from AukcijaTim3 a WHERE UPPER(a.boja) = UPPER(:boja)"),
	
	@NamedQuery(name = "AukcijaTim3.getByProdavac", query = "SELECT a from AukcijaTim3 a WHERE a.korisnik.kIme = :prodavac")	
})

public class AukcijaTim3 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;  //ovde je bio String
	
	private String tip;
	private String materijal;
	private String boja;
	private int cena;
	private boolean aktuelna;
	
	@Lob
	private byte[] slika;
	
	@ManyToOne
	private KorisnikTim3 korisnik;
	
	@OneToMany (mappedBy = "aukcijaK")
	private List<KomentarTim3> komentari;
	
	@OneToMany (mappedBy = "aukcijaP")
	private List<PonudaTim3> ponude;
	
	
	@Override
	public String toString() {
		return "AukcijaTim3 [id=" + id + ", tip=" + tip + ", materijal=" + materijal + ", boja=" + boja + ", cena="
				+ cena + ", aktuelna=" + aktuelna + ", korisnik=" + korisnik + "]";
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boja == null) ? 0 : boja.hashCode());
		result = prime * result + cena;
		result = prime * result + id;
		result = prime * result + ((tip == null) ? 0 : tip.hashCode());
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
		AukcijaTim3 other = (AukcijaTim3) obj;
		if (boja == null) {
			if (other.boja != null)
				return false;
		} else if (!boja.equals(other.boja))
			return false;
		if (cena != other.cena)
			return false;
		if (id != other.id)
			return false;
		if (korisnik == null) {
			if (other.korisnik != null)
				return false;
		} 
		if (tip == null) {
			if (other.tip != null)
				return false;
		} else if (!tip.equals(other.tip))
			return false;
		return true;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getMaterijal() {
		return materijal;
	}

	public void setMaterijal(String materijal) {
		this.materijal = materijal;
	}

	public String getBoja() {
		return boja;
	}

	public void setBoja(String boja) {
		this.boja = boja;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public boolean isAktuelna() {
		return aktuelna;
	}

	public void setAktuelna(boolean aktuelna) {
		this.aktuelna = aktuelna;
	}

	public KorisnikTim3 getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(KorisnikTim3 korisnik) {
		this.korisnik = korisnik;
	}

	public byte[] getSlika() {
		return slika;
	}

	public void setSlika(byte[] slika) {
		this.slika = slika;
	}
	
}
