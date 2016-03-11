package my.vaadin.crm.ui;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import my.vaadin.crm.data.Customer;
import my.vaadin.crm.data.CustomerFacade;
import my.vaadin.crm.ui.event.AddCustomerEvent;
import my.vaadin.crm.ui.event.EditCustomerEvent;

@CDIView(MainView.VIEW_NAME)
public class MainView extends MainViewDesign implements View, Serializable {
	public static final String VIEW_NAME = "main";

	@Inject
	private CustomerFacade customerFacade;

	@Inject
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
	}

	private void showEditForm(String caption, Customer customer) throws CloneNotSupportedException {
		CustomerForm form = new CustomerForm(caption, customer, this::onSave, this::onCancel);
		setSecondComponent(form);
	}

	private void onSave(Customer customer) {
		try {
			customer = customerFacade.save(customer);
		} catch (EJBException e) {
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

	public void editCustomer(@Observes EditCustomerEvent event) {
		try {
			if (event.getCustomer() == null) {
				setFormVisible(false);
			}
			showEditForm("Edit Customer", event.getCustomer());
			setFormVisible(true);
		} catch (CloneNotSupportedException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to clone Customer", e);
		}
	}

	public void addCustomer(@Observes AddCustomerEvent event) {
		try {
			showEditForm("Add New Customer", new Customer());
			setFormVisible(true);
		} catch (CloneNotSupportedException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to clone Customer", e);
		}
	}

}
