package br.com.tinnova.avaliacao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("veiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VeiculoResource {

	@GET
	public List<Veiculo> buscarTodosVeiculos() {
		return Veiculo.listAll();
	}
	
	@POST
	@Transactional
	public void cadastrarVeiculo(CadastrarVeiculoDTO cadastrarVeiculoDTO) {
		Veiculo novoVeiculo = new Veiculo();
		novoVeiculo.setVeiculo(cadastrarVeiculoDTO.getVeiculo());
		novoVeiculo.setMarca(cadastrarVeiculoDTO.getMarca());
		novoVeiculo.setAno(cadastrarVeiculoDTO.getAno());
		novoVeiculo.setDescricao(cadastrarVeiculoDTO.getDescricao());
		novoVeiculo.setVendido(cadastrarVeiculoDTO.getVendido());
		novoVeiculo.persist();
	}
	
	@PUT
	@Path("{id}")
	@Transactional
	public void atualizarVeiculo(@PathParam("id") Long id, CadastrarVeiculoDTO cadastrarVeiculoDTO) {
		
		Optional<Veiculo> veiculoOptional = Veiculo.findByIdOptional(id);
		
		if (veiculoOptional.isPresent()) {
			Veiculo veiculo = veiculoOptional.get();
			veiculo.setVeiculo(cadastrarVeiculoDTO.getVeiculo());
			veiculo.setMarca(cadastrarVeiculoDTO.getMarca());
			veiculo.setAno(cadastrarVeiculoDTO.getAno());
			veiculo.setDescricao(cadastrarVeiculoDTO.getDescricao());
			veiculo.setVendido(cadastrarVeiculoDTO.getVendido());
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
}
