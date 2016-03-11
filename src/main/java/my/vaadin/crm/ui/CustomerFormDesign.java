package my.vaadin.crm.ui;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { … }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class CustomerFormDesign extends VerticalLayout {
	protected CssLayout formWrapper;
	protected TextField email;
	protected ComboBox status;
	protected TextField firstName;
	protected TextField lastName;
	protected PopupDateField birthday;
	protected Button cancelButton;
	protected Button saveButton;

	public CustomerFormDesign() {
		Design.read(this);
	}
}
