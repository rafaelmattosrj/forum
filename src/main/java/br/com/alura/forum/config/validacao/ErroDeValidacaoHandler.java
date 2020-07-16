package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//ErroDeValidacaoHandler -> ErroDeFormularioDto

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	@Autowired
	private MessageSource messageSource;
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {

		List<ErroDeFormularioDto> dto = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});
		return dto;
	}
}

//@RestControllerAdvice
//PARA INTERCPTAR UM CASO DE ERRO. @ControllerAdvice + @ResponseBody.
//@Autowired
//private MessageSource messageSource;
//BUSCAR MENSAGENS DE ERRO DE ACORDO COM IDIOMA QUE O CLIENTE REQUISITAR.
//@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//PADRÃO SERÁ DEVOLVIDO 200, MAS SERÁ MODIFICADO, ALTERANDO STATUS CODE. DEVOLVER 400.
//@ExceptionHandler(MethodArgumentNotValidException.class)
//MÉTODO DE EXCESSÃO.
//handle(MethodArgumentNotValidException exception)
//MÉTODO HANDLE PASSANDO COMO PARAMETRO A EXCEPTION.
//List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//RESULTADO DAS VALIDAÇÕES COM TODOS OS ERROS DE FORMULARIOS.
//String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
//DESCOBRIR O LOCAL DO ERRO DE ACORDO COM O IDIOMA REQUISITADO.
