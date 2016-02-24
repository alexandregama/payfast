package com.payfast.endpoint.transaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.payfast.endpoint.payment.Payment;
import com.payfast.endpoint.user.User;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {

	@XmlElement(name = "id")
	private Long id;

	@XmlElement(name = "user")
	private User user;

	@XmlElement(name = "payment")
	private Payment payment;

	@Deprecated //JAX-B eyes only
	Transaction() {
	}
	
	public Transaction(Long id, User user, Payment payment) {
		this.id = id;
		this.user = user;
		this.payment = payment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
