package fr.iban.common.teleport;

public class SLocation {
	
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;
	private String world;
	private String server;
	
	public SLocation() {}
	
	public SLocation(String server, String world, double x, double y, double z, float pitch, float yaw) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.world = world;
		this.server = server;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	public String serialize() {
		StringBuilder sb = new StringBuilder();
		sb.append(server);
		sb.append(":");
		sb.append(world);
		sb.append(":");
		sb.append(x);
		sb.append(":");
		sb.append(y);
		sb.append(":");
		sb.append(z);
		sb.append(":");
		sb.append(pitch);
		sb.append(":");
		sb.append(yaw);
		return sb.toString();
	}
	
	public SLocation deserialize(String slocation) {
		String[] split = slocation.split(":");
		return new SLocation(split[0], split[1], Double.parseDouble(split[2]), Double.parseDouble(split[3]), Double.parseDouble(split[4]), Float.parseFloat(split[5]), Float.parseFloat(split[6]));
	}
	
	@Override
	public String toString() {
		return server + " - " + world + " - " + x + " - " + y + " - " + z;
	}

}
