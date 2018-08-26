package it.unimib.discover.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.entity.Recensione;

@Service
public class RecensioniService {
	
	@Autowired
	private FotoService fotoService;
	
	@Autowired
	private MyUserAccountDAO myUserAccountDAO;
	
	@Transactional
	public List<Recensione> getRecensioniEsempioLaPelosa() throws ParseException {
		List<Recensione> recensioni = new ArrayList<Recensione>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		recensioni.add(new Recensione("Vivamente consigliata", "Spiaggia eccezionale peccato la calca di gente ma ad agosto credo sia più che normale!" + 
				"Il parcheggio si paga 2 euro l’ora ma vista la spiaggia ne vale la pena!!!! " + 
				"Da sapere assolutamente che sotto agli asciugamani bisogna mettere la stuoia per evitare di portare via la sabbia dalla spiaggia. Girano le guardie per controllare le persone singolarmente e la multa è di 100 euro circa.... ma nella via ci sono molti ambulanti che vendono stuoie di tutti i tipi a poco prezzo!", 
				5.0, null, true, myUserAccountDAO.findById("utenzaesempioSB"), sdf.parse("08/08/2018"),
				fotoService.getFotoEsempioLaPelosa().subList(0, 2)));
		recensioni.add(new Recensione("Una piscina naturale ma...", "Siamo arrivati in mattina per le 11 ed era impossibile starci, nessun parcheggio nei dintorni e spiaggia super affollata, quindi siamo tornati sul tardo pomeriggio verso le 18, parcheggi disponibili al prezzo di 2€ ogni ora e spiaggia finalmente utilizzabile, ricordatevi di comprare in precedenza le stuoie perché sono OBBLIGATORIE, mare semplicemente fantastico, una piscina naturale.", 
				4.0, null, true, myUserAccountDAO.findById("utenzaesempioHY"), sdf.parse("18/08/2018"),
				fotoService.getFotoEsempioLaPelosa().subList(2, 3)));
		return recensioni;
	}
	
	@Transactional
	public List<Recensione> getRecensioniEsempioPonteSanMichele() throws ParseException {
		List<Recensione> recensioni = new ArrayList<Recensione>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		recensioni.add(new Recensione("Una grande opera architettonica nascosta", "Dalla ciclabile sottostante si può ammirare in tutto il suo splendore. Davvero impressionante. Quando poi si viene a conoscere la storia da chi ha studiato bene l'opera, lo si apprezza ancora di più. Peccato che la ciclabile non sia tenuta bene in tutti i chilometri di percorrenza.", 
				5.0, null, true, myUserAccountDAO.findById("utenzaesempioRB"), sdf.parse("17/07/2018"),
				fotoService.getFotoEsempioPonteSanMichele().subList(0, 2)));
		recensioni.add(new Recensione("Passaggio pendolare", "...la protezione che hanno messo x evitare i suicidi non serve a nulla e in più hanno reso la struttura orribile. Per il traffico già si sa xk è da sempre a senso alternato. Puntualizzazione x i vigili di paderno: date le multe a chi passa con il rossa da Paderno a Calusco altrimenti fate a meno di far funzionare il semaforo. Non passa una sola macchina con il rosso ma anche fino a 9-10!...quelli di Calusco la danno la multa..eccome se le danno.", 
				4.0, null, true, myUserAccountDAO.findById("utenzaesempioAR"), sdf.parse("19/07/2018"),
				fotoService.getFotoEsempioPonteSanMichele().subList(2, 3)));
		return recensioni;
	}

}
