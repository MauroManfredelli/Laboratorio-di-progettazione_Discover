package it.unimib.discover.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unimib.discover.entity.Foto;

@Service
public class FotoService {
	
	@Transactional
	public List<Foto> getFotoEsempioLaPelosa() {
		List<Foto> foto = new ArrayList<Foto>();
		foto.add(new Foto("/discover/resources/dist/img/attrazione100/1.jpg"));
		foto.add(new Foto("/discover/resources/dist/img/attrazione100/2.jpg"));
		foto.add(new Foto("/discover/resources/dist/img/attrazione100/3.jpg"));
		return foto;
	}
	
	@Transactional
	public List<Foto> getFotoEsempioPonteSanMichele() {
		List<Foto> foto = new ArrayList<Foto>();
		foto.add(new Foto("/discover/resources/dist/img/attrazione101/1.jpg"));
		foto.add(new Foto("/discover/resources/dist/img/attrazione101/2.jpg"));
		foto.add(new Foto("/discover/resources/dist/img/attrazione101/3.jpg"));
		return foto;
	}

}
