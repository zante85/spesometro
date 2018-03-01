package it.znt.datamodel;

/**
 * 
 * @author zante85
 *
 */
public class Soggetto {

	private String nome;
	private String cognome;
	private String denominazione;
	private String piva;
	private String cf;
	private Indirizzo sede;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Indirizzo getSede() {
		return sede;
	}

	public void setSede(Indirizzo sede) {
		this.sede = sede;
	}

}
