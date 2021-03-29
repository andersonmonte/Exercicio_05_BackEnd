package br.com.tinnova.avaliacao;

/**
 * Classe utilizada para as operações de transferência de dados de Veículos,
 * mas que não possui todos os atributos da referida entidade, com o objetivo
 * de proteger certos dados que não devem ser alterados pelos usuários.
 */
public class VeiculoDTO {

	private String veiculo;
	
	private Marcas marca;
	
	private Integer ano;
	
	private String descricao;
	
	private Boolean vendido;

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public Marcas getMarca() {
		return marca;
	}

	public void setMarca(Marcas marca) {
		this.marca = marca;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getVendido() {
		return vendido;
	}

	public void setVendido(Boolean vendido) {
		this.vendido = vendido;
	}
	
	
}
