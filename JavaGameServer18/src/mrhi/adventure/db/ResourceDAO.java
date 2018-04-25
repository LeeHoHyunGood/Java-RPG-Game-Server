package mrhi.adventure.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mrhi.adventure.model.game.JItem;
import mrhi.adventure.model.game.JMap;
import mrhi.adventure.model.game.JMob;

public class ResourceDAO extends DataDAO {
	private String item_sql = "select * from item_information";
	private String map_sql = "select * from map_information";
	private String mob_sql = "select * from mob_information";
	
	public List<JItem> getItemInfoList() {
		List<JItem> itemList = new ArrayList<>();
		
		ResultSet rs = executeQuery(item_sql);
		try {
			while(rs.next()) {
				JItem item = new JItem();
				item.setItem_idx(rs.getInt("item_idx"));
				item.setItem_type(rs.getInt("item_type"));
				item.setItem_name(rs.getString("item_name"));
				item.setItem_job(rs.getInt("item_job"));
				item.setItem_level(rs.getInt("item_level"));
				item.setItem_power(rs.getInt("item_power"));
				item.setItem_mPower(rs.getInt("item_mPower"));
				item.setItem_str(rs.getInt("item_str"));
				item.setItem_dex(rs.getInt("item_dex"));
				item.setItem_int(rs.getInt("item_int"));
				item.setItem_pro(rs.getInt("item_pro"));
				item.setItem_maxHp(rs.getInt("item_maxHp"));
				item.setItem_maxMp(rs.getInt("item_maxMp"));
				item.setItem_speed(rs.getInt("item_speed"));
				item.setItem_value(rs.getInt("item_value"));
				item.setOwn_idx(0);
				itemList.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return itemList;
	}
	
	public List<JMap> getMapInfoList() {
		List<JMap> mapList = new ArrayList<>();
		
		ResultSet rs = executeQuery(map_sql);
		try {
			while(rs.next()) {
				JMap map = new JMap();
				map.setMap_idx(rs.getInt("map_idx"));
				map.setMap_type(rs.getInt("map_type"));
				map.setMap_name(rs.getString("map_name"));
				map.setMap_width(rs.getInt("map_width"));
				map.setMap_height(rs.getInt("map_height"));
				mapList.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return mapList;
	}
	
	public List<JMob> getMobInfoList() {
		List<JMob> mobList = new ArrayList<>();
		
		ResultSet rs = executeQuery(mob_sql);
		try {
			while(rs.next()) {
				JMob mob = new JMob();
				mob.setMob_idx(rs.getInt("mob_idx"));
				mob.setMob_name(rs.getString("mob_name"));
				mob.setMob_level(rs.getInt("mob_level"));
				mob.setMob_hp(rs.getInt("mob_hp"));
				mob.setMob_exp(rs.getInt("mob_exp"));
				mob.setMob_speed(rs.getInt("mob_speed"));
				mobList.add(mob);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return mobList;
	}

}
