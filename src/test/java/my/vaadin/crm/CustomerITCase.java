package my.vaadin.crm;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.ComboBoxElement;
import com.vaadin.testbench.elements.GridElement;
import com.vaadin.testbench.elements.GridElement.GridCellElement;
import com.vaadin.testbench.elements.TextFieldElement;

import my.vaadin.crm.data.CustomerStatus;

public class CustomerITCase extends TestBenchTestCase {

	@Before
	public void setup() {
		setDriver(DriverUtil.getPreferredDriver());
		getDriver().get("http://localhost:8080/crm");
	}

	@After
	public void teardown() {
		getDriver().quit();
	}

	@Test
	public void addNewCustomer_RequiredInformationGiven_CustomerGridShouldListAddedCustomer()
			throws InterruptedException {
		final String email = "a.b@email.com";

		CustomerGridPageObject customerGrid = new CustomerGridPageObject(getDriver());
		customerGrid.searchBy(email);
		Thread.sleep(500);

		Assert.assertTrue(customerGrid.isEmpty());
		customerGrid.clearSearch();

		ButtonElement addNewCustomerButton = $(ButtonElement.class).caption("Add New Customer").first();
		addNewCustomerButton.click();

		CustomerFormPageObject customerForm = new CustomerFormPageObject(getDriver());
		customerForm.enterEmail(email);
		customerForm.selectStatus(CustomerStatus.NOT_CONTACTED);
		customerForm.save();

		customerGrid.searchBy(email);
		Thread.sleep(500);
		Assert.assertFalse(customerGrid.isEmpty());
		Assert.assertEquals(email, customerGrid.getGridCell(0, 0));

	}

	public static class CustomerFormPageObject extends MyPageObject {

		public CustomerFormPageObject(WebDriver driver) {
			super(driver, "customerForm");
		}

		public void selectStatus(CustomerStatus value) {
			ComboBoxElement status = $(ComboBoxElement.class).id("status");
			status.selectByText(value.getCaption());
		}

		public void enterEmail(String value) {
			TextFieldElement email = $(TextFieldElement.class).id("email");
			email.sendKeys(value);
		}

		public void save() {
			ButtonElement save = $(ButtonElement.class).id("saveButton");
			save.click();
		}

	}

	public static class CustomerGridPageObject extends MyPageObject {

		public CustomerGridPageObject(WebDriver driver) {
			super(driver, "customerGrid");
		}

		public void clearSearch() {
			$(ButtonElement.class).id("clearFilterButton").click();
		}

		public boolean isEmpty() {
			GridElement grid = $(GridElement.class).first();
			try {
				grid.getRow(0);
				return false;
			} catch (NoSuchElementException e) {
				return true;
			}
		}

		public void searchBy(String str) {
			$(TextFieldElement.class).first().sendKeys(str);
			testBench().waitForVaadin();
		}

		public String getGridCell(int row, int col) {
			try {
				GridCellElement cell = $(GridElement.class).first().getCell(row, col);
				return cell.getText();
			} catch (NoSuchElementException e) {
				return null;
			}
		}
	}

	public static abstract class MyPageObject extends TestBenchTestCase {
		private final SearchContext context;

		public MyPageObject(WebDriver driver, SearchContext context) {
			setDriver(driver);
			this.context = context;
		}

		public MyPageObject(WebDriver driver, String contextId) {
			this(driver, driver.findElement(By.id(contextId)));
		}

		@Override
		public SearchContext getContext() {
			return context;
		}
	}
}
