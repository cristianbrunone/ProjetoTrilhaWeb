package br.com.coldigogeladeiras.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import java.sql.ResultSet;
import java.sql.Statement;
import br.com.coldigogeladeiras.jdbcinterface.ProdutoDAO;
import br.com.coldigogeladeiras.modelo.Produto;

public class JDBCProdutoDAO implements ProdutoDAO {
	private Connection conexao;
	
	public JDBCProdutoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean inserir(Produto produto) {
		String comando = "INSERT INTO produtos "
				+ "(id, categoria, modelo, capacidade, valor, marcas_id) "
				+ "VALUES (?,?,?,?,?,?)";
		PreparedStatement p = null;
		
		try {
			// Prepara o comando para execução no BD em que nos conectamos
			p = this.conexao.prepareStatement(comando);
			
			// Substitui no comando os "?" pelos valores do produto
			p.setInt(1, produto.getId());
			p.setString(2, produto.getCategoria());
			p.setString(3, produto.getModelo());
			p.setInt(4, produto.getCapacidade());
			p.setFloat(5, produto.getValor());
			p.setInt(6, produto.getMarcaId());
			
			// Executa o comando no BD
			p.execute();
			
		} catch (SQLException e) {
			System.err.println("Erro ao inserir produto: " + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			// Fecha o PreparedStatement para liberar recursos
			try {
				if (p != null) {
					p.close();
				}
			} catch (SQLException e) {
				System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<JsonObject> buscarPorNome(String nome) {
		//Inicia criação do comando SQL de busca
		String comando = "SELECT produtos.*, marcas.nome as marca FROM produtos "
				+ "INNER JOIN marcas ON produtos.marcas_id = marcas.id ";
		//Se o nome não estiver vazio...
		if(!nome.equals("")) {
			//concatena no comando o WHERE buscando no nome do produto
			//o texto da variável nome 
			comando += "WHERE modelo LIKE '%" + nome + "%' ";
		}
		//Finaliza o comando ordenado alfabeticamente por
		//categoria, marca e depois modelo.
		comando += "ORDER BY categoria ASC, marcas.nome ASC, modelo ASC";
		
		List<JsonObject> listaProdutos = new ArrayList<JsonObject>();
		JsonObject produto = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String categoria = rs.getString("categoria");
				String modelo = rs.getString("modelo");
				int capacidade = rs.getInt("capacidade");
				float valor = rs.getFloat("valor");
				String marcaNome = rs.getString("marca");
				
				if(categoria.equals("1")) {
					categoria = "Geladeira";
				} else if (categoria.equals("2")) {
					categoria = "Freezer";
				}
				
				produto = new JsonObject();
				produto.addProperty("id", id);
				produto.addProperty("categoria", categoria);
				produto.addProperty("modelo", modelo);
				produto.addProperty("capacidade", capacidade);
				produto.addProperty("valor", valor);
				produto.addProperty("marcaNome", marcaNome);
				
				listaProdutos.add(produto);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return listaProdutos;
	}
	
}
