package com.ioana.google.places.guice;

import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.ioana.google.places.AppModule;

public class AppGuiceServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new AppModule());
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}
