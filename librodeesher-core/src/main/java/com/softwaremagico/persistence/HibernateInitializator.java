package com.softwaremagico.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.log.EsherLog;

public class HibernateInitializator {

	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();	

			// Put database if file in home folder.
			String databaseUrl = configuration.getProperty("hibernate.connection.url");
			EsherLog.severe(HibernateInitializator.class.getName(), databaseUrl);
			if (databaseUrl.contains("jdbc:sqlite")) {
				String databaseName = databaseUrl.substring(databaseUrl.lastIndexOf(":") + 1,
						databaseUrl.length());
				configuration
						.setProperty("hibernate.connection.url",
								"jdbc:sqlite:" + RolemasterFolderStructure.getDatabaseFolderInHome() + "/"
										+ databaseName);
			}
			
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
					configuration.getProperties()).build();			
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			return sessionFactory;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
