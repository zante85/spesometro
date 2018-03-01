package it.znt.classes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import it.znt.datamodel.Fattura;
import it.znt.datamodel.Soggetto;

/**
 * 
 * @author zante85
 *
 */
public class MapperDTR {

	public static BigDecimal IMPONIBILI = new BigDecimal(0);
	public static BigDecimal IVA = new BigDecimal(0);
	public NumberFormat formatDouble = new DecimalFormat("#0.00");

	public Document createCessionarioCommittenteDTR(Element dtrElement, Document doc, Soggetto soggetto) {
		Element rootElement = doc.createElement("CessionarioCommittenteDTR");
		dtrElement.appendChild(rootElement);
		Element identificativiFiscali = doc.createElement("IdentificativiFiscali");
		rootElement.appendChild(identificativiFiscali);

		if (soggetto.getPiva() != null && !soggetto.getPiva().startsWith("9")
				&& GeneralMethods.isNumeric(soggetto.getPiva())) {
			Element idFiscaleIVA = doc.createElement("IdFiscaleIVA");
			identificativiFiscali.appendChild(idFiscaleIVA);
			/*************************************************************/
			Element idPaese = doc.createElement("IdPaese");
			idFiscaleIVA.appendChild(idPaese);
			idPaese.appendChild(doc.createTextNode("IT"));
			/*************************************************************/
			Element idCodice = doc.createElement("IdCodice");
			idFiscaleIVA.appendChild(idCodice);
			idCodice.appendChild(doc.createTextNode(soggetto.getPiva()));
		} else if (soggetto.getPiva() != null) {
			Element codiceFiscale = doc.createElement("CodiceFiscale");
			identificativiFiscali.appendChild(codiceFiscale);
			codiceFiscale.appendChild(doc.createTextNode(soggetto.getPiva()));
		} else {
			Element codiceFiscale = doc.createElement("CodiceFiscale");
			identificativiFiscali.appendChild(codiceFiscale);
			codiceFiscale.appendChild(doc.createTextNode(soggetto.getCf()));
		}
		/**********************************************************/
		/**********************************************************/
		Element altriDatiIdentificativi = doc.createElement("AltriDatiIdentificativi");
		rootElement.appendChild(altriDatiIdentificativi);
		if (soggetto.getDenominazione() != null) {
			Element denominazione = doc.createElement("Denominazione");
			altriDatiIdentificativi.appendChild(denominazione);
			denominazione.appendChild(doc.createTextNode(soggetto.getDenominazione()));
		} else if (soggetto.getCognome() != null) {
			Element nome = doc.createElement("Nome");
			altriDatiIdentificativi.appendChild(nome);
			nome.appendChild(doc.createTextNode(soggetto.getNome()));
			Element cognome = doc.createElement("Cognome");
			altriDatiIdentificativi.appendChild(cognome);
			cognome.appendChild(doc.createTextNode(soggetto.getCognome()));
		}
		/*************************************************************/
		/********************** SEDE *********************************/
		Element sede = doc.createElement("Sede");
		altriDatiIdentificativi.appendChild(sede);
		Element indirizzo = doc.createElement("Indirizzo");
		sede.appendChild(indirizzo);
		indirizzo.appendChild(doc.createTextNode(soggetto.getSede().getIndirizzo()));
		/*************************************************************/
		Element cap = doc.createElement("CAP");
		sede.appendChild(cap);
		cap.appendChild(doc.createTextNode(soggetto.getSede().getCap()));
		/*************************************************************/
		Element comune = doc.createElement("Comune");
		sede.appendChild(comune);
		comune.appendChild(doc.createTextNode(soggetto.getSede().getComune()));
		/*************************************************************/
		Element provincia = doc.createElement("Provincia");
		sede.appendChild(provincia);
		provincia.appendChild(doc.createTextNode(soggetto.getSede().getProvincia()));
		/*************************************************************/
		Element nazione = doc.createElement("Nazione");
		sede.appendChild(nazione);
		nazione.appendChild(doc.createTextNode(
				soggetto.getSede().getNazione()));/*************************************************************/
		return doc;
	}

	public Document createCedentePrestatoreDTR(Element dtrElement, Document doc, Soggetto soggetto,
			List<Fattura> fatture) {
		Element rootElement = doc.createElement("CedentePrestatoreDTR");
		dtrElement.appendChild(rootElement);
		Element identificativiFiscali = doc.createElement("IdentificativiFiscali");
		rootElement.appendChild(identificativiFiscali);

		if (soggetto.getPiva() != null
				&& ((!soggetto.getPiva().startsWith("9") && GeneralMethods.isNumeric(soggetto.getPiva()))
						|| (soggetto.getPiva().equals(Constant.SPECIAL_PIVA)))) {
			Element idFiscaleIVA = doc.createElement("IdFiscaleIVA");
			identificativiFiscali.appendChild(idFiscaleIVA);
			/*************************************************************/
			Element idPaese = doc.createElement("IdPaese");
			idFiscaleIVA.appendChild(idPaese);
			if (soggetto.getDenominazione().startsWith(Constant.SPECIAL_CHAR)) {
				idPaese.appendChild(doc.createTextNode(Constant.SPECIAL_NATION));
			} else {
				idPaese.appendChild(doc.createTextNode("IT"));
			}
			/*************************************************************/
			Element idCodice = doc.createElement("IdCodice");
			idFiscaleIVA.appendChild(idCodice);
			idCodice.appendChild(doc.createTextNode(soggetto.getPiva()));
		} else if (soggetto.getPiva() != null) {
			// Nella DTR non si può emettere per CF
			Element codiceFiscale = doc.createElement("CodiceFiscale");
			identificativiFiscali.appendChild(codiceFiscale);
			codiceFiscale.appendChild(doc.createTextNode(soggetto.getPiva()));
			return null;
		} else {
			// Nella DTR non si può emettere per CF
			Element codiceFiscale = doc.createElement("CodiceFiscale");
			identificativiFiscali.appendChild(codiceFiscale);
			codiceFiscale.appendChild(doc.createTextNode(soggetto.getCf()));
			return null;
		}
		/*************************************************************/
		/*************************************************************/
		Element altriDatiIdentificativi = doc.createElement("AltriDatiIdentificativi");
		rootElement.appendChild(altriDatiIdentificativi);
		if (soggetto.getDenominazione() != null) {
			Element denominazione = doc.createElement("Denominazione");
			altriDatiIdentificativi.appendChild(denominazione);
			denominazione.appendChild(doc.createTextNode(soggetto.getDenominazione()));
		} else if (soggetto.getCognome() != null) {
			Element nome = doc.createElement("Nome");
			altriDatiIdentificativi.appendChild(nome);
			nome.appendChild(doc.createTextNode(soggetto.getNome()));
			Element cognome = doc.createElement("Cognome");
			altriDatiIdentificativi.appendChild(cognome);
			cognome.appendChild(doc.createTextNode(soggetto.getCognome()));
		}
		/*************************************************************/
		/********************** SEDE *********************************/
		Element sede = doc.createElement("Sede");
		altriDatiIdentificativi.appendChild(sede);
		Element indirizzo = doc.createElement("Indirizzo");
		sede.appendChild(indirizzo);
		indirizzo.appendChild(doc.createTextNode(soggetto.getSede().getIndirizzo()));
		/*************************************************************/
		Element cap = doc.createElement("CAP");
		sede.appendChild(cap);
		cap.appendChild(doc.createTextNode(soggetto.getSede().getCap()));
		/*************************************************************/
		Element comune = doc.createElement("Comune");
		sede.appendChild(comune);
		comune.appendChild(doc.createTextNode(soggetto.getSede().getComune()));
		/*************************************************************/
		if (!soggetto.getDenominazione().startsWith(Constant.SPECIAL_CHAR)) {
			Element provincia = doc.createElement("Provincia");
			sede.appendChild(provincia);
			provincia.appendChild(doc.createTextNode(soggetto.getSede().getProvincia()));
		}
		/*************************************************************/
		Element nazione = doc.createElement("Nazione");
		sede.appendChild(nazione);
		nazione.appendChild(doc.createTextNode(soggetto.getSede().getNazione()));
		/*************************************************************/
		for (Fattura fattura : fatture) {
			doc = createDatiFatturaDTR(rootElement, doc, fattura);
		}

		return doc;
	}

	private Document createDatiFatturaDTR(Element cedentePrestatoreDTR, Document doc, Fattura fattura) {
		Element rootElement = doc.createElement("DatiFatturaBodyDTR");
		cedentePrestatoreDTR.appendChild(rootElement);
		/*************************************************************/
		Element datiGenerali = doc.createElement("DatiGenerali");
		rootElement.appendChild(datiGenerali);
		/*************************************************************/
		Element tipoDocumento = doc.createElement("TipoDocumento");
		datiGenerali.appendChild(tipoDocumento);
		tipoDocumento.appendChild(doc.createTextNode(fattura.getTipoDocumento()));
		/*************************************************************/
		Element data = doc.createElement("Data");
		datiGenerali.appendChild(data);
		data.appendChild(doc.createTextNode(fattura.getData()));
		/*************************************************************/
		Element numero = doc.createElement("Numero");
		datiGenerali.appendChild(numero);
		numero.appendChild(doc.createTextNode(fattura.getNumero()));
		/*************************************************************/
		/*************************************************************/
		Element dataRegistrazione = doc.createElement("DataRegistrazione");
		datiGenerali.appendChild(dataRegistrazione);
		dataRegistrazione.appendChild(doc.createTextNode(fattura.getDataRegistrazione()));
		/*************************************************************/

		if (fattura.getImponibile22() != 0) {
			addDatiRiepilogo(rootElement, doc, fattura.getImponibile22(), fattura.getImposta22(), 22.00);
		}
		if (fattura.getImponibile10() != 0) {
			addDatiRiepilogo(rootElement, doc, fattura.getImponibile10(), fattura.getImposta10(), 10.00);
		}
		if (fattura.getImponibile4() != 0) {
			addDatiRiepilogo(rootElement, doc, fattura.getImponibile4(), fattura.getImposta4(), 4.00);
		}
		if (fattura.getImponibileNOIVA() != 0) {
			addDatiRiepilogo(rootElement, doc, fattura.getImponibileNOIVA(), 0.00, 0.00);
		}

		/*************************************************************/

		return doc;
	}

	private void addDatiRiepilogo(Element rootElement, Document doc, double imponibileFatt, double impostaFatt,
			double aliquotaFatt) {
		Element datiRiepilogo = doc.createElement("DatiRiepilogo");
		rootElement.appendChild(datiRiepilogo);
		/*************************************************************/
		Element imponibileImporto = doc.createElement("ImponibileImporto");
		datiRiepilogo.appendChild(imponibileImporto);
		IMPONIBILI = IMPONIBILI.add(new BigDecimal(imponibileFatt));
		imponibileImporto.appendChild(
				doc.createTextNode(formatDouble.format(imponibileFatt).replaceAll("\\.", "").replaceAll(",", "\\.")));
		/*************************************************************/
		Element datiIVA = doc.createElement("DatiIVA");
		datiRiepilogo.appendChild(datiIVA);
		Element imposta = doc.createElement("Imposta");
		datiIVA.appendChild(imposta);
		IVA = IVA.add(new BigDecimal(impostaFatt));
		imposta.appendChild(
				doc.createTextNode(formatDouble.format(impostaFatt).replaceAll("\\.", "").replaceAll(",", "\\.")));
		Element aliquota = doc.createElement("Aliquota");
		datiIVA.appendChild(aliquota);
		aliquota.appendChild(
				doc.createTextNode(formatDouble.format(aliquotaFatt).replaceAll("\\.", "").replaceAll(",", "\\.")));
		/*************************************************************/
		// Element detraibile = doc.createElement("Detraibile");
		// datiRiepilogo.appendChild(detraibile);
		// detraibile.appendChild(doc.createTextNode("100"));
		/*************************************************************/
		// Element deducibile = doc.createElement("Deducibile");
		// datiRiepilogo.appendChild(deducibile);
		// deducibile.appendChild(doc.createTextNode("100"));
		/*************************************************************/
		// Element esigibilitaIVA = doc.createElement("EsigibilitaIVA");
		// datiRiepilogo.appendChild(esigibilitaIVA);
		// esigibilitaIVA.appendChild(doc.createTextNode("I"));
		/*************************************************************/
		if (impostaFatt == 0) {
			Element natura = doc.createElement("Natura");
			datiRiepilogo.appendChild(natura);
			natura.appendChild(doc.createTextNode("N2"));
		}
	}

}
