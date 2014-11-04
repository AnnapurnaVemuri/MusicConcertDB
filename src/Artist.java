public class Artist {
	int id;
	String name, webpage;

	public Artist(int id, String name, String webpage) {
		this.id = id;
		this.name = name;
		this.webpage = webpage;
	}

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

	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
}
