package mrhi.adventure.model.game;

import mrhi.adventure.model.Player;

public class Damage {
	private Player player;
	private double damage;
	
	public Damage() {
		super();
	}

	public Damage(Player player, double damage) {
		super();
		this.player = player;
		this.damage = damage;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
}
