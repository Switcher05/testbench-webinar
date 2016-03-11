package my.vaadin.crm.ui.event;

import java.util.EventObject;

import my.vaadin.crm.data.Customer;

public class EditCustomerEvent extends EventObject {

	private final Customer customer;

	public EditCustomerEvent(Object source, Customer customer) {
		super(source);
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

}
