package fr.iban.common.data.sql;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DbAccess {

	private static HikariDataSource dataSource;

	public static void initPool(DbCredentials credentials) {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(credentials.toURI());
		hikariConfig.setUsername(credentials.getUser());
		hikariConfig.setPassword(credentials.getPass());
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		System.out.println("Tentative de connexion à la bdd " + credentials.toURI());
		dataSource = new HikariDataSource(hikariConfig);
		System.out.println("Connection effectuée.");
	}

	public static void closePool(){
		if(dataSource != null && !dataSource.isClosed())
			dataSource.close();
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

}
