package my.vaadin.crm.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CustomerFacade {

	@Inject
	private CustomerRepository repository;

	public Customer save(Customer customer) {
		return repository.saveAndFlush(customer);
	}

	public List<Customer> list() {
		return repository.findAll();
	}

	public Customer get(Long id) {
		return repository.findBy(id);
	}
}
