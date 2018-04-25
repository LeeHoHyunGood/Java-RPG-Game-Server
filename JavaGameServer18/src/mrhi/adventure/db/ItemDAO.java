package mrhi.adventure.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mrhi.adventure.model.game.DropItem;
import mrhi.adventure.model.game.JItem;
import mrhi.adventure.model.game.JMob;
import mrhi.adventure.model.vo.CharacterVO;
import mrhi.adventure.model.vo.ItemVO;

public class ItemDAO extends DataDAO {

	private String getDropItem_sql = "select item_idx, probability from drop_item where mob_idx=?";
	private String gainItem_sql = "insert into item_own values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private String itemList_sql = "select * from item_own where chr_idx=?";
	private String saleItem_sql = "DELETE FROM item_own WHERE own_idx =?";
	
	public List<DropItem> getDropItem(JMob mob) {
		List<DropItem> dropItemList = new ArrayList<>();	
		
		ResultSet rs = executeQuery(getDropItem_sql, mob.getMob_idx());
		try {
			while(rs.next()) {
				DropItem dropItem = new DropItem();
				dropItem.setItem_idx(rs.getInt("item_idx"));
				dropItem.setProbability(rs.getInt("probability"));
				dropItemList.add(dropItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dropItemList;
	}
	
	public void gainItem(CharacterVO charVO, JItem item) {
		executeUpdate(gainItem_sql, charVO.getChr_idx(), item.getItem_idx(), item.getItem_name(), item.getItem_type()
				, item.getItem_level(), item.getItem_job(), item.getItem_power(), item.getItem_mPower()
				, item.getItem_str(), item.getItem_dex(), item.getItem_int(), item.getItem_pro()
				, item.getItem_maxHp(), item.getItem_maxMp(), item.getItem_speed(), item.getItem_value());
	}
	
	public List<JItem> getItemList(CharacterVO charVO) {
		List<JItem> itemList = new ArrayList<>();	
		ResultSet rs = executeQuery(itemList_sql, charVO.getChr_idx());
		try {
			while(rs.next()) {
				JItem itemVO = new JItem();
				itemVO.setItem_dex(rs.getInt("item_dex"));
				itemVO.setItem_idx(rs.getInt("item_idx"));
				itemVO.setItem_int(rs.getInt("item_int"));
				itemVO.setItem_job(rs.getInt("item_job"));
				itemVO.setItem_level(rs.getInt("item_level"));
				itemVO.setItem_maxHp(rs.getInt("item_maxHp"));
				itemVO.setItem_maxMp(rs.getInt("item_maxMp"));
				itemVO.setItem_mPower(rs.getInt("item_mPower"));
				itemVO.setItem_name(rs.getString("item_name"));
				itemVO.setItem_power(rs.getInt("item_power"));
				itemVO.setItem_pro(rs.getInt("item_pro"));
				itemVO.setItem_speed(rs.getInt("item_speed"));
				itemVO.setItem_str(rs.getInt("item_str"));
				itemVO.setItem_type(rs.getInt("item_type"));
				itemVO.setOwn_idx(rs.getInt("own_idx"));
				itemVO.setItem_value(rs.getInt("item_value"));
				itemList.add(itemVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemList;
	}
	
	public ItemVO itemDelete(ItemVO itemVO) {
		ItemVO item = new ItemVO();
		executeUpdate(saleItem_sql, itemVO.getOwn_idx());
		item = itemVO;
		return item;
	}	
	
	public ItemVO buyItems(CharacterVO charVO, ItemVO item) {
		ItemVO retJitem = null;
		System.out.println(charVO.getChr_money());
		if (charVO.getChr_money()-item.getItem_value() >= 0) {
			retJitem = new ItemVO();
		retJitem.setItem_dex(item.getItem_dex());
		retJitem.setItem_idx(item.getItem_idx());
		retJitem.setItem_int(item.getItem_int());
		retJitem.setItem_job(item.getItem_job());
		retJitem.setItem_level(item.getItem_level());
		retJitem.setItem_maxHp(item.getItem_maxHp());
		retJitem.setItem_maxMp(item.getItem_maxMp());
		retJitem.setItem_mPower(item.getItem_mPower());
		retJitem.setItem_name(item.getItem_name());
		retJitem.setItem_power(item.getItem_power());
		retJitem.setItem_pro(item.getItem_pro());
		retJitem.setItem_speed(item.getItem_speed());
		retJitem.setItem_str(item.getItem_str());
		retJitem.setItem_type(item.getItem_type());
		retJitem.setOwn_idx(item.getOwn_idx());
		retJitem.setItem_value(item.getItem_value());
		executeUpdate(gainItem_sql, charVO.getChr_idx(), item.getItem_idx(), item.getItem_name(), item.getItem_type()
				, item.getItem_level(), item.getItem_job(), item.getItem_power(), item.getItem_mPower()
				, item.getItem_str(), item.getItem_dex(), item.getItem_int(), item.getItem_pro()
				, item.getItem_maxHp(), item.getItem_maxMp(), item.getItem_speed(), item.getItem_value());
		}
		return retJitem;
	}
}
