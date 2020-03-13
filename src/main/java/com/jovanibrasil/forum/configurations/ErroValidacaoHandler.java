package com.jovanibrasil.forum.configurations;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jovanibrasil.forum.forms.ErroDeFormularioForm;

@RestControllerAdvice
public class ErroValidacaoHandler {

	private final MessageSource messageSource;
	
	public ErroValidacaoHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioForm> handle(MethodArgumentNotValidException exception) {
		Locale locale = LocaleContextHolder.getLocale();
		return exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(e -> 
				new ErroDeFormularioForm(e.getField(), messageSource.getMessage(e, locale))
			).collect(Collectors.toList());
	}
	
}
