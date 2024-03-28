public class Vertex {
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	int value, weight;
	Vertex(int value, int weight)  {
		this.value = value;
		this.weight = weight;
	}
}