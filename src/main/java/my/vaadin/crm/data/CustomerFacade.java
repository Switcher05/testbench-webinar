package my.vaadin.crm.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerFacade {

	@Autowired
	private CustomerRepository repository;

	public Customer save(Customer customer) {
		return repository.save(customer);
	}

	public List<Customer> list() {
		return repository.findAll();
	}

	public Customer get(Long id) {
		return repository.findOne(id);
	}
}
