package it.znt.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import it.znt.datamodel.Fattura;
import it.znt.datamodel.Indirizzo;
import it.znt.datamodel.Soggetto;

/**
 * 
 * @author zante85
 *
 */
public class GeneralMethods {

	SimpleDateFormat formatterInput = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatterOutput = new SimpleDateFormat("yyyy-MM-dd");

	public Document generaHeader(String cf, Element elementInput, Document doc) {

		Element rootElement = doc.createElement("DatiFatturaHeader");
		elementInput.appendChild(rootElement);
		// Element progressivoInvio = doc.createElement("ProgressivoInvio");
		// rootElement.appendChild(progressivoInvio);
		// progressivoInvio.appendChild(doc.createTextNode("00001"));
		Element dichiarante = doc.createElement("Dichiarante");
		rootElement.appendChild(dichiarante);
		/**********************************************************/
		Element codiceFiscale = doc.createElement("CodiceFiscale");
		dichiarante.appendChild(codiceFiscale);
		codiceFiscale.appendChild(doc.createTextNode(cf));
		/**********************************************************/
		Element carica = doc.createElement("Carica");
		dichiarante.appendChild(carica);
		carica.appendChild(doc.createTextNode("01"));

		return doc;
	}

	public static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

	public Fattura creaFatturaDaLineaCSV(String[] dati, boolean dte) throws ParseException {
		Fattura result = new Fattura();
		if (dati != null) {

			boolean trovato = false;
			if (notVuota(dati[11]) && Double.parseDouble(dati[11].trim()) > 0) {
				result.setImponibile22(Double.parseDouble(dati[11].trim()));
				result.setImposta22(Double.parseDouble(dati[12].trim()));
				trovato = true;
			}
			if (notVuota(dati[13]) && Double.parseDouble(dati[13].trim()) > 0) {
				result.setImponibile10(Double.parseDouble(dati[13].trim()));
				result.setImposta10(Double.parseDouble(dati[14].trim()));
				trovato = true;
			}
			if (notVuota(dati[15]) && Double.parseDouble(dati[15].trim()) > 0) {
				result.setImponibile4(Double.parseDouble(dati[15].trim()));
				result.setImposta4(Double.parseDouble(dati[16].trim()));
				trovato = true;
			}
			if (dati.length > 23 && notVuota(dati[24]) && Double.parseDouble(dati[24].trim()) > 0) {
				result.setImponibileNOIVA(Double.parseDouble(dati[24].trim()));
				trovato = true;
			}

			if (!trovato) {
				if (notVuota(dati[11])) {
					result.setImponibile22(Double.parseDouble(dati[11].trim()));
					result.setImposta22(Double.parseDouble(dati[12].trim()));
					trovato = true;
				}
				if (notVuota(dati[13])) {
					result.setImponibile10(Double.parseDouble(dati[13].trim()));
					result.setImposta10(Double.parseDouble(dati[14].trim()));
					trovato = true;
				}
				if (notVuota(dati[15])) {
					result.setImponibile4(Double.parseDouble(dati[15].trim()));
					result.setImposta4(Double.parseDouble(dati[16].trim()));
					trovato = true;
				}
				if (dati.length > 23 && notVuota(dati[24])) {
					result.setImponibileNOIVA(Double.parseDouble(dati[24].trim()));
					trovato = true;
				}
			}

			if (result.getImponibile22() < 0) {
				result.setTipoDocumento("TD04");
			} else if (dati[4].trim().toUpperCase().startsWith(Constant.SPECIAL_CHAR)) {
				result.setTipoDocumento("TD10");
			} else {
				result.setTipoDocumento("TD01");
			}

			if (notVuota(dati[1])) {
				if (dte) {
					result.setNumero(dati[1].trim());
				} else {
					result.setNumero("0");
				}
			} else {
				return null;
			}

			if (notVuota(dati[2])) {

				Date date = formatterInput.parse(dati[2].trim());
				result.setData(formatterOutput.format(date));

			}
			// result.setData(data);
			// result.setDeducibile(deducibile);
			// result.setEsigibileIVA(esigibileIVA);
			// result.setPercDetraibile(percDetraibile);
			// result.setNatura("N1");

			if (notVuota(dati[0])) {

				Date date = formatterInput.parse(dati[0].trim());
				result.setDataRegistrazione(formatterOutput.format(date));

			}

			return result;
		}

		return null;
	}

	private boolean notVuota(String string) {
		if (string != null && !string.trim().equals("")) {
			return true;
		}
		return false;
	}

	public Soggetto creaFornitoreDaLineaCSV(String[] dati, boolean ricevute) {
		Soggetto result = new Soggetto();
		if (dati != null) {

			if (!notVuota(dati[5])) {
				System.out.println("SALTATA: " + dati[4].trim().toUpperCase() + " - PROGRESSIVO: " + dati[1].trim());
				return null;
			} else {
				// System.out.println("ELABORO: " + dati[4].trim().toUpperCase() + " -
				// PROGRESSIVO: " + dati[1].trim());
			}

			if (dati[5] != null) {

				// result.setCf(cf);
				// result.setCognome(cognome);

				result.setDenominazione(dati[4].trim().toUpperCase());

				/* CUSTOM PART */
				if (dati[4].trim().toUpperCase().startsWith(Constant.SPECIAL_CHAR)) {
					result.setPiva(dati[9].trim().toUpperCase());
					result.setSede(new Indirizzo());
					result.getSede().setCap(dati[5].trim().toUpperCase());
					result.getSede().setComune(dati[7].trim().toUpperCase());
					result.getSede().setIndirizzo(dati[6].trim().toUpperCase());
					result.getSede().setNazione(Constant.SPECIAL_NATION);
					// result.getSede().setProvincia(dati[8].trim().toUpperCase());
				} else {
					// result.setNome(nome);
					result.setPiva(dati[9].trim().toUpperCase());
					result.setSede(new Indirizzo());
					result.getSede().setCap(dati[5].trim().toUpperCase());
					result.getSede().setComune(dati[7].trim().toUpperCase());
					result.getSede().setIndirizzo(dati[6].trim().toUpperCase());
					result.getSede().setNazione("IT");
					result.getSede().setProvincia(dati[8].trim().toUpperCase());
				}

				if (ricevute) {
					// Nella DTR non si può emettere per CF
					if (result.getPiva() != null
							&& ((!result.getPiva().startsWith("9") && GeneralMethods.isNumeric(result.getPiva()))
									|| (result.getPiva().equals(Constant.SPECIAL_PIVA)))) {

					} else {
						System.out.println(
								"SALTATA: " + dati[4].trim().toUpperCase() + " - PROGRESSIVO: " + dati[1].trim());
						return null;
					}
				}

				return result;
			}

			// result.getSede().setNumeroCivico(numeroCivico);
		}
		return null;
	}

}
