package com.store.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.CurrentUserSession;
import com.store.enums.OrderStatus;
import com.store.exception.AddressException;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.exception.OrdersException;
import com.store.exception.ProductException;
import com.store.module.Address;
import com.store.module.Cart;
import com.store.module.Customer;
import com.store.module.Orders;
import com.store.module.Product;
import com.store.repository.AddressRepo;
import com.store.repository.CartRepo;
import com.store.repository.CurrentUserSessionRepo;
import com.store.repository.CustomerRepo;
import com.store.repository.OrdersRepo;
import com.store.repository.ProductRepo;

@Service
public class OrdersServicesImpl implements OrdersServices{
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CustomerRepo customerrepo;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private CurrentUserSessionRepo currentUserSessionRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private OrdersRepo ordersRepo;
	
	@Override
	public Orders placeOrder(Orders orders,Integer addressId,String key) throws ProductException, AddressException,CustomerException,OrdersException,LoginException {
		Optional<Orders> opt = ordersRepo.findById(orders.getOrderId());
		if(opt.isPresent()) {
			throw new OrdersException("Order with this order ID already placed => "+orders.getOrderId());
		}
		Optional<Address> aopt = addressRepo.findById(addressId);
		if(aopt.isEmpty()) {
			throw new AddressException("No address found with this address ID => "+addressId);
		}
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Only customer can order products please log in as customer ");
		}
		
		Optional<Customer> copt = customerrepo.findById(loggedInUser.getUserId());
		if(copt.isEmpty()) {
			throw new CustomerException("No customer data found with this ID ");
		}
			Customer customer = copt.get();
			Cart cart = customer.getCart();
			Map<String,Integer> products = cart.getProducts();
			Address adr = new Address();
			if(customer.getAddresses().contains(aopt.get())) {
				adr = aopt.get();
			}else {
				throw new AddressException("You don't have any address with this address ID  => "+addressId);
			}
			
			if(products.isEmpty()) {
				throw new ProductException("No product found ");
			}else {
				orders.setOrderedProducts(products);
				orders.setDeliveryDate(orders.getOrderDate().plusDays(7));
				orders.setCustomer(customer);
				orders.setDeliveryAddress(adr);
				for(String i:orders.getOrderedProducts().keySet()) {
					Product p = productRepo.findByProductName(i);
					p.setQuantity(p.getQuantity()-orders.getOrderedProducts().get(i));
					productRepo.save(p);
				}
				cart.setProducts(null);
				cartRepo.save(cart);
				return ordersRepo.save(orders);	
			}
	}
	

	@Override
	public Orders cancelOrder(Integer orderId, String key)
			throws ProductException, OrdersException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Only customer can cancel the order please log in as customer ");
		}
		
		Optional<Customer> copt = customerrepo.findById(loggedInUser.getUserId());
		if(copt.isEmpty()) {
			throw new CustomerException("No customer data found with this ID ");
		}
			Customer customer = copt.get();
		
		Optional<Orders> oopt = ordersRepo.findById(orderId);
		if(oopt.isEmpty()) {
			throw new OrdersException("No Order found with this order ID => "+orderId);
		}
		
		Orders orders = oopt.get();
		
		for(String i:orders.getOrderedProducts().keySet()) {
			Product p = productRepo.findByProductName(i);
			p.setQuantity(p.getQuantity()+orders.getOrderedProducts().get(i));
			productRepo.save(p);
		}
		
		ordersRepo.delete(orders);
		
		return orders;
	}


	@Override
	public List<Orders> getAllOrders(String key) throws OrdersException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (!loggedInUser.getAdmin()) {
			throw new CustomerException("Only admin can access all order details by all customers ");
		}
		List<Orders> allorders = ordersRepo.findAll();
		if(allorders.isEmpty()) {
			throw new OrdersException("No order found ");
		}else {
			return allorders;
		}
	}


	@Override
	public List<Orders> getAllOrdersByCustomer(String key) throws OrdersException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as customer to get all order details ");
		}
		List<Orders> allorders = ordersRepo.findBycustomerId(loggedInUser.getUserId());
		if(allorders.isEmpty()) {
			throw new OrdersException("No order found ");
		}else {
			return allorders;
		}
	}


	@Override
	public Orders UpdateDeliveryAddress(Integer orderId, Integer addressId, String key)
			throws OrdersException, CustomerException, LoginException, AddressException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as customer to update delivery address ");
		}
		Optional<Address> aopt = addressRepo.findById(addressId);
		if(aopt.isEmpty()) {
			throw new AddressException("No address found with this address ID => "+addressId);
		}
		List<Orders> allorders = ordersRepo.findBycustomerId(loggedInUser.getUserId());
		if(allorders.isEmpty()) {
			throw new OrdersException("No order found ");
		}else {
			Optional<Orders> oopt  = ordersRepo.findById(orderId);
			if(oopt.isEmpty()) {
				throw new OrdersException("No order found with this order id");
			}else {
				Orders order = oopt.get();
				if(allorders.contains(order)) {
					Optional<Customer> copt = customerrepo.findById(order.getCustomer().getCustomerId());
					if(copt.isEmpty()) {
						throw new CustomerException("No customer data found with this ID ");
					}
					Customer customer = copt.get();
					Address adr = new Address();
					if(customer.getAddresses().contains(aopt.get())) {
						adr = aopt.get();
					}else {
						throw new AddressException("You don't have any address with this address ID  => "+addressId);
					}
					order.setDeliveryAddress(adr);
					ordersRepo.save(order);
					return order;
				}else {
					throw new OrdersException("No order found with this order id");
				}
			}
		}
	}


	@Override
	public Orders UpdateDeliveryStatus(Integer orderId, String status, String key)
			throws OrdersException, CustomerException, LoginException, AddressException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (!loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as admin to update delivery status ");
		}
		OrderStatus orderStatus = null ;

		try {
			orderStatus = OrderStatus.valueOf(status);
		} catch (Exception e) {
			throw new OrdersException("Order status can only be NotShipped or Shipped or Delivered");
		}
		Optional<Orders> oopt  = ordersRepo.findById(orderId);
		if(oopt.isEmpty()) {
			throw new OrdersException("No order found with this order id");
		}else {
			Orders order = oopt.get();
			order.setOrderStatus(orderStatus.getOrderStatus());
			ordersRepo.save(order);
			return order;
		}
	}


	@Override
	public Orders UpdateDeliveryDate(LocalDate date, Integer orderId, String key)
			throws OrdersException, CustomerException, LoginException, AddressException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as customer to update delivery date ");
		}
		Customer customer = customerrepo.findById(loggedInUser.getUserId()).get();
		
		Optional<Orders> oopt = ordersRepo.findById(orderId);
		if(oopt.isEmpty()) {
			throw new OrdersException("No order found with this order ID ");
		}else {
			Orders order = oopt.get();
			List <Orders> orders = ordersRepo.findBycustomerId(customer.getCustomerId());
			if(orders.contains(order)) {
				if(date.isAfter(order.getDeliveryDate())) {
					order.setDeliveryDate(date);
					return ordersRepo.save(order);
				}else {
					throw new OrdersException("Order can't be delivered before "+order.getDeliveryDate());
				}
			}else {
				throw new OrdersException("Loged-in customer does not have any order with this order ID ");
			}
		}
	}


	@Override
	public List<Orders> UpdateDeliveryStatusByOrderdate(LocalDate date, String status, String key)
			throws OrdersException, CustomerException, LoginException, AddressException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (!loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as admin to update delivery status ");
		}
		OrderStatus orderStatus = null ;

		try {
			orderStatus = OrderStatus.valueOf(status);
		} catch (Exception e) {
			throw new OrdersException("Order status can only be NotShipped or Shipped or Delivered");
		}
		List<Orders> oopt  = ordersRepo.findByOrderDate(date);
		if(oopt.isEmpty()) {
			throw new OrdersException("No order found for this order date => "+date);
		}else {
			for(Orders i:oopt) {
				i.setOrderStatus(orderStatus.getOrderStatus());
				ordersRepo.save(i);
			}
			return oopt;
		}
	}


	@Override
	public List<Orders> cancelOrdersByOrderDate(LocalDate date, String key)
			throws ProductException, OrdersException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (!loggedInUser.getAdmin()) {
			throw new CustomerException("Only admin can cancel the all the orders Placed on particular date  ");
		}
		
		List<Orders> orders = ordersRepo.findByOrderDate(date);
		if(orders.isEmpty()) {
			throw new OrdersException("No Order found with on this date => "+date);
		}
		
		for(Orders j:orders) {
		
		for(String i:j.getOrderedProducts().keySet()) {
			Product p = productRepo.findByProductName(i);
			p.setQuantity(p.getQuantity()+j.getOrderedProducts().get(i));
			productRepo.save(p);
		}
		
		ordersRepo.delete(j);
		}
		return orders;
	}

}
