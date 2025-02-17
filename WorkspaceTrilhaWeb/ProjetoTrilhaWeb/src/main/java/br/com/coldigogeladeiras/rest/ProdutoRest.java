package br.com.coldigogeladeiras.rest;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;

import br.com.coldigogeladeiras.bd.Conexao;
import br.com.coldigogeladeiras.jdbc.JDBCProdutoDAO;
import br.com.coldigogeladeiras.modelo.Produto;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;





@Path("produto")
public class ProdutoRest extends UtilRest {

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String produtoParam) {
		try {
			System.out.println("JSON recebido: " + produtoParam);
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);
			System.out.println("Objeto Produto criado: " + produto);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			boolean retorno = jdbcProduto.inserir(produto);
			
			String msg ="";
			
			if(retorno) {
				msg= "Produto cadastrado com sucesso!";
			} else {
				msg= "Erro ao cadastrar produto";
			}
			
			conec.fecharConexao();
			
			System.out.println("Mensagem retornada para o cliente: " + msg);

			
			return this.buildResponse(msg);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String nome) {
		
		try {
			List<JsonObject> listaProdutos = new ArrayList<JsonObject>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			listaProdutos = jdbcProduto.buscarPorNome(nome);
			conec.fecharConexao();
			
			String json = new Gson().toJson(listaProdutos);
			return this.buildResponse(json);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	
}
