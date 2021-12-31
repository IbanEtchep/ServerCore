package fr.iban.common.data.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbTables {

	public static void createTables(String prefix) {
		createplayersTable(prefix);
		createAnnounceBLTable(prefix);
		createIpTable(prefix);
		createOptionsTable(prefix);
		createIgnoredPlayersTable(prefix);
	}

	/*
	 * Création de la table
	 */
	private static void createplayersTable(String prefix) {
		createTable("CREATE TABLE IF NOT EXISTS "+prefix+"_players (" +
					"  id          int auto_increment PRIMARY KEY," +
					"  uuid    varchar(36)  not null," +
					"  name        varchar(16)  not null," +
					"  date_created timestamp default now()," +
					"  lastseen       bigint DEFAULT 0," +
					"  maxclaims smallint DEFAULT 5," +
					"  CONSTRAINT  UC_sc_players" +
					"  UNIQUE (id)," +
					"  CONSTRAINT UC_sc_players_uuid" +
					"  UNIQUE (uuid)" +
					") engine = InnoDB;");
	}
	
	private static void createOptionsTable(String prefix) {
		createTable("CREATE TABLE IF NOT EXISTS "+prefix+"_options (" +
				"  id int," +
				"  idOption varchar(16)," +
				"  CONSTRAINT PK_sc_options" +
				"  PRIMARY KEY (id, idOption)," +
				"  CONSTRAINT FK_sc_options" +
				"  FOREIGN KEY (id) REFERENCES sc_players(id)" +
		") engine = InnoDB;");
    }

	private static void createAnnounceBLTable(String prefix) {
		createTable("CREATE TABLE IF NOT EXISTS "+prefix+"_annonces_blacklist (" +
							"  id int," +
							"  idAnnonce int," +
							"  CONSTRAINT PK_sc_annonces_blacklist" +
							"  PRIMARY KEY (id, idAnnonce)," +
							"  CONSTRAINT FK_sc_annonces" +
							"  FOREIGN KEY (id) REFERENCES sc_players(id)" +
					") engine = InnoDB;");
	}
	
	private static void createIgnoredPlayersTable(String prefix) {
		createTable("CREATE TABLE IF NOT EXISTS "+prefix+"_ignored_players (" +
							"  id int," +
							"  uuidPlayer varchar(36) not null," +
							"  CONSTRAINT PK_sc_ignored_players" +
							"  PRIMARY KEY (id, uuidPlayer)," +
							"  CONSTRAINT FK_sc_ignored" +
							"  FOREIGN KEY (id) REFERENCES sc_players(id)" +
					") engine = InnoDB;");
	}

	private static void createIpTable(String prefix) {
		createTable("CREATE TABLE IF NOT EXISTS "+prefix+"_players_ip (" +
							"  id int," +
							"  ip VARBINARY(16)," +
							"  CONSTRAINT PK_sc_players_ip" +
							"  PRIMARY KEY (id, ip)," +
							"  CONSTRAINT FK_sc_players_ip" +
							"  FOREIGN KEY (id) REFERENCES sc_players(id)" +
					") engine = InnoDB;");
	}
	private static void createTable(String statement) {
		try (Connection connection = DbAccess.getDataSource().getConnection()) {
			try(PreparedStatement preparedStatemente = connection.prepareStatement(statement)){
				preparedStatemente.executeUpdate();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
