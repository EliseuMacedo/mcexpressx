package com.mcexpress.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcexpress.domain.ItemPedido;
import com.mcexpress.domain.PagamentoComBoleto;
import com.mcexpress.domain.Pedido;
import com.mcexpress.domain.enums.EstadoPagamento;
import com.mcexpress.repositories.ItemPedidoRepository;
import com.mcexpress.repositories.PagamentoRepository;
import com.mcexpress.repositories.PedidoRepository;
import com.mcexpress.repositories.ProdutoRepository;
import com.mcexpress.services.exceptions.ObjectNotFountException;

//anotação de serviço do springboot
@Service
public class PedidoService {

	// declarar uma dependência de um objeto do tipo PedidoRepository
	// agora uma anotação para instanciar automaticamente o repositório, assim o
	// serviço acessa o objeto de acesso a dados que é o repository
	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);

		// Agora o meu método de serviço lança uma exceção caso o id não exista, porém o
		// rest tem
		// que capturar a exceção e enviar um JSON apropriado para a resposta HTTP do
		// meu recurso
		return obj.orElseThrow(() -> new ObjectNotFountException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		// tipo do objeto que trouxe essa exceção
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null); // Para garantir que o pedido seja novo
		obj.setInstante(new Date()); // nova data com instante atual
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		// Associação de mão dupla, o pagamento tem que conhecer o pedido
		obj.getPagamento().setPedido(obj);

		// O vencimento como não teremos um Web service que gera o boleto neste exemplo,
		// vamos jogar o vencimento para alguns dias a frente do pagamento
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();

			// vamos criar uma classe boleto service
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante()); // num cenário real esse preenchimento é
																				// feito pelo WebService
		}
		obj = repo.save(obj); // Salvo o Pedido
		pagamentoRepository.save(obj.getPagamento()); // Salvo o pto

		// Processo p/ salvar os itens do pedido - percorrendo todos os itens assiciados
		// ao pedido
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepository.findById(ip.getProduto().getId()).get().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		
		return obj;
	}
}


