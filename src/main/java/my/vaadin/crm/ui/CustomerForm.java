package my.vaadin.crm.ui;

import java.util.Optional;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;

import my.vaadin.crm.data.Customer;
import my.vaadin.crm.data.CustomerStatus;
import my.vaadin.crm.ui.event.CancelListener;
import my.vaadin.crm.ui.event.SaveListener;

public class CustomerForm extends CustomerFormDesign {

	private BeanFieldGroup<Customer> fieldGroup;
	private final Customer original;
	private final Optional<SaveListener<Customer>> saveListener;
	private final Optional<CancelListener<Customer>> cancelListener;

	public CustomerForm(String caption, Customer customer, SaveListener<Customer> saveListener,
			CancelListener<Customer> cancelListener) throws CloneNotSupportedException {

		this.saveListener = Optional.ofNullable(saveListener);
		this.cancelListener = Optional.ofNullable(cancelListener);
		original = customer;
		formWrapper.setCaption(caption);
		for (CustomerStatus customerStatus : CustomerStatus.values()) {
			status.addItem(customerStatus);
			status.setItemCaption(customerStatus, customerStatus.getCaption());
		}
		status.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);
		fieldGroup = BeanFieldGroup.bindFieldsUnbuffered(customer.clone(), this);
		saveButton.addClickListener(event -> commit());
		cancelButton.addClickListener(event -> cancel());
	}

	private void commit() {
		if (fieldGroup.isValid()) {
			saveListener.ifPresent(listener -> listener.accept(fieldGroup.getItemDataSource().getBean()));
		}
	}

	private void cancel() {
		cancelListener.ifPresent(listener -> listener.accept(original));
	}
}
