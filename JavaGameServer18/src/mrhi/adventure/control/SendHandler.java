package mrhi.adventure.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import mrhi.adventure.model.Player;
import mrhi.adventure.model.SendPacket;
import mrhi.adventure.model.Server;
import mrhi.adventure.model.packet.MyPacket;

public class SendHandler implements Runnable{
	
	private Queue<SendPacket> sendPacketQueue = new LinkedList<>();
	
	@Override
	public void run() {
		while(true) {		
			if(sendPacketQueue.size()!=0) {
				SendPacket sp = sendPacketQueue.remove();
				try {
					sp.getPlayer().getConnectionManager().send(sp.getPacket());
				} catch (IOException e) {
					e.printStackTrace();
					if(sp.getPlayer().getAccount() != null && sp.getPlayer().getCharacter() != null)
						Server.getInstance().getCharacterDAO().saveLocation(sp.getPlayer().getCharacter());
					System.out.println(sp.getPlayer().getConnectionManager().getSocket().getInetAddress().getHostAddress() + "¥‘123¿Ã ¡æ∑·«œºÃΩ¿¥œ¥Ÿ.");
				}
//				try {
//					disconnectCharacter(sp.getPlayer());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addPacket(Player player, MyPacket packet) {
		sendPacketQueue.add(new SendPacket(player, packet));
	}
	
	public void addPacket(List<Player> players, MyPacket packet) {
		for(Player player : players) {
			sendPacketQueue.add(new SendPacket(player, packet));
		}
	}
	
//	private void disconnectCharacter(Player p) throws IOException{
//		int mapid = p.getCharacter().getChr_mapid();
//		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(mapid);
//		gameMap.leavePlayer(p);
//		for(Player player : gameMap.getPlayerList())
//			Server.getInstance().sendPacket(new SendPacket(player, new MyPacket(27, p.getCharacter().makeInfoCharacter())));
//	}
}
