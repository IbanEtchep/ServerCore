package fr.iban.common.data;

public class Boost {
	
	private int id;
	private int value;
	private long end;
	
	public Boost() {}
	
	public Boost(int id, int value, long end) {
		this.id = id;
		this.value = value;
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}
	
	

}
