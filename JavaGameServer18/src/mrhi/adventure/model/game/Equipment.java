package mrhi.adventure.model.game;

import java.io.Serializable;

public class Equipment implements Serializable {
	private int item_idx;
	private int equip_idx;
	private int level;
	private int job;
	private int def;
	private int magic_def;
	private int power;
	private int magic_power;
	private int str;
	private int dex;
	private int intp;
	private int luk;
	private int hp;
	private int mp;
	
	public Equipment(int item_idx, int equip_idx, int level, int job, int def, int magic_def, int power,
			int magic_power, int str, int dex, int intp, int luk, int hp, int mp) {
		super();
		this.item_idx = item_idx;
		this.equip_idx = equip_idx;
		this.level = level;
		this.job = job;
		this.def = def;
		this.magic_def = magic_def;
		this.power = power;
		this.magic_power = magic_power;
		this.str = str;
		this.dex = dex;
		this.intp = intp;
		this.luk = luk;
		this.hp = hp;
		this.mp = mp;
	}
	public Equipment() {
		super();
	}
	public int getItem_idx() {
		return item_idx;
	}
	public void setItem_idx(int item_idx) {
		this.item_idx = item_idx;
	}
	public int getEquip_idx() {
		return equip_idx;
	}
	public void setEquip_idx(int equip_idx) {
		this.equip_idx = equip_idx;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getJob() {
		return job;
	}
	public void setJob(int job) {
		this.job = job;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getMagic_def() {
		return magic_def;
	}
	public void setMagic_def(int magic_def) {
		this.magic_def = magic_def;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getMagic_power() {
		return magic_power;
	}
	public void setMagic_power(int magic_power) {
		this.magic_power = magic_power;
	}
	public int getStr() {
		return str;
	}
	public void setStr(int str) {
		this.str = str;
	}
	public int getDex() {
		return dex;
	}
	public void setDex(int dex) {
		this.dex = dex;
	}
	public int getIntp() {
		return intp;
	}
	public void setIntp(int intp) {
		this.intp = intp;
	}
	public int getLuk() {
		return luk;
	}
	public void setLuk(int luk) {
		this.luk = luk;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMp() {
		return mp;
	}
	public void setMp(int mp) {
		this.mp = mp;
	}
}
