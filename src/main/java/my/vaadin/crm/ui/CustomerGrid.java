package my.vaadin.crm.ui;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.shared.ui.grid.ScrollDestination;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;

import my.vaadin.crm.data.Customer;
import my.vaadin.crm.ui.event.AddCustomerEvent;
import my.vaadin.crm.ui.event.EditCustomerEvent;

@Dependent
public class CustomerGrid extends CustomerGridDesign {

	private static final String PROPERTY_EMAIL = "email";
	private static final String PROPERTY_FIRST_NAME = "firstName";
	private static final String PROPERTY_LAST_NAME = "lastName";
	private static final String PROPERTY_STATUS = "status.caption";

	@Inject
	private javax.enterprise.event.Event<EditCustomerEvent> editCustomerEvent;

	@Inject
	private javax.enterprise.event.Event<AddCustomerEvent> addCustomerEvent;

	private BeanItemContainer<Customer> container;

	public CustomerGrid() {
		grid.setColumns(PROPERTY_EMAIL, PROPERTY_FIRST_NAME, PROPERTY_LAST_NAME, PROPERTY_STATUS);
		grid.getColumn(PROPERTY_STATUS).setHeaderCaption("Status");
		grid.addSelectionListener(selectionListener);
		addButton.addClickListener(event -> addCustomerEvent.fire(new AddCustomerEvent(CustomerGrid.this)));

		filterField.focus();
		filterField.setTextChangeEventMode(TextChangeEventMode.LAZY);
		filterField.addTextChangeListener(event -> filter(event.getText()));
		clearFilterButton.addClickListener(event -> {
			filterField.clear();
			filter("");
		});
	}

	public void filter(String text) {
		if (container != null) {
			container.removeAllContainerFilters();
			if (!text.isEmpty()) {
				String searchTerm = "%" + text + "%";
				container.addContainerFilter(new Or(new Like(PROPERTY_EMAIL, searchTerm, false),
						new Like(PROPERTY_FIRST_NAME, searchTerm, false),
						new Like(PROPERTY_LAST_NAME, searchTerm, false), new Like(PROPERTY_STATUS, searchTerm, false)));
			}
		}
	}

	public void refreshGrid(List<Customer> customers) {
		refreshGrid(customers, true);
	}

	public void refreshGrid(List<Customer> customers, boolean keepFilterAndSelection) {
		container = new BeanItemContainer<>(Customer.class);
		container.addNestedContainerProperty(PROPERTY_STATUS);
		container.addAll(customers);
		grid.setContainerDataSource(container);

		if (!keepFilterAndSelection) {
			deselectAll();
			filterField.clear();
			container.removeAllContainerFilters();
		}
	}

	public Customer getSelectedRow() {
		return (Customer) grid.getSelectedRow();
	}

	public void deselectAll() {
		grid.removeSelectionListener(selectionListener);
		grid.deselectAll();
		grid.addSelectionListener(selectionListener);
	}

	public void scrollToCustomer(Customer customer) {
		grid.scrollTo(customer, ScrollDestination.END);
	}

	private final SelectionListener selectionListener = new SelectionListener() {

		@Override
		public void select(SelectionEvent event) {
			editCustomerEvent.fire(new EditCustomerEvent(CustomerGrid.this, (Customer) grid.getSelectedRow()));
		}
	};
}
