package my.vaadin.crm.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Customer extends AbstractEntity implements Cloneable {
	private String firstName;
	private String lastName;
	private String email;

	@Enumerated(EnumType.STRING)
	private CustomerStatus status;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public Customer clone() throws CloneNotSupportedException {
		Customer customer = (Customer) super.clone();
		customer.birthday = birthday != null ? new Date(birthday.getTime()) : null;
		return customer;
	}

}
