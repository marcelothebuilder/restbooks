package io.github.marcelothebuilder.restbooks.config;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletInitializationConfig implements ServletContextListener {

	private static final Logger LOG = LoggerFactory.getLogger(ServletInitializationConfig.class); 
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.info("Setting app timezone.");
		ZoneId brZoneId = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(-3));
		TimeZone brTimeZone = TimeZone.getTimeZone(brZoneId);
		TimeZone.setDefault(brTimeZone);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}

}
