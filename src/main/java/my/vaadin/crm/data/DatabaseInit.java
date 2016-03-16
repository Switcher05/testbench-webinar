package my.vaadin.crm.data;

import java.time.LocalDate;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class DatabaseInit {

	@Autowired
	private CustomerRepository customerRepository;

	@PostConstruct
	private void initDatabaseIfEmpty() {
		if (!customerRepository.findAll().isEmpty()) {
			return;
		}

		customerRepository.save(createCustomer("joanna.doe@email.com", "Joanna", "Doe", CustomerStatus.NOT_CONTACTED,
				java.sql.Date.valueOf(LocalDate.of(1979, 1, 19))));
	}

	private static Customer createCustomer(String email, String firstName, String lastName, CustomerStatus status,
			Date birthday) {
		Customer customer = new Customer();
		customer.setEmail(email);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setStatus(status);
		customer.setBirthday(birthday);
		return customer;
	}
}
