package com.store.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.CurrentUserSession;
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

}
