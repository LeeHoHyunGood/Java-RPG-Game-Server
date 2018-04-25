package mrhi.adventure.model;

import mrhi.adventure.model.packet.MyPacket;

public class SendPacket {
	private Player player;
	private MyPacket packet;

	public SendPacket(Player player, MyPacket packet) {
		super();
		this.player = player;
		this.packet = packet;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public MyPacket getPacket() {
		return packet;
	}
	public void setPacket(MyPacket packet) {
		this.packet = packet;
	}
}
