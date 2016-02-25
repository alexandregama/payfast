package com.payfast.endpoint.link;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

	@XmlElement(name = "rel", required = true)
	private String rel;

	@XmlElement(name = "uri", required = true)
	private String uri;

	@XmlElement(name = "method", required = true)
	private String method;

	@Deprecated //JAX-B eyes only
	Link() {
	}
	
	public Link(String rel, String uri, String method) {
		this.rel = rel;
		this.uri = uri;
		this.method = method;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "Link [rel=" + rel + ", uri=" + uri + ", method=" + method + "]";
	}

}
