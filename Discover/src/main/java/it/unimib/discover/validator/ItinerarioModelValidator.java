package it.unimib.discover.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.unimib.discover.model.ItinerarioModel;

@Component
public class ItinerarioModelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == ItinerarioModel.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ItinerarioModel form = (ItinerarioModel) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "", "Nome obbligatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divisione", "", "Divisione obbligatorio");
        if(form.getDivisione() != null) {
        	if(form.getDivisione() == 1) {
        		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataInizio", "", "Data inizio obbligatorio");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataFine", "", "Data fine obbligatorio");
        	} else {
        		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numeroGiorni", "", "Numero giorni obbligatorio");
        	}
        }
        if(form.getId() == null) {
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idWishlist", "", "Wishlist obbligatorie");
        }
	}

}
