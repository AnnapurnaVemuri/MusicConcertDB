import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Album {
	int id;
	String name;
	Band band;
	Artist artist;
	List<Song> songsList;
	Genre genre;
	
	public Album(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void populateDataOfAlbum(DatabaseHelper helper) {
		genre = helper.getGenreOfAlbum(id);
		band = helper.getBandOfAlbum(id);
		if (band != null) {
			band.getBandMembers(helper);
		}
		artist = helper.getArtistOfAlbum(id);
		songsList = helper.getSongsInAlbum(id);
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

	public Band getBand() {
		return band;
	}

	public void setBand(Band band) {
		this.band = band;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public List<Song> getSongsList() {
		return songsList;
	}

	public void setSongsList(List<Song> songsList) {
		this.songsList = songsList;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public JsonElement getJsonObject() {
		JsonObject mainObj = new JsonObject();
		mainObj.addProperty("name", name);
		mainObj.add("band", band.getJsonObject());
		mainObj.add("artist", artist.getJsonObject());
		JsonArray songArray = new JsonArray();
		for (Song s: songsList) {
			songArray.add(s.getJsonObject());
		}
		mainObj.add("songsInAlbum", songArray);
		return mainObj;
	}
	
}
