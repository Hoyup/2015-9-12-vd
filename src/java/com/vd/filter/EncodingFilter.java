package com.vd.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter {
	
	protected FilterConfig config;

	 protected String Encoding = null;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
	  if (request.getCharacterEncoding() == null) {
		   if (Encoding != null) {
		    request.setCharacterEncoding(Encoding);
		    response.setCharacterEncoding(Encoding);
		   }
		  }
	  	  System.out.println("Nor At "+new Date()+"-->"+((HttpServletRequest) request).getRequestURI());
		  chain.doFilter(request,response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		 this.config = config;
		  this.Encoding = config.getInitParameter("Encoding");
	}

}
