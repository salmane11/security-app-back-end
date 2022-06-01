package ma.ac.inpt.security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ma.ac.inpt.security.models.Orders;
import ma.ac.inpt.security.repositories.OrderRepository;

@RestController
@RequestMapping("/security-api/v1/orders")
@CrossOrigin("*")
public class OrderController {
		@Autowired
		private OrderRepository orderRepository;

		@GetMapping
		public List<Orders> getAllOrders(){
			return orderRepository.findAll();
		}
		
		@GetMapping("/{id}")
		public Orders getOrderById(@PathVariable("id") Long id) {
			return orderRepository.findById(id).get();
		}
		
		@PostMapping
		@ResponseBody
		public ResponseEntity<HttpStatus> addOrder(@RequestBody Orders order){
			Orders newOrder=new Orders();
			if(order.getOrder_date()!=null) {
				newOrder.setOrder_date(order.getOrder_date());
			}
			if(order.getClient()!=null) {
				newOrder.setClient(order.getClient());
			}
			orderRepository.saveAndFlush(newOrder);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		
		@PutMapping("/{id}")
		@ResponseBody
		public ResponseEntity<HttpStatus> updateOrder(@PathVariable Long id,@RequestBody Orders order){
			Orders registredOrder=orderRepository.findById(id).get();
			if(order.getOrder_date()!=null) {
				registredOrder.setOrder_date(order.getOrder_date());
			}
			if(order.getClient()!=null) {
				registredOrder.setClient(order.getClient());
			}
			orderRepository.saveAndFlush(registredOrder);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		
		@DeleteMapping("/{id}")
		@ResponseBody
		public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id){
			orderRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
		}
		
		
}
