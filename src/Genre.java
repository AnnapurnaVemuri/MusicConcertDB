public class Genre {
	int genreId;
	String name;
	
	public Genre(int genreId, String name) {
		this.genreId = genreId;
		this.name = name;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Genre.class) {
			return false;
		}
		return ((Genre)obj).getGenreId() == this.genreId;
	}
	
}
