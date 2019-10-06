package com.restaurante.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.event.RecursoCriadoEvent;
import com.restaurante.models.Cardapio;
import com.restaurante.repository.CardapioRepository;
import com.restaurante.service.CardapioService;

@RestController
@RequestMapping("/cardapio")
public class CardapioResource {

	@Autowired
	private CardapioRepository cr;
	
	@Autowired
	private CardapioService cardapioService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping()
	public List<Cardapio> listar(){
		return cr.findAll();
	}
	
	@PostMapping()
	public ResponseEntity<Cardapio> inserir(@Valid @RequestBody Cardapio c, HttpServletResponse response){

		 Cardapio cardapioSalva = cr.save(c); 

		 publisher.publishEvent(new RecursoCriadoEvent(this, response, cardapioSalva.getCodigo()));
			return ResponseEntity.status(HttpStatus.CREATED).body(cardapioSalva);
			
	
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Cardapio cardapio = cr.findOne(codigo);
		return (cardapio != null) ? ResponseEntity.ok(cardapio) : ResponseEntity.notFound().build();
	}
	

	//======================================= atualizar ==========================================
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cardapio> atualizarCardapio(@PathVariable Long codigo, @Valid @RequestBody Cardapio cardapio){
		Cardapio cardapioSalva = cardapioService.atualiza(codigo, cardapio);
		return ResponseEntity.ok(cardapioSalva);
	}
	

	//======================================= atualizar ==========================================
	
}
