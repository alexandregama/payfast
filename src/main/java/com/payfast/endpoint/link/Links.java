package com.payfast.endpoint.link;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Links {

	@XmlElement(name = "link")
	private List<Link> link = new ArrayList<>();
	
	public Links() {
	}

	public void add(Link newlink) {
		link.add(newlink);
	}
	
	public List<Link> getLink() {
		return link;
	}

	public void setLink(List<Link> link) {
		this.link = link;
	}
	
}
