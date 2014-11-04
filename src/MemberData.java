import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MemberData {
	String name;
	int memberid;
	DatabaseHelper helper;
	List<Concert> likedConcert = new ArrayList<Concert>();
	List<Album> likedAlbums = new ArrayList<Album>();
	List<Band> likedBands = new ArrayList<Band>();
	List<Artist> likedArtists = new ArrayList<Artist>();
	List<Song> likedSongs = new ArrayList<Song>();
	List<Genre> likedGenres = new ArrayList<Genre>();
	
	public MemberData(String name, DatabaseHelper helper) {
		this.name = name;
		this.helper = helper;
	}

	public String getDataOfMemberAsJson(String username) {
		memberid = helper.getMemberId(username);
		likedConcert = helper.getLikedConcerts(memberid);
		likedBands = helper.getLikedBands(memberid);
		likedAlbums = helper.getLikedAlbums(memberid);
		likedArtists = helper.getLikedArtists(memberid);
		likedSongs = helper.getLikedSongs(memberid);
		likedGenres = helper.getLikedGenre(memberid);
		populateDetailsOfConcerts();
		populateDetailsOfBands();
		populateDetailsOfAlbums();
		populateDetailsOfSongs();
		return getJsonOfData();
	}

	private String getJsonOfData() {
		JsonObject mainObj = new JsonObject();
		mainObj.addProperty("status", 1);
		JsonArray concertArray = new JsonArray();
		for (Concert c: likedConcert) {
			concertArray.add(c.getJsonObject());
		}
		mainObj.add("likedConcerts", concertArray);
		JsonArray bandArray = new JsonArray();
		for (Band b: likedBands) {
			bandArray.add(b.getJsonObject());
		}
		mainObj.add("likedBands", bandArray);
		JsonArray albumArray = new JsonArray();
		for (Album a: likedAlbums) {
			albumArray.add(a.getJsonObject());
		}
		mainObj.add("likedAlbums", albumArray);
		JsonArray songArray = new JsonArray();
		for (Song s: likedSongs) {
			songArray.add(s.getJsonObject());
		}
		mainObj.add("likedSongs", songArray);
		JsonArray artistArray = new JsonArray();
		for (Artist a: likedArtists) {
			artistArray.add(a.getJsonObject());
		}
		mainObj.add("likedArtists", artistArray);
		return mainObj.toString();
	}

	private void populateDetailsOfSongs() {
		for (Song song : likedSongs) {
			song.populateDetailsOfSong(helper);
		}
	}

	private void populateDetailsOfAlbums() {
		for (Album album : likedAlbums) {
			album.populateDataOfAlbum(helper);
		}
	}

	private void populateDetailsOfBands() {
		for (Band band : likedBands) {
			band.getBandMembers(helper);
		}
	}

	private void populateDetailsOfConcerts() {
		for (Concert concert: likedConcert) {
			concert.populateDetailsOfConcert(helper);
		}
	}
	
}
