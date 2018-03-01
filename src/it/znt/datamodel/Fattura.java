package it.znt.datamodel;

/**
 * 
 * @author zante85
 *
 */
public class Fattura {
	private String tipoDocumento;
	private String data;
	private String numero;
	private String dataRegistrazione;
	private double imponibile22;
	private double imposta22;

	private double imponibile10;
	private double imposta10;

	private double imponibile4;
	private double imposta4;

	private double imponibileNOIVA;

	private String natura;
	private double percDetraibile;
	private String deducibile;
	private String esigibileIVA;

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public double getImponibile22() {
		return imponibile22;
	}

	public void setImponibile22(double imponibile22) {
		this.imponibile22 = imponibile22;
	}

	public double getImposta22() {
		return imposta22;
	}

	public void setImposta22(double imposta22) {
		this.imposta22 = imposta22;
	}

	public double getImponibile10() {
		return imponibile10;
	}

	public void setImponibile10(double imponibile10) {
		this.imponibile10 = imponibile10;
	}

	public double getImposta10() {
		return imposta10;
	}

	public void setImposta10(double imposta10) {
		this.imposta10 = imposta10;
	}

	public double getImponibile4() {
		return imponibile4;
	}

	public void setImponibile4(double imponibile4) {
		this.imponibile4 = imponibile4;
	}

	public double getImposta4() {
		return imposta4;
	}

	public void setImposta4(double imposta4) {
		this.imposta4 = imposta4;
	}

	public double getImponibileNOIVA() {
		return imponibileNOIVA;
	}

	public void setImponibileNOIVA(double imponibileNOIVA) {
		this.imponibileNOIVA = imponibileNOIVA;
	}

	public String getNatura() {
		return natura;
	}

	public void setNatura(String natura) {
		this.natura = natura;
	}

	public double getPercDetraibile() {
		return percDetraibile;
	}

	public void setPercDetraibile(double percDetraibile) {
		this.percDetraibile = percDetraibile;
	}

	public String getDeducibile() {
		return deducibile;
	}

	public void setDeducibile(String deducibile) {
		this.deducibile = deducibile;
	}

	public String getEsigibileIVA() {
		return esigibileIVA;
	}

	public void setEsigibileIVA(String esigibileIVA) {
		this.esigibileIVA = esigibileIVA;
	}

	public String getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

}
