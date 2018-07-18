package beans;

import javax.ejb.Remote;

@Remote
public interface RegistracijaTim3Remote {
	
	boolean registrujSe(String kIme, String ime, String prezime, String email, String lozinka, String opis, String pol);
	
}
