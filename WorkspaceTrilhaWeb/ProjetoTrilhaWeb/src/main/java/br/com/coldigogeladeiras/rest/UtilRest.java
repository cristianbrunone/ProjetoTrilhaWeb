package br.com.coldigogeladeiras.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.google.gson.Gson;

public class UtilRest {

    public Response buildResponse(Object result) {
        try {
            /*
             * Retorna o objeto de resposta com status 200(ok), tendo
             * em seu corpo o objeto valorRespota (que consiste no 
             * objeto result convertido para JSON
             */
            String valorResposta = new Gson().toJson(result);
            return Response.ok(valorResposta).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Se algo der errado acima, cria Response de erro
            return this.buildErrorResponse(ex.getMessage());
        }	
    }
    
    /*
     * Abaixo o metodo responsavel por enviar a resposta ao cliente sobre
     *  a transação realizada, inclusão, consulta, deição ou exclusão
     *  cliente, não realizadas com sucesso, ou seja, que contenha algum
     *  erro.
     *  Repare que o metodo em questão aguarda que seja repassado um 
     *  conteudo que sera referenciado pelo por um objeto chamado rb.
     */
    
    public Response buildErrorResponse(String str) {
    	/*
    	 * Abaixo o objeto rb recebe o status do erro.
    	 */
    	ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
    
    
    	/*
    	 * Define a entidade (objeto), que nesse caso é uma 
    	 * mensagem que será retornado para o cliente
    	 * 
    	 */
    	rb = rb.entity(str);
    	/*
    	 * Define a entidade (objeto), que nesse caso é uma
    	 * mensagem que será retornado para o cliente.
    	 */
    	rb = rb.type("text/plain");
    	
    	/*
    	 *Retorna o objeto de resposta com status 500(erro),
    	 *junto com a String contendo a mensagem de erro. 
    	 */
    	return rb.build();
    	
    } 	
}
