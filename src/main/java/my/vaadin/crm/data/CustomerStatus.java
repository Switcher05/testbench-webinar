package my.vaadin.crm.data;

public enum CustomerStatus {
	NOT_CONTACTED("Not Contacted"), CONTACTED("Contacted"), PROSPECT("Prospect"), CUSTOMER("Customer"), CLOSED_LOST(
			"Closed Lost");

	private final String caption;

	private CustomerStatus(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}
}
