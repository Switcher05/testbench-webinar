package my.vaadin.crm.ui;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;

import my.vaadin.crm.data.Customer;
import my.vaadin.crm.data.CustomerFacade;
import my.vaadin.crm.ui.event.AddCustomerEvent;
import my.vaadin.crm.ui.event.EditCustomerEvent;

@SpringView(name = MainView.VIEW_NAME)
public class MainView extends MainViewDesign implements View, Serializable {
	public static final String VIEW_NAME = "main";

	@Autowired
	private CustomerFacade customerFacade;

	@Autowired
	private CustomerGrid customerGrid;

	private void setFormVisible(boolean visible) {
		if (visible) {
			setSplitPosition(615, Unit.PIXELS, true);
		} else {
			setSplitPosition(100, Unit.PERCENTAGE);
		}
	}

	@PostConstruct
	private void init() {
		setFirstComponent(customerGrid);
		setFormVisible(false);
		customerGrid.refreshGrid(customerFacade.list());
		customerGrid.addListener(this::editCustomer);
		customerGrid.addListener(this::addCustomer);
	}

	private void showEditForm(String caption, Customer customer) throws CloneNotSupportedException {
		CustomerForm form = new CustomerForm(caption, customer, this::onSave, this::onCancel);
		setSecondComponent(form);
	}

	private void onSave(Customer customer) {
		try {
			customer = customerFacade.save(customer);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Stale object or something");
		}
		customerGrid.refreshGrid(customerFacade.list(), false);
		setFormVisible(false);
		customerGrid.scrollToCustomer(customer);
	}

	private void onCancel(Customer customer) {
		setFormVisible(false);
		customerGrid.deselectAll();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// do nothing
	}

	public void editCustomer(EditCustomerEvent event) {
		try {
			if (event.getCustomer() == null) {
				setFormVisible(false);
				return;
			}
			showEditForm("Edit Customer", event.getCustomer());
			setFormVisible(true);
		} catch (CloneNotSupportedException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to clone Customer", e);
		}
	}

	public void addCustomer(AddCustomerEvent event) {
		try {
			showEditForm("Add New Customer", new Customer());
			setFormVisible(true);
		} catch (CloneNotSupportedException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to clone Customer", e);
		}
	}

}
