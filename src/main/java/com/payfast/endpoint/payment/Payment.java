package com.payfast.endpoint.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.payfast.endpoint.link.Link;
import com.payfast.endpoint.link.Links;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Payment {

	@XmlElement(name = "id")
	private Long id;

	@XmlElement(name = "status")
	private String status;
	
	@XmlElement(name = "value")
	private Double value;
	
	@XmlElement(name = "links")
	private Links links = new Links();
	
	@Deprecated //We must create a no-arg default constructor
	Payment() {
	}
	
	public Payment(Long id, String status) {
		this.id = id;
		this.status = status;
		this.status = "STARTED";
	}
	
	public void confirm() {
		this.status = "CONFIRMED";
	}
	
	public void cancel() {
		this.status = "CANCELED";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void started() {
		this.status = "STARTED";
		Link confirm = new Link("confirm", PaymentResource.PAYMENT_RESOURCE_URI + this.id, "PUT");		
		Link delete = new Link("cancel", PaymentResource.PAYMENT_RESOURCE_URI + this.id, "DELETE");
		links.add(confirm);
		links.add(delete);
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", status=" + status + ", value=" + value + ", links=" + links + "]";
	}

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}
	
}
