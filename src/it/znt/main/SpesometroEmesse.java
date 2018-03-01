package it.znt.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import it.znt.classes.Constant;
import it.znt.classes.GeneralMethods;
import it.znt.classes.MapperDTE;
import it.znt.datamodel.Fattura;
import it.znt.datamodel.Indirizzo;
import it.znt.datamodel.Soggetto;

/**
 * 
 * @author zante85
 *
 */
public class SpesometroEmesse {

	private static final Soggetto CED_PRESTATORE = new Soggetto();
	static {
		CED_PRESTATORE.setCognome(Constant.COGNOME);
		CED_PRESTATORE.setNome(Constant.NOME);
		CED_PRESTATORE.setPiva(Constant.PIVA);
		CED_PRESTATORE.setSede(new Indirizzo());
		CED_PRESTATORE.getSede().setCap(Constant.CAP);
		CED_PRESTATORE.getSede().setComune(Constant.COMUNE);
		CED_PRESTATORE.getSede().setProvincia(Constant.PROVINCIA);
		CED_PRESTATORE.getSede().setNazione(Constant.NAZIONE);
		CED_PRESTATORE.getSede().setIndirizzo(Constant.INDIRIZZO);
	}

	public static void main(String[] args) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();

			GeneralMethods metodi = new GeneralMethods();
			MapperDTE mapper = new MapperDTE();
			Element rootElement = doc.createElement("ns2:DatiFattura");
			rootElement.setAttribute("versione", "DAT20");
			rootElement.setAttribute("xmlns:ns2", "http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v2.0");
			doc.appendChild(rootElement);

			doc = metodi.generaHeader(Constant.CF, rootElement, doc);

			/************************ DTE ****************************************/

			Element dteElement = doc.createElement("DTE");
			rootElement.appendChild(dteElement);
			doc = mapper.createCedentePrestatoreDTE(dteElement, doc, CED_PRESTATORE);
			File file = new File(Constant.PATH_FILE + Constant.FILE_EMESSE);

			Map<String, List<Fattura>> fatturePerFornitore = new HashMap<String, List<Fattura>>();
			Map<String, Soggetto> soggetti = new HashMap<String, Soggetto>();

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] dati = line.split(";");
					Soggetto soggetto = metodi.creaFornitoreDaLineaCSV(dati, false);

					if (soggetto != null) {
						Fattura fattura = metodi.creaFatturaDaLineaCSV(dati, true);
						if (fattura != null) {
							if (soggetto != null) {

								if (soggetto.getCf() != null && fatturePerFornitore.containsKey(soggetto.getCf())) {
									fatturePerFornitore.get(soggetto.getCf()).add(fattura);
								} else if (soggetto.getPiva() != null
										&& fatturePerFornitore.containsKey(soggetto.getPiva())) {
									fatturePerFornitore.get(soggetto.getPiva()).add(fattura);
								} else if (soggetto.getCf() != null) {
									fatturePerFornitore.put(soggetto.getCf(), new ArrayList<Fattura>());
									soggetti.put(soggetto.getCf(), soggetto);
									fatturePerFornitore.get(soggetto.getCf()).add(fattura);
								} else if (soggetto.getPiva() != null) {
									fatturePerFornitore.put(soggetto.getPiva(), new ArrayList<Fattura>());
									soggetti.put(soggetto.getPiva(), soggetto);
									fatturePerFornitore.get(soggetto.getPiva()).add(fattura);
								}
							}
						}
					}
				}
			}

			for (String cf : fatturePerFornitore.keySet()) {
				doc = mapper.createCessionarioCommittenteDTE(dteElement, doc, soggetti.get(cf),
						fatturePerFornitore.get(cf));
			}

			/****************************************************************/

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(
					Constant.PATH_FILE + "IT" + Constant.PIVA + "_DF_C000" + Constant.PROGRESSIVO_FILE + ".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
			System.out.println("SOMMA IMPONIBILI " + MapperDTE.IMPONIBILI.doubleValue());
			System.out.println("IVA " + MapperDTE.IVA.doubleValue());
			System.out.println("File saved!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
