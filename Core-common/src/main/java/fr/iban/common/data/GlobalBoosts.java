package fr.iban.common.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.sql.DataSource;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.data.sql.DbAccess;

public class GlobalBoosts {
	
	private RedissonClient client = RedisAccess.getInstance().getRedissonClient();
	
	private RList<Boost> boosts = client.getList("boosts");
	
	public void deleteGlobalBoostFromDB(Integer id, Long end, Integer value) {
		DataSource ds = DbAccess.getDataSource();
		final String DELETE_SQL = "DELETE FROM sc_boosts WHERE id = ? AND end = ? AND value = ?";
			try (Connection connection = ds.getConnection()) {
				try(PreparedStatement ps = connection.prepareStatement(DELETE_SQL)){
					ps.setInt(1, id);
					ps.setLong(2, end);
					ps.setInt(3, value);
					ps.executeUpdate();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void saveGlobalBoostToDB() {
		DataSource ds = DbAccess.getDataSource();
		try (Connection connection = ds.getConnection()) {
		   try(PreparedStatement ps = connection.prepareStatement("INSERT INTO sc_boosts(id, owner, end, value) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE id=VALUES(id);")){
			 for(Boost boost : boosts) {
				ps.setInt(1, boost.getId());
				ps.setString(2, "GLOBAL");
				ps.setLong(3, boost.getEnd());
				ps.setInt(4, boost.getValue());
				ps.executeUpdate();
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
	
	public void removeGlobalBoostFromDB(Integer id) {
		DataSource ds = DbAccess.getDataSource();
		final String DELETE_SQL = "DELETE FROM sc_boosts WHERE id = ? AND owner = ?";
		try (Connection connection = ds.getConnection()) {
			try(PreparedStatement ps = connection.prepareStatement(DELETE_SQL)){
				ps.setInt(1, id);
				ps.setString(2, "GLOBAL");
				ps.executeUpdate();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public RList<Boost> getBoosts() {
		return boosts;
	}
	
	public int getLastId() {
		int valeur = 0;
		Iterator<Boost> it = getBoosts().iterator();
		while(it.hasNext()){
		     Boost boost = it.next();
		     valeur = boost.getId();
		}
		return valeur;
	}
	
	public long getLastEnd() {
		long end = 0;
		Iterator<Boost> it = getBoosts().iterator();
		while(it.hasNext()){
		     Boost boost = it.next();
		     end = boost.getEnd();
		}
		return end;
	}
	
	public void getGlobalBoostsFromDB() {
		DataSource ds = DbAccess.getDataSource();
		try (Connection connection = ds.getConnection()) {
		   try(PreparedStatement ps = 
				connection.prepareStatement(
						"SELECT * FROM sc_boosts WHERE owner = ? "
						)){
			ps.setString(1, "GLOBAL");
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					boosts.add(new Boost(rs.getInt("id"), rs.getInt("value"), rs.getLong("end")));
			   	    }
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
