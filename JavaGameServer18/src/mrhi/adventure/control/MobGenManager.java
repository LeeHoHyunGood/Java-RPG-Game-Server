package mrhi.adventure.control;

import java.io.IOException;
import java.util.Map;

import mrhi.adventure.model.Player;
import mrhi.adventure.model.SendPacket;
import mrhi.adventure.model.Server;
import mrhi.adventure.model.game.GameMap;
import mrhi.adventure.model.game.JMob;
import mrhi.adventure.model.packet.MyPacket;

public class MobGenManager extends Thread {
	
	@Override
	public void run() {
		while(true) {
			System.out.println("MobGen!");
			Map<Integer, GameMap> existMap = Server.getInstance().getGame().getExistMap();
			for (int map_idx : existMap.keySet()) {
				GameMap gameMap = existMap.get(map_idx);
				for (int i = gameMap.getMobList().size(); i < 10; i++) {
					int x = ((int) (Math.random() * gameMap.getMap().getMap_width()));
					int y = ((int) (Math.random() * gameMap.getMap().getMap_height()));
					Server.getInstance().getGameManager().generateMob(1000, map_idx, x, y);
				}
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
