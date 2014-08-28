package com.softwaremagico.persistence;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import com.softwaremagico.log.EsherLog;

@SuppressWarnings("rawtypes")
public class JpaSchemaExporter {
	private static final String DATABASE_NAME = "esher";
	private static final String DEFAULT_DATABASE_HOST_NAME = "localhost";
	private static final String DEFAULT_DATABASE_PORT = "3306";
	private static final String DEFAULT_DATABASE_USER = "esher";
	private static final String DEFAULT_DATABASE_PASSWORD = "esher";
	private static final String[] PACKETS_TO_SCAN = { "com.softwaremagico.librodeesher" };
	private static final String DEFAULT_OUTPUT_DIRECTORY = "../database/";
	private Configuration cfg;

	public JpaSchemaExporter(String[] packagesName) {
		cfg = new Configuration();
		try {
			for (int i = 0; i < packagesName.length; i++) {
				for (Class clazz : getClasses(packagesName[i])) {
					cfg.addAnnotatedClass(clazz);
				}
			}
		} catch (Exception e) {
			EsherLog.errorMessage(JpaSchemaExporter.class.getName(), e);
		}
	}

	/**
	 * Utility method used to fetch Class list based on a package name.
	 * 
	 * @param packageName
	 *            should be the package containing your annotated beans.
	 */
	private List<Class> getClasses(String packageName) throws Exception {
		File directory = null;
		try {
			ClassLoader cld = getClassLoader();
			URL resource = getResource(packageName, cld);
			directory = new File(resource.getFile());
		} catch (NullPointerException ex) {
			throw new ClassNotFoundException(packageName + " (" + directory + ") does not appear to be a valid package");
		}
		return collectClasses(packageName, directory);
	}

	private ClassLoader getClassLoader() throws ClassNotFoundException {
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		if (cld == null) {
			throw new ClassNotFoundException("Can't get class loader.");
		}
		return cld;
	}

	private URL getResource(String packageName, ClassLoader cld) throws ClassNotFoundException {
		String path = packageName.replace('.', '/');
		URL resource = cld.getResource(path);
		if (resource == null) {
			throw new ClassNotFoundException("No resource for " + path);
		}
		return resource;
	}

	private List<Class> collectClasses(String packageName, File directory) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<>();
		if (directory.exists()) {
			String[] files = directory.list();
			for (String fileName : files) {
				File subdirectory = new File(directory.getPath() + File.separator + fileName);
				if (fileName.endsWith(".class")) {
					// removes the .class extension
					classes.add(Class.forName(packageName + '.' + fileName.substring(0, fileName.length() - 6)));
				} else if (subdirectory.isDirectory()) {
					// Subpacket.
					classes.addAll(collectClasses(packageName + '.' + fileName, subdirectory));
				}
			}
		} else {
			throw new ClassNotFoundException(packageName + " is not a valid package");
		}
		return classes;
	}

	/**
	 * Create a script that can generate a database for the selected dialect
	 * 
	 * @param dialect
	 * @param directory
	 */
	public void createDatabaseScript(HibernateDialect dialect, String directory, boolean onlyCreation) {
		cfg.setProperty("hibernate.hbm2ddl.auto", "create");
		cfg.setProperty("hibernate.dialect", dialect.getDialectClass());
		cfg.setProperty("hibernate.show_sql", "false");
		SchemaExport export = new SchemaExport(cfg);
		export.setDelimiter(";");
		export.setOutputFile(directory + File.separator + "create_" + dialect.name().toLowerCase() + ".sql");
		export.setFormat(true);
		export.execute(true, false, false, onlyCreation);
	}

	public void updateDatabaseScript(HibernateDialect dialect, String outputDirectory, String host, String port,
			String username, String password) {
		cfg.setProperty("hibernate.hbm2ddl.auto", "update");
		cfg.setProperty("hibernate.dialect", dialect.getDialectClass());
		cfg.setProperty("hibernate.show_sql", "true");
		cfg.setProperty("hibernate.connection.driver_class", dialect.getDriver());
		cfg.setProperty("hibernate.connection.url", "jdbc:mysql://" + host + ":" + port + "/" + DATABASE_NAME);
		cfg.setProperty("hibernate.connection.username", username);
		cfg.setProperty("hibernate.connection.password", password);

		SchemaUpdate update = new SchemaUpdate(cfg);
		update.setDelimiter(";");
		update.setOutputFile(outputDirectory + "updates/update_" + dialect.name().toLowerCase() + "_" + getDate()
				+ ".sql");
		update.setFormat(true);
		update.execute(true, true);
	}

	/**
	 * For executing.
	 * 
	 * @param args
	 *            args[0] -> directory, args[1] -> user, args[2] -> password, args[3] -> host, args[4] -> port
	 */
	public static void main(String[] args) {
		JpaSchemaExporter gen = new JpaSchemaExporter(PACKETS_TO_SCAN);
		String directory;
		if (args.length == 0) {
			directory = DEFAULT_OUTPUT_DIRECTORY;
		} else {
			directory = args[0] + File.separator;
		}

		String user;
		if (args.length > 0) {
			user = DEFAULT_DATABASE_USER;
		} else {
			user = args[1];
		}
		String password;
		if (args.length > 1) {
			password = DEFAULT_DATABASE_PASSWORD;
		} else {
			password = args[2];
		}

		String host;
		if (args.length > 2) {
			host = DEFAULT_DATABASE_HOST_NAME;
		} else {
			host = args[3];
		}

		String port;
		if (args.length > 3) {
			port = DEFAULT_DATABASE_PORT;
		} else {
			port = args[4];
		}
		gen.createDatabaseScript(HibernateDialect.MYSQL, directory, true);
		gen.updateDatabaseScript(HibernateDialect.MYSQL, directory, host, port, user, password);
	}

	private static String getDate() {
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy_HHmmss");
		return formateador.format(ahora);
	}

}
