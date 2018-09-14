package it.unimib.discover.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.unimib.discover.dao.impl.ItinerarioDAO;
import it.unimib.discover.model.ItinerarioModel;

@Component
public class ItinerarioModelValidator implements Validator {
	
	@Autowired
	private ItinerarioDAO itinerarioDAO;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == ItinerarioModel.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ItinerarioModel form = (ItinerarioModel) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "", "Nome obbligatorio");
        if(form.getNome() != null) {
        	Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        	Matcher m = p.matcher(form.getNome());
        	boolean b = m.find();
        	if(b) {
        		errors.rejectValue("nome", "", "Il nome dell'itinerario deve contenere solo numeri o lettere");
        	} else if(itinerarioDAO.existsItinerarioSameNameForUser(form.getNome(), form.getIdUtente())) {
        		errors.rejectValue("nome", "", "Esiste già un itinerario con lo stesso nome");
        	}
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divisione", "", "Divisione obbligatorio");
        if(form.getDivisione() != null) {
        	if(form.getDivisione() == 1) {
        		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataInizio", "", "Data inizio obbligatorio");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataFine", "", "Data fine obbligatorio");
        	} else {
        		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numeroGiorni", "", "Numero giorni obbligatorio");
        		if(form.getNumeroGiorni() != null && form.getNumeroGiorni() <= 0) {
        			errors.rejectValue("numeroGiorni", "", "Il numero di giorni deve essere maggiore di zero");
        		}
        	}
        }
        if(form.getId() == null) {
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idWishlist", "", "Wishlist obbligatorie");
        }
	}

}
