import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Song {
	int id;
	String name, videoLink;
	Band band;
	Album album;
	Genre genre;
	Artist artist;
	
	public Song(int id, String name, String videoLink) {
		this.id = id;
		this.name = name;
		this.videoLink = videoLink;
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

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	
	public void populateDetailsOfSong(DatabaseHelper helper) {
		genre = helper.getGenreOfSong(id);	
		artist = helper.getArtistOfSong(id);
		band = helper.getBandOfSong(id);
		if (band != null) {
			band.getBandMembers(helper);
		}
		album = helper.getAlbumOfSong(id);
	}

	public JsonElement getJsonObject() {
		JsonObject mainObj = new JsonObject();
		mainObj.addProperty("name", name);
		mainObj.addProperty("link", videoLink);
		if (band != null) {
			mainObj.add("band", band.getJsonObject());
		}
		if (album != null) {
			mainObj.add("album", album.getJsonObject());
		}
		if (artist != null) {
			mainObj.add("artist", artist.getJsonObject());
		}
		return mainObj;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Song.class && ((Song)obj).id == this.id) {
			return true;
		}
		return false;
	}
}
