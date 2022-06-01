package ma.ac.inpt.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ma.ac.inpt.security.models.Orders;
import ma.ac.inpt.security.repositories.ClientRepository;
import ma.ac.inpt.security.repositories.OrderRepository;

public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ClientRepository clientRepository;
	
	@Override
	public Orders addOrder(Orders order) {
		return orderRepository.save(order);
	}

	@Override
	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public Orders updateOrder(Long order_id, Orders order) {
		// TODO Auto-generated method stub
		Orders o = orderRepository.findById(order_id).get();
		o.setOrderDate(order.getOrderDate());
		o.setClient(order.getClient());
		return orderRepository.save(o);
	}

	@Override
	public void deleteOrder(Orders order) {
		// TODO Auto-generated method stub
		orderRepository.delete(order);
	}

	@Override
	public Orders serchOrderById(Long id) {
		// TODO Auto-generated method stub
		return orderRepository.findById(id).get();
	}

}
