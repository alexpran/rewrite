package org.giavacms.rewrite.filter;

import java.io.IOException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class MappingFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;
	private static final String facesExtension = ".jsf";

	private FilterConfig filterConfig;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) request;
		// Get the requested URI
		String uri = httpReq.getRequestURI();

		try {
			System.out.println("***************************");
			System.out.println("MappingFilter uri: " + uri);
			System.out.println("***************************");
			// Check if the URI matches mapping creteria.
			boolean matches = true;
			if (uri.startsWith("/private/") || uri.endsWith("js")
					|| uri.endsWith("css") || uri.endsWith("png")
					|| uri.endsWith("gif") || uri.endsWith("jpg")
					|| uri.endsWith("jpeg")) {
				matches = false;
			}
			if (matches) {

				String originalUri = uri;
				ServletContext context = filterConfig.getServletContext();

				// Strip context path from the requested URI
				String path = context.getContextPath();
				if (uri.startsWith(path)) {
					uri = uri.substring(path.length());
				}
				String mappingPath = "";
				if (uri.contains("/")) {
					mappingPath = uri.substring(0, uri.lastIndexOf("/"));
					uri = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
				}
				// Check if there is actually a file to handle the forward.

				URL url = context.getResource("/cache/" + uri + ".xhtml");
				if (url != null) {
					request.setAttribute("mappingPath", mappingPath);
					request.setAttribute("originalUri", originalUri);

					// Generate the forward URI
					String forwardURI = "/cache/" + uri + facesExtension;
					// Get the request dispatcher
					System.out.println("***************************");
					System.out.println("MappingFilter forwardURI: "
							+ forwardURI);
					System.out.println("MappingFilter originalUri: "
							+ originalUri);
					System.out.println("***************************");
					RequestDispatcher rd = context
							.getRequestDispatcher(forwardURI);
					if (rd != null) {
						// Forward the request to FacesServlet
						rd.forward(request, response);
						return;
					}
				}
			}
			// We are not interested for this request, pass it to the
			// FilterChain.
			chain.doFilter(request, response);

		} catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage());
		} catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage());
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;

	}

}