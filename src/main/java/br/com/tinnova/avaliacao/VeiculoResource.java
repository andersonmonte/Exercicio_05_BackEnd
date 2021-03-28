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

@Path("veiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VeiculoResource {

	@GET
	public List<Veiculo> buscarTodosVeiculos() {
		return Veiculo.listAll();
	}

	@GET
	@Path("find")
	public List<Veiculo> buscarVeiculosPorParametro(@QueryParam("param") String parametro) {
		List<Veiculo> listaResultado = new ArrayList<Veiculo>();
		
		listaResultado.addAll(Veiculo.find("veiculo like concat('%', :veiculo, '%')", 
				Parameters.with("veiculo", parametro)).list());
		
		if (buscarMarcaEnum(parametro) != null) {
			listaResultado.addAll(Veiculo.find("marca", buscarMarcaEnum(parametro)).list());
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
	
	@GET
	@Path("{id}")
	public Veiculo buscarVeiculoPorId(@PathParam("id") Long id) {
		return Veiculo.findById(id);
	}
	
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
	
	@DELETE
	@Path("{id}")
	@Transactional
	public void excluirVeiculo(@PathParam("id") Long id) {
		
		Optional<Veiculo> veiculoOptional = Veiculo.findByIdOptional(id);
		
		veiculoOptional.ifPresentOrElse(Veiculo::delete, () -> {
			throw new NotFoundException();
		});
	}
	
	public static Marcas buscarMarcaEnum(String nome){
	    for(Marcas m : Marcas.values()){
	        if( m.toString().toUpperCase().equals(nome.toUpperCase())){
	            return m;
	        }
	    }
	    return null;
	}
}
