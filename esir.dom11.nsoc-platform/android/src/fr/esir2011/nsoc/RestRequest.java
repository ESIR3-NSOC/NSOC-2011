package fr.esir2011.nsoc;

import org.restlet.resource.Get;


public interface RestRequest {

	@Get
	public String getRequest();
}
