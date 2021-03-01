package com.lucaslearning.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucaslearning.cursomc.domain.Cliente;
import com.lucaslearning.cursomc.domain.ItemPedido;
import com.lucaslearning.cursomc.domain.PagamentoComBoleto;
import com.lucaslearning.cursomc.domain.Pedido;
import com.lucaslearning.cursomc.domain.enums.EstadoPagamento;
import com.lucaslearning.cursomc.repositories.ItemPedidoRepository;
import com.lucaslearning.cursomc.repositories.PagamentoRepository;
import com.lucaslearning.cursomc.repositories.PedidoRepository;
import com.lucaslearning.cursomc.security.UserSS;
import com.lucaslearning.cursomc.services.exceptions.AuthorizationException;
import com.lucaslearning.cursomc.services.exceptions.ObjectNotFoundException;


@Service 
public class PedidoService {
	
	@Autowired //injecao de dependencia, vai ser automaticamente instanciada pelo spring
	private PedidoRepository repository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstant());
			
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repository.findByCliente(cliente, pageRequest); 
	}
}
