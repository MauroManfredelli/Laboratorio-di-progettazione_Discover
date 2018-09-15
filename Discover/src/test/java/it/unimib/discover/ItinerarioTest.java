package it.unimib.discover;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.List;

import it.unimib.discover.configuration.ApplicationInitializer;
import it.unimib.discover.entity.Itinerario;
import it.unimib.discover.entity.Lista;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Wishlist;
import it.unimib.discover.model.ItinerarioModel;
import it.unimib.discover.service.impl.ListeService;
import it.unimib.discover.service.impl.TestService;
import it.unimib.discover.validator.ItinerarioModelValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationInitializer.class)
@WebAppConfiguration
public class ItinerarioTest {
	
	@Autowired
	private ItinerarioModelValidator itinerarioModelValidator;
	
	@Autowired
	private ListeService listeService;
	
	@Autowired
	private TestService testService;
	
	private Wishlist wishlistTest;
	private MyUserAccount userTest;
	private Itinerario itinerarioTest, itinerarioTestSameName;
	
	/**
     * Imposta i parametri dei test. 
     * Crea delle wishlist di test per testare la creazione di itinerari.
     * (Invocato prima di tutti i metodi di test case)
     */
    @Before
    @Transactional
    public void setUp() {
    	userTest = testService.setUpTestUser();
    	wishlistTest = testService.setUpWishlist(userTest);
    	itinerarioTestSameName = testService.setUpItinerario(userTest);
    }

    /**
     * Cancella i parametri di test. 
     * Cancella i dati di test e gli itinerari di test creati
     * (Invocato dopo l'esecuzione di tutti i test case)
     */
    @After
    @Transactional
    public void tearDown() {
    	testService.tearDownTest(itinerarioTest, itinerarioTestSameName);
    }

	@Test
	public void testCase1() {
		ItinerarioModel im = new ItinerarioModel();
		im.setNome("");
		im.setIdUtente(userTest.getId());
		BindingResult errors = new BeanPropertyBindingResult(im, "itinerarioModel");
		itinerarioModelValidator.validate(im, errors);
		assertTrue(errors.hasFieldErrors("nome"));
	}

	@Test
	public void testCase2() {
		ItinerarioModel im = new ItinerarioModel();
		im.setNome("$prova");
		im.setIdUtente(userTest.getId());
		BindingResult errors = new BeanPropertyBindingResult(im, "itinerarioModel");
		itinerarioModelValidator.validate(im, errors);
		assertTrue(errors.hasFieldErrors("nome"));
	}

	@Test
	public void testCase3() {
		ItinerarioModel im = new ItinerarioModel();
		im.setNome("Test itinerario 1");
		im.setIdUtente(userTest.getId());
		BindingResult errors = new BeanPropertyBindingResult(im, "itinerarioModel");
		itinerarioModelValidator.validate(im, errors);
		assertTrue(errors.hasFieldErrors("nome"));
	}

	@Test
	public void testCase4() {
		ItinerarioModel im = new ItinerarioModel();
		im.setNome("Test itinerario");
		im.setDivisione(2);
		im.setIdUtente(userTest.getId());
		BindingResult errors = new BeanPropertyBindingResult(im, "itinerarioModel");
		itinerarioModelValidator.validate(im, errors);
		assertTrue(errors.hasFieldErrors("numeroGiorni"));
	}

	@Test
	public void testCase5() {
		ItinerarioModel im = new ItinerarioModel();
		im.setNome("Test itinerario");
		im.setDivisione(2);
		im.setNumeroGiorni(null);
		im.setIdUtente(userTest.getId());
		BindingResult errors = new BeanPropertyBindingResult(im, "itinerarioModel");
		itinerarioModelValidator.validate(im, errors);
		assertTrue(errors.hasFieldErrors("numeroGiorni"));
	}

	@Test
	public void testCase6() {
		ItinerarioModel im = new ItinerarioModel();
		im.setNome("Test itinerario");
		im.setDivisione(2);
		im.setNumeroGiorni(0);
		im.setIdUtente(userTest.getId());
		BindingResult errors = new BeanPropertyBindingResult(im, "itinerarioModel");
		itinerarioModelValidator.validate(im, errors);
		assertTrue(errors.hasFieldErrors("numeroGiorni"));
	}

	@Test
	public void testCase7() {
		ItinerarioModel im = new ItinerarioModel();
		im.setNome("Test itinerario");
		im.setDivisione(2);
		im.setNumeroGiorni(1);
		im.setIdUtente(userTest.getId());
		BindingResult errors = new BeanPropertyBindingResult(im, "itinerarioModel");
		itinerarioModelValidator.validate(im, errors);
		assertTrue(errors.hasFieldErrors("idWishlist"));
	}

	@Test
	public void testCase8() throws ParseException {
		ItinerarioModel im = new ItinerarioModel();
		im.setNome("Test itinerario");
		im.setDivisione(2);
		im.setNumeroGiorni(1);
		im.setIdUtente(userTest.getId());
		im.setIdWishlist(Lists.newArrayList(wishlistTest.getId()));
		BindingResult errors = new BeanPropertyBindingResult(im, "itinerarioModel");
		itinerarioModelValidator.validate(im, errors);
		assertFalse(errors.hasErrors());
		listeService.salvaItinerario(im, userTest);
		List<Lista> listeUtente = listeService.getListeByUser(userTest.getId(), "");
		itinerarioTest = getItinerarioTestFromListeUtente(listeUtente, im.getNome());
		assertNotNull(itinerarioTest);
		assertEquals(itinerarioTest.getVisite().size(), 2);
		assertNull(listeService.getListaById("wishlist"+wishlistTest.getId()));
	}

	private Itinerario getItinerarioTestFromListeUtente(List<Lista> listeUtente, String nomeItinerario) {
		for(Lista lista : listeUtente) {
			if(lista.getNome().equals(nomeItinerario)) {
				return listeService.getItinerarioById(lista.getIdItinerario());
			}
		}
		return null;
	}

}
