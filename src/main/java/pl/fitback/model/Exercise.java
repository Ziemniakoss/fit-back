package pl.fitback.model;

public class Exercise {
	private int id;
	private String name;
	private String description;
	private double met;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMet() {
		return met;
	}

	public void setMet(double met) {
		this.met = met;
	}
}
