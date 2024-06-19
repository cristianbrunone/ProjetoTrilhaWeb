package br.com.coldigogeladeiras.jdbc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import br.com.coldigogeladeiras.jdbcinterface.MarcaDAO;
import br.com.coldigogeladeiras.modelo.Marca;

public class JDBCMarcaDAO implements MarcaDAO {
	private Connection conexao;
	
	public JDBCMarcaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public List<Marca> buscar() {
		//Criação da instrução SQl para busca de todas as marcas
		String comando = "SELECT * FROM marcas";
		
		//Criação de uma lista para armazenar cada marca encontrada
		List<Marca> listMarcas = new ArrayList<Marca>();
		
		//Criação de objeto marca com valor null (ou seja, sem instancia-lo)
		Marca marca = null;
		
	
		
		
		try {
			
			
			//Uso da conexao do banco para prepara=lo para uma instrução SQL
			java.sql.Statement stmt = conexao.createStatement();
			
			
			//Execuçao da instrucao criada previamente 
			// e armazenamento do resultado no objeto rs
			
			
			ResultSet rs = stmt.executeQuery(comando);
			
			
			
			//Enquanto houver uma proxima linha no resultado
			while (rs.next()) {
				//Criação de instancia da classe Marca
				marca = new Marca();
				
				
				//Recibimento dos 2 dados retornados no BD para cada linha encontrada
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				
				
				//Sentado no objeto marca os valores encontrados
				marca.setId(id);
				marca.setNome(nome);
				
				//Adicao da instancia contida no objeto Marca na lista de marcas
				listMarcas.add(marca);
			}
		//Caso alguma exception seja gerada no try, recebe-a no objeto "ex"	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//Retorna para quem chamou o metodo a lista criada
		return listMarcas;
	}
}
