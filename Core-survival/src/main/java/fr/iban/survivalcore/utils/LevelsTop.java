package fr.iban.survivalcore.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.sql.DataSource;

import fr.iban.common.data.sql.DbAccess;

public class LevelsTop {
	
	public static List<TopJoueur> getTopLevels(int size){
		List<TopJoueur> top = new ArrayList<>();
		DataSource ds = DbAccess.getDataSource();

		try(Connection connection = ds.getConnection()){
			try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM sc_players ORDER BY exp DESC LIMIT ?;")){
				ps.setInt(1, size);
				try(ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
						long exp = rs.getLong("exp");
						String name = rs.getString("name");
						top.add(new TopJoueur(name, LevelUtils.getLevel((int) exp)));
					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return top;
	}
	
	public static CompletableFuture<List<TopJoueur>> getTopLevelsAsync(int size) {
		return CompletableFuture.supplyAsync(() -> {
			return getTopLevels(size);
		});
	}
}
	