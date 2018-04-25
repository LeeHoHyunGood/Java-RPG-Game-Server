package mrhi.adventure.model.game;

import java.util.LinkedList;
import java.util.List;

import mrhi.adventure.model.Player;
import mrhi.adventure.model.vo.MapVO;
import mrhi.adventure.model.vo.MobVO;

public class GameMap {
	private JMap map;
	private List<Player> playerList = new LinkedList<>();
	private List<JMob> mobList = new LinkedList<>();
	private List<JItem> itemList = new LinkedList<>();
	
	public MapVO getMapVO() {
		MapVO mapVO = new MapVO();
		mapVO.setMap_height(map.getMap_height());
		mapVO.setMap_idx(map.getMap_idx());
		mapVO.setMap_name(map.getMap_name());
		mapVO.setMap_type(map.getMap_type());
		mapVO.setMap_width(map.getMap_width());
		
		for(Player p : playerList)
			mapVO.getOtherCharList().add(p.getCharacter().makeInfoCharacter());
		
		for(JMob mob : mobList)
			mapVO.getMobList().add(mob.getMobVO());
		
		for(JItem item : itemList) 
			mapVO.getItemList().add(item.getItemVO());

		return mapVO;
	}
	
	public void addItem(JItem item) {
		itemList.add(item);
	}
	
	public void removeItem(JItem item) {
		itemList.remove(item);
	}
	
	public void addMob(JMob jmob) {
		mobList.add(jmob);
	}
	
	public void removeMob(JMob jmob) {
		mobList.remove(jmob);
	}
	
	public JMob getMob(MobVO mobVO) {
		for(JMob mob : mobList) {
			if(mob.getGen_idx()==mobVO.getGen_idx()) {
				return mob;
			}
		}
		return null;
	}
	
//	public void attack(MobVO mobVO, CharacterVO charVO, int damage) {
//		JMob mob = getMob(mobVO);
//		if(mob.getMob_hp()>damage) {
//			mob.getDamageList().add(new Damage(charVO, damage));
//			mob.setMob_hp(mob.getMob_hp()-damage);
//		} else {
//			mob.getDamageList().add(new Damage(charVO, mob.getMob_hp()));
//			mob.setMob_hp(0);
//		}
//		
//		if(mob.getMob_hp()==0) 
//			DeadMob(mob);
//	}
	
//	public void DeadMob(JMob mob) {
//		for(Damage dam : mob.getDamageList())
//			charDAO.plusExp(dam.getCharVO(), mob.getMob_exp()*(dam.getDamage()/mob.getMob_maxHp()));
//		
//		for(Player player : this.playerList) {
//			player.getConnectionManager().send(new MyPacket(41, mob.getMobVO()));
//		}
//		
//		this.mobList.remove(mob);
//	}
	
	public void addPlayer(Player player) {
		this.playerList.add(player);
	}
	
	public void removePlayer(Player player) {
		this.playerList.remove(player);
	}

	public JMap getMap() {
		return map;
	}

	public void setMap(JMap map) {
		this.map = map;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	public List<JMob> getMobList() {
		return mobList;
	}

	public void setMobList(List<JMob> mobList) {
		this.mobList = mobList;
	}

	public List<JItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<JItem> itemList) {
		this.itemList = itemList;
	}
	
}
