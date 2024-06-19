COLDIGO.produto = new Object();

$(document).ready(function() {
	//carrega as marcas registradas no Bd no select do formulario de inserir
	COLDIGO.produto.carregarMarcas = function(){
		$.ajax({
			type: "GET",
			url: "/ProjetoTrilhaWeb/rest/marca/buscar",
			success: function () {
				
			},
			error: function () {
				
			}
		});
	}
});