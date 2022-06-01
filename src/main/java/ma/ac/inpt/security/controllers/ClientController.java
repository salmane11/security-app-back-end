package ma.ac.inpt.security.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ma.ac.inpt.security.models.Client;
import ma.ac.inpt.security.models.Orders;
import ma.ac.inpt.security.repositories.ClientRepository;
import ma.ac.inpt.security.repositories.OrderRepository;



@RestController
@RequestMapping("/security-api/v1/clients")
@CrossOrigin("*")
public class ClientController {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping
	public List<Client> getAllClients(){
		return clientRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Client getClientById(@PathVariable("id") Long id){
		return clientRepository.findById(id).get();
	}
	
	@GetMapping("/{id}/orders")
	public List<Orders> getClientOrders(@PathVariable("id") Long clientId){
		List<Orders> orders=orderRepository.findAll();
		List<Orders> clientOrders=new ArrayList<Orders>();
		for(int i=0;i<orders.size();i++) {
			if(orders.get(i).getClient().getClient_id()==clientId) {
				clientOrders.add(orders.get(i));
			}
		}
		return clientOrders;
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<HttpStatus> addClient(@RequestBody Client client) {
		Client newClient=new Client();
		if(client.getFirst_name()!=null) {
			newClient.setFirst_name(client.getFirst_name());
		}
		if(client.getLast_name()!=null) {
			newClient.setLast_name(client.getLast_name());
		}
		if(client.getAge()>0) {
			newClient.setAge(client.getAge());
		}
		
		clientRepository.saveAndFlush(newClient);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> updateClient(@PathVariable("id") Long id,@RequestBody Client client){
		Client registredClient=clientRepository.getById(id);
		if(registredClient==null) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}else {
			if(client.getAge()>0) {
				registredClient.setAge(client.getAge());
			}
			if(!client.getFirst_name().equals("")) {
				registredClient.setFirst_name(client.getFirst_name());
			}
			if(!client.getLast_name().equals("")) {
				registredClient.setLast_name(client.getLast_name());
			}
			//BeanUtils.copyProperties(client, registredClient,"client_id");
			clientRepository.saveAndFlush(registredClient);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") Long id){
		clientRepository.deleteById(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
	
	//Reception interface
	@GetMapping("/reception/{id}")
	public ResponseEntity<?> Reception(@PathVariable("id") Long id){
		return ResponseEntity.ok()
				.body(orderRepository.findAllClientOrders(id));
	}

}
