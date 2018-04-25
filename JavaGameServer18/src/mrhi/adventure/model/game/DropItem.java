package mrhi.adventure.model.game;

public class DropItem {
	private int item_idx;
	private int probability;

	public DropItem() {
		super();
	}
	public DropItem(int item_idx, int probability) {
		super();
		this.item_idx = item_idx;
		this.probability = probability;
	}
	public int getItem_idx() {
		return item_idx;
	}
	public void setItem_idx(int item_idx) {
		this.item_idx = item_idx;
	}
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
	}
}
