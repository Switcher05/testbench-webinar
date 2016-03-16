package my.vaadin.crm.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	public List<Customer> findAll();
}
