package mrhi.adventure.model;

import mrhi.adventure.model.game.GameMap;
import mrhi.adventure.model.game.JItem;
import mrhi.adventure.model.game.JMob;
import mrhi.adventure.model.packet.MyPacket;
import mrhi.adventure.model.vo.AccountVO;
import mrhi.adventure.model.vo.CharacterVO;
import mrhi.adventure.model.vo.IntegerVO;
import mrhi.adventure.model.vo.ItemVO;

public class GameManager {

	public void createItem(int item_idx, int map_idx, int x, int y) {
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(map_idx);
		JItem item = Server.getInstance().getGame().createItem(item_idx, x, y);
		gameMap.addItem(item);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(43, item.getItemVO()));
	}

	public void generateMob(int mob_idx, int map_idx, int x, int y) {
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(map_idx);
		JMob mob = Server.getInstance().getGame().generateMob(mob_idx, x, y);
		gameMap.addMob(mob);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(42, mob.getMobVO()));
	}

	public void gainItem(Player player, ItemVO itemVO) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(mapid);
		for(JItem item : gameMap.getItemList()) {
			if(item.getGen_idx()==itemVO.getGen_idx()) {
				gameMap.getItemList().remove(item);
				Server.getInstance().getItemDAO().gainItem(player.getCharacter(), item);
				Server.getInstance().addPacket(player, new MyPacket(61, item.getItemVO()));
				Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(45, itemVO));
				break;
			}
		}
	}

	public void joinMap(Player player, int map_idx) {
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(map_idx);
		gameMap.addPlayer(player);
		Server.getInstance().addPacket(player, new MyPacket(31, gameMap.getMapVO()));
		Server.getInstance().addPacket(Server.getInstance().getGame().getExistMap().get(map_idx).getPlayerList(), new MyPacket(26, player.getCharacter().makeInfoCharacter()));
	}

	public void leaveMap(Player player) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(mapid);
		gameMap.removePlayer(player);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(27, player.getCharacter().makeInfoCharacter()));
	}

	public void showPlayer(Player player) {
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(player.getCharacter().getChr_mapid());
		gameMap.addPlayer(player);
		Server.getInstance().addPacket(player, new MyPacket(31, gameMap.getMapVO()));
		Server.getInstance().addPacket(Server.getInstance().getGame().getExistMap().get(player.getCharacter().getChr_mapid()).getPlayerList(), new MyPacket(26, player.getCharacter().makeInfoCharacter()));
	}

	public void hidePlayer(Player player) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(mapid);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(27, player.getCharacter().makeInfoCharacter()));
	}

	public void saveStat(Player player, CharacterVO charVO) {
		Server.getInstance().getCharacterDAO().saveStat(player.getCharacter());
		Server.getInstance().addPacket(player, new MyPacket(81, charVO));
	}
	
	public void dieCharacter(Player player) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(mapid);
		
		player.getCharacter().setDead(true);
		player.getCharacter().setChr_hp(0);
		Server.getInstance().addPacket(player, new MyPacket(72, null));
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(73, player.getCharacter().makeInfoCharacter()));
	}
	
	public void hurtCharacter(Player player, int damage) {
		player.getCharacter().setChr_hp(player.getCharacter().getChr_hp()-damage);
		Server.getInstance().addPacket(player, new MyPacket(71, new IntegerVO(damage)));
	}


	public void deleteCharacter(AccountVO accountVO, CharacterVO charVO) {
		Server.getInstance().getCharacterDAO().deleteCharacter(accountVO, charVO);
	}

	public void outPlayer(String chr_name) {
		for(Player player : Server.getInstance().getPlayerList()) {
			if(player.getCharacter()!=null && player.getCharacter().getChr_name().equals(chr_name))
				player.disconnect();
		}
	}
}
