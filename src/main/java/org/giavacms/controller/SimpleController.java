package org.giavacms.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class SimpleController {

	static String WITH_REDIRECT = "?faces-redirect=true";

	private String name = "ciao ciao ";

	public void log() {
		System.out.println("LOG: " + name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String action() {
		if (this.name != null && !this.name.isEmpty()
				&& this.name.equals("CIAO")) {
			// FUNZIONA
			return "/cache/ciao" + WITH_REDIRECT;
		} else if (this.name != null && !this.name.isEmpty()
				&& this.name.equals("CIAO_S")) {
			// NON FUNZIONA!
			return "/prova/dove/ci/ciao" + WITH_REDIRECT;
		} else {
			FacesContext.getCurrentInstance().addMessage("NON SO BENE",
					new FacesMessage("BOHHH"));
		}
		return null;
	}

	public String goToCiao() {
		return "/cache/ciao";
	}

	public String goToIndex() {
		return "/cache/index";
	}

	public String goToCiaoRedirect() {
		return "/cache/ciao" + WITH_REDIRECT;
	}

	public String goToIndexRedirect() {
		return "/cache/index" + WITH_REDIRECT;
	}
}
