package mrhi.adventure.model.game;

import java.util.ArrayList;
import java.util.List;

import mrhi.adventure.model.vo.MobVO;

public class JMob implements Cloneable{
	private int mob_idx;
	private int gen_idx;
	private String mob_name;
	private int mob_level;
	private int mob_hp;
	private int mob_maxHp;
	private int mob_exp;
	private int mob_speed;
	private int x;
	private int y;
	private List<Damage> damageList;
	
	public JMob() {
		super();
	}
	
	public JMob(JMob jMob) {
		mob_idx = jMob.getMob_idx();
		gen_idx = jMob.getGen_idx();
		mob_name = jMob.getMob_name();
		mob_level = jMob.getMob_level();
		mob_maxHp = jMob.getMob_hp();
		mob_hp = jMob.getMob_hp();
		mob_exp = jMob.getMob_exp();
		mob_speed = jMob.getMob_speed();
		x = jMob.getX();
		y = jMob.getY();
	}
	
	public MobVO getMobVO() {
		MobVO mobVO = new MobVO();
		mobVO.setMob_idx(mob_idx);
		mobVO.setGen_idx(gen_idx);
		mobVO.setMob_name(mob_name);
		mobVO.setMob_level(mob_level);
		mobVO.setMob_hp(mob_hp);
		mobVO.setMob_exp(mob_exp);
		mobVO.setMob_speed(mob_speed);
		mobVO.setX(x);
		mobVO.setY(y);
		return mobVO;
	}
	
	public void hurt(Damage dam) {
		if(this.damageList==null)
			this.damageList = new ArrayList<>();
		for(Damage damage : this.damageList)
		{
			if(damage.getPlayer().getCharacter().getChr_idx()==dam.getPlayer().getCharacter().getChr_idx()) {
				damage.setDamage(damage.getDamage()+dam.getDamage());
				return;
			}
		}
		this.damageList.add(dam);
	}
	
	public List<Damage> getDamageList() {
		return damageList;
	}
	public void setDamageList(List<Damage> damageList) {
		this.damageList = damageList;
	}
	public int getMob_idx() {
		return mob_idx;
	}
	public void setMob_idx(int mob_idx) {
		this.mob_idx = mob_idx;
	}
	public int getGen_idx() {
		return gen_idx;
	}
	public void setGen_idx(int gen_idx) {
		this.gen_idx = gen_idx;
	}
	public String getMob_name() {
		return mob_name;
	}
	public void setMob_name(String mob_name) {
		this.mob_name = mob_name;
	}
	public int getMob_level() {
		return mob_level;
	}
	public void setMob_level(int mob_level) {
		this.mob_level = mob_level;
	}
	public int getMob_hp() {
		return mob_hp;
	}
	public void setMob_hp(int mob_hp) {
		this.mob_hp = mob_hp;
	}
	public int getMob_exp() {
		return mob_exp;
	}
	public void setMob_exp(int mob_exp) {
		this.mob_exp = mob_exp;
	}
	public int getMob_speed() {
		return mob_speed;
	}
	public void setMob_speed(int mob_speed) {
		this.mob_speed = mob_speed;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getMob_maxHp() {
		return mob_maxHp;
	}
	public void setMob_maxHp(int mob_maxHp) {
		this.mob_maxHp = mob_maxHp;
	}
	@Override
	public JMob clone() {
		return new JMob(this);
	}
}
