package mrhi.adventure.model.game;

public class JMap implements Cloneable{
	private int map_idx;
	private int map_type;
	private String map_name;
	private int map_width;
	private int map_height;
	
	
	public JMap() {
		super();
	}
	
	public JMap(int map_idx, int map_type, String map_name, int map_width, int map_height) {
		super();
		this.map_idx = map_idx;
		this.map_type = map_type;
		this.map_name = map_name;
		this.map_width = map_width;
		this.map_height = map_height;
	}
	
	public JMap(JMap pMap) {
		this.map_idx = pMap.getMap_idx();
		this.map_type = pMap.getMap_type();
		this.map_name = pMap.getMap_name();
		this.map_width = pMap.getMap_width();
		this.map_height = pMap.getMap_height();
	}
	
	public int getMap_idx() {
		return map_idx;
	}
	public void setMap_idx(int map_idx) {
		this.map_idx = map_idx;
	}
	public int getMap_type() {
		return map_type;
	}
	public void setMap_type(int map_type) {
		this.map_type = map_type;
	}
	public String getMap_name() {
		return map_name;
	}
	public void setMap_name(String map_name) {
		this.map_name = map_name;
	}
	public int getMap_width() {
		return map_width;
	}
	public void setMap_width(int map_width) {
		this.map_width = map_width;
	}
	public int getMap_height() {
		return map_height;
	}
	public void setMap_height(int map_height) {
		this.map_height = map_height;
	}
	
	@Override
	public JMap clone() {
		return new JMap(this);
	}
}
