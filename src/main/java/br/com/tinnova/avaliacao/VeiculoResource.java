package br.com.tinnova.avaliacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.quarkus.panache.common.Parameters;

/**
 * Classe que possui os métodos de acesso aos recursos de Veiculo.
 */
@Path("veiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VeiculoResource {

	/**
	 * Obtém todos os veículos cadastrados na base.
	 * 
	 * @return Lista de veículos - List<Veiculo>
	 */
	@GET
	public List<Veiculo> buscarTodosVeiculos() {
		return Veiculo.listAll();
	}
	
	/**
	 * Obtém veículos de acordo com o parâmetro de busca passado.
	 * A correspondência do parâmetro é buscada em todos os campos
	 * da entidade Veículo.
	 * 
	 *  @param parametro - String
	 * 
	 * @return Lista de veículos - List<Veiculo>
	 */
	@GET
	@Path("find")
	public List<Veiculo> buscarVeiculosPorParametro(@QueryParam("param") String parametro) {
		List<Veiculo> listaResultado = new ArrayList<Veiculo>();
		
		listaResultado.addAll(Veiculo.find("veiculo like concat('%', :veiculo, '%')", 
				Parameters.with("veiculo", parametro)).list());
		
		if (Marcas.buscarMarcaEnum(parametro) != null) {
			listaResultado.addAll(Veiculo.find("marca", Marcas.buscarMarcaEnum(parametro)).list());
		}
		
		try {
			listaResultado.addAll(Veiculo.find("ano", Integer.parseInt(parametro)).list());
		} catch (NumberFormatException e) {
		}
		
		listaResultado.addAll(Veiculo.find("descricao like concat('%', :descricao, '%')", 
				Parameters.with("descricao", parametro)).list());
		
		if (parametro.equalsIgnoreCase("true") || parametro.equalsIgnoreCase("false")) {
			listaResultado.addAll(Veiculo.find("vendido", Boolean.parseBoolean(parametro)).list());
		}
		
		return listaResultado;
	}
	
	/**
	 * Obtém os dados de um Veículo em particular através do seu id.
	 * 
	 *  @param id - identificador do Veículo
	 * 
	 * @return Veiculo
	 */
	@GET
	@Path("{id}")
	public Veiculo buscarVeiculoPorId(@PathParam("id") Long id) {
		return Veiculo.findById(id);
	}
	
	/**
	 * Cadastra um novo Veículo.
	 * 
	 *  @param veiculoDTO - Veiculo a ser persistido
	 */
	@POST
	@Transactional
	public void cadastrarVeiculo(VeiculoDTO veiculoDTO) {
		Veiculo novoVeiculo = new Veiculo();
		novoVeiculo.setVeiculo(veiculoDTO.getVeiculo());
		novoVeiculo.setMarca(veiculoDTO.getMarca());
		novoVeiculo.setAno(veiculoDTO.getAno());
		novoVeiculo.setDescricao(veiculoDTO.getDescricao());
		novoVeiculo.setVendido(veiculoDTO.getVendido());
		novoVeiculo.persist();
	}
	
	/**
	 * Atualiza todos os dados de um veículo.
	 * 
	 *  @param veiculoDTO - Veiculo a ser atualizado
	 *  @param id - identificador do Veiculo
	 *  
	 */
	@PUT
	@Path("{id}")
	@Transactional
	public void atualizarVeiculoIntegral(@PathParam("id") Long id, VeiculoDTO veiculoDTO) {
		
		Optional<Veiculo> veiculoOptional = Veiculo.findByIdOptional(id);
		
		if (veiculoOptional.isPresent()) {
			Veiculo veiculo = veiculoOptional.get();
			veiculo.setVeiculo(veiculoDTO.getVeiculo());
			veiculo.setMarca(veiculoDTO.getMarca());
			veiculo.setAno(veiculoDTO.getAno());
			veiculo.setDescricao(veiculoDTO.getDescricao());
			veiculo.setVendido(veiculoDTO.getVendido());
			veiculo.persist();
		} else {
			throw new NotFoundException();
		}
	}
	
	/**
	 * Atualiza apenas os dados de um veículo que foram
	 * solicitados pelo cliente.
	 * 
	 *  @param veiculoDTO - Veiculo a ser atualizado
	 *  @param id - identificador do Veiculo
	 *  
	 */
	@PATCH
	@Path("{id}")
	@Transactional
	public void atualizarVeiculoParcial(@PathParam("id") Long id, VeiculoDTO veiculoDTO) {
		
		Optional<Veiculo> veiculoOptional = Veiculo.findByIdOptional(id);
		
		if (veiculoOptional.isPresent()) {
			Veiculo veiculo = veiculoOptional.get();
			
			if (veiculoDTO.getVeiculo() != null) {
				veiculo.setVeiculo(veiculoDTO.getVeiculo());
			}
			
			if (veiculoDTO.getMarca() != null) {
				veiculo.setMarca(veiculoDTO.getMarca());
			}

			if (veiculoDTO.getAno() != null) {
				veiculo.setAno(veiculoDTO.getAno());
			}
			
			if (veiculoDTO.getDescricao() != null) {
				veiculo.setDescricao(veiculoDTO.getDescricao());
			}
			
			if (veiculoDTO.getVendido() != null) {
				veiculo.setVendido(veiculoDTO.getVendido());
			}
			
			veiculo.persist();
		} else {
			throw new NotFoundException();
		}
	}
	/**
	 * Exclui o veículo da base de dados.
	 * 
	 *  @param id - identificador do Veiculo
	 *  
	 */
	@DELETE
	@Path("{id}")
	@Transactional
	public void excluirVeiculo(@PathParam("id") Long id) {
		
		Optional<Veiculo> veiculoOptional = Veiculo.findByIdOptional(id);
		
		veiculoOptional.ifPresentOrElse(Veiculo::delete, () -> {
			throw new NotFoundException();
		});
	}
	

}
