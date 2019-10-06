package com.restaurante.exceptionhanndler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class RestauranteExceptionHandler extends ResponseEntityExceptionHandler  {

		@Autowired
		private MessageSource messageSource;


		
		
		//-----------------------------METODOS JA EXISTENTES PARA CAPTURA DE ERROS----------------------------------
		
		@Override
		protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {

				String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
			
				String mensagemDesenvolvedor =  ex.getCause() != null ? ex.getCause().toString() : ex.toString();
			
			
			List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
			return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);

		}
		
		




		//handleMethodArgumentNotValid: pega os valores que nao sao validados e joga dentro desse metodo(EX.: Exceder o num de caracteres ou pôr null)
		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {
			
			//cria uma lista de varios valores pois pode acontecer do usuario ter errado mais de um campo/local
			List<Erro> erros = criarListaDeErros(ex.getBindingResult());
			return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
		}
		
		
		@ExceptionHandler({DataIntegrityViolationException.class})
		public ResponseEntity<Object> handleDataIntegrityViolationException(WebRequest request, DataIntegrityViolationException ex){
			
			String mensagemUsuario = messageSource.getMessage("mensagem.operacao-nao-sucedida", null, LocaleContextHolder.getLocale());
			

			String mensagemDesenvolvedor =  ex.getCause() != null ? ex.getCause().toString() : ex.toString();
			List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
			
			return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request) ;
			
		}

		
		
		
		
		
		//========================================DELETE=====================================================
		@ExceptionHandler({EmptyResultDataAccessException.class})
		public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
			String mensagemUsuario = messageSource.getMessage("mensagem.nao-encontrado",null, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = ex.toString();
			
			List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
			return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		}
		//========================================DELETE=====================================================
		
		//--------------------------METODOS JA EXISTENTES PARA CAPTURA DE ERROS(FIM)-------------------------
		
		
		//-------------------------------------METODOS-------------------------------------------------------
		
		//retorna o array de erros de mais de um atributo
		private List<Erro> criarListaDeErros(BindingResult bindingResult) {
			List<Erro> erros = new ArrayList<>();

			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
				String mensagemDesenvolvedor = fieldError.toString();
				
				erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));

			}
			return erros;
		}
		
		
		
		//-------------------------------------METODOS-------------------------------------------------------
		
		
		public static class Erro {
			private String mensagemUsuario;
			private String mensagemDesenvolvedor;
			
			public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
				this.mensagemUsuario = mensagemUsuario;
				this.mensagemDesenvolvedor = mensagemDesenvolvedor;			
				
			}
			
			public String getMensagemUsuario() {
				return mensagemUsuario;
			}

			public String getMensagemDesenvolvedor() {
				return mensagemDesenvolvedor;
			}


			
			
		}
	

}
