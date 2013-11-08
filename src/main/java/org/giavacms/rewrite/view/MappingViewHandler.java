package org.giavacms.rewrite.view;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.servlet.http.HttpServletRequest;

public class MappingViewHandler extends ViewHandlerWrapper {

	private ViewHandler wrappedHandler;

	public MappingViewHandler(ViewHandler defaultHandler) {
		this.wrappedHandler = defaultHandler;
	}

	@Override
	public ViewHandler getWrapped() {
		return wrappedHandler;
	}

	/**
	 * This is the only method needed to be extended. First, we get the normal
	 * URL form the original ViewHandler. Then we simply return the same URL
	 * with the extension stripped of.
	 */
	public String getActionURL(FacesContext context, String viewId) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) context
				.getExternalContext().getRequest();
		Object originalUri = httpServletRequest.getAttribute("originalUri");
		if (originalUri != null && !originalUri.toString().isEmpty()) {
			return originalUri.toString();
		} else {
			return getWrapped().getActionURL(context, viewId);
		}
	}

	public String _getActionURL(FacesContext context, String viewId) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) context
				.getExternalContext().getRequest();
		Object mappingPath = httpServletRequest.getAttribute("mappingPath");
		System.out.println("***************************");
		System.out.println("MappingViewHandler mappingPath: " + mappingPath);
		System.out.println("***************************");
		System.out.println(mappingPath);
		if (mappingPath == null) {
			mappingPath = "";
		}
		String origURL = getWrapped().getActionURL(context, viewId);
		if (!origURL.startsWith("/cache/")) {
			System.out.println("***************************");
			System.out.println("MappingViewHandler origURL: " + origURL);
			System.out.println("***************************");
			return origURL;
		}
		int dotIdx = origURL.lastIndexOf(".");
		String where = "";
		if (dotIdx > 0) {
			where = mappingPath + origURL.substring(0, dotIdx);

		} else {
			where = mappingPath + origURL;
		}
		System.out.println("***************************");
		System.out.println("MappingViewHandler where: " + where);
		System.out.println("***************************");
		return where;
	}

	@Override
	public String calculateCharacterEncoding(FacesContext arg0) {
		System.out.println("calculateCharacterEncoding");
		return super.calculateCharacterEncoding(arg0);
	}

	@Override
	public Locale calculateLocale(FacesContext arg0) {
		System.out.println("calculateLocale");
		return super.calculateLocale(arg0);
	}

	@Override
	public String calculateRenderKitId(FacesContext arg0) {
		System.out.println("calculateRenderKitId");
		return super.calculateRenderKitId(arg0);
	}

	@Override
	public UIViewRoot createView(FacesContext arg0, String arg1) {
		System.out.println("createView");
		return super.createView(arg0, arg1);
	}

	@Override
	public String deriveViewId(FacesContext arg0, String arg1) {
		System.out.println("deriveViewId");
		return super.deriveViewId(arg0, arg1);
	}

	@Override
	public String getBookmarkableURL(FacesContext arg0, String arg1,
			Map<String, List<String>> arg2, boolean arg3) {
		System.out.println("getBookmarkableURL");
		return super.getBookmarkableURL(arg0, arg1, arg2, arg3);
	}

	@Override
	public String getRedirectURL(FacesContext paramFacesContext,
			String paramString, Map<String, List<String>> paramMap,
			boolean paramBoolean) {
		System.out.println("getRedirectURL: " + paramString);
		paramString = paramString.replace("/cache/", "/rewrite/");

		if (paramString != null && !paramString.trim().isEmpty()) {
			int dotIdx = paramString.lastIndexOf(".");
			if (dotIdx > 0) {
				paramString = paramString.substring(0, dotIdx);
			}
			System.out.println("getRedirectURL rewrite:" + paramString);
			HttpServletRequest httpServletRequest = (HttpServletRequest) paramFacesContext
					.getExternalContext().getRequest();
			httpServletRequest.setAttribute("originalUri", paramString);
		}
		return super.getRedirectURL(paramFacesContext, paramString, paramMap,
				paramBoolean);
	}

	@Override
	public String getResourceURL(FacesContext arg0, String arg1) {
		System.out.println("getResourceURL");
		return super.getResourceURL(arg0, arg1);
	}

	@Override
	public ViewDeclarationLanguage getViewDeclarationLanguage(
			FacesContext arg0, String arg1) {
		System.out.println("getViewDeclarationLanguage");
		return super.getViewDeclarationLanguage(arg0, arg1);
	}

	@Override
	public void initView(FacesContext arg0) throws FacesException {
		System.out.println("initView");
		super.initView(arg0);
	}

	@Override
	public void renderView(FacesContext arg0, UIViewRoot arg1)
			throws IOException, FacesException {
		System.out.println("renderView");
		super.renderView(arg0, arg1);
	}

	@Override
	public UIViewRoot restoreView(FacesContext arg0, String arg1) {
		System.out.println("restoreView");
		return super.restoreView(arg0, arg1);
	}

	@Override
	public void writeState(FacesContext arg0) throws IOException {
		System.out.println("writeState");
		super.writeState(arg0);
	}

}
