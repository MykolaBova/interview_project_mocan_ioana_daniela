package com.ioana.google.places;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;

import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.ioana.google.places.server.GooglePlacesServiceImpl;
import com.ioana.google.places.service.api.CityService;
import com.ioana.google.places.service.api.GoogleAPIService;
import com.ioana.google.places.service.impl.CityServiceImpl;
import com.ioana.google.places.service.impl.GoogleAPIServiceImpl;

public class AppModule extends ServletModule {

	static final String CONTEXT_PATH_APPMODULE_APPSERVICE = "/GooglePlaces/googlePlaces";

	@Override
	protected void configureServlets() {
		// BIND LOGGER
		bind(Log.class).to(Log4JLogger.class);

		install(new JpaPersistModule("default"));
		filter("/*").through(PersistFilter.class);

		bind(CityService.class).to(CityServiceImpl.class);
		bind(GoogleAPIService.class).to(GoogleAPIServiceImpl.class);

		// CONFIGURE SERVLETS
		serve(CONTEXT_PATH_APPMODULE_APPSERVICE).with(
				GooglePlacesServiceImpl.class);
	}
}
