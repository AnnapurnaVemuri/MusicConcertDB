import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	Set<Band> suggestedBandBasedOnConcert = new HashSet<Band>();
	Set<Artist> suggestedArtistBasedOnConcert = new HashSet<Artist>();
	Set<Concert> suggestedConcertBasedOnConcert = new HashSet<Concert>();
	Set<Album> suggestAlbumsBasedOnGenreLiked = new HashSet<Album>();
	Set<Band> suggestBandBasedOnGenreLiked = new HashSet<Band>();
	Set<Artist> suggestArtistBasedOnGenreLiked = new HashSet<Artist>();
	Set<Song> suggestSongBasedOnGenreLiked = new HashSet<Song>();
	Set<Band> suggestBandBasedOnAlbumLiked = new HashSet<Band>();
	Set<Artist> suggestArtistBasedOnBandLiked = new HashSet<Artist>();
	
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
		getRecommendationsFromLikedConcerts();
		suggestAlbumsBasedOnGenresLiked();
		suggestBandBasedOnGenresLiked();
		suggestArtistsBasedOnGenresLiked();
		suggestSongsBasedOnGenresLiked();
		suggestBandsBasedOnAlbumsLiked();
		suggestArtistBasedOnBandsLiked();
		return getJsonOfData();
	}

	private void suggestArtistBasedOnBandsLiked() {
		for (Band b : likedBands) {
			Set<Artist> artists = b.suggestArtists(helper);
			for (Artist a : artists) {
				if (!likedArtists.contains(a)) {
					suggestArtistBasedOnBandLiked.add(a);
				}
			}
		}
	}

	private void suggestBandsBasedOnAlbumsLiked() {
		for (Album a : likedAlbums) {
			Set<Band> bands = a.suggestBands(helper);
			for (Band b : bands) {
				if (!likedBands.contains(b)) {
					b.getBandMembers(helper);
					suggestBandBasedOnAlbumLiked.add(b);
				}
			} 
		}
	}

	private void suggestAlbumsBasedOnGenresLiked() {
		for (Genre g : likedGenres) {
			Set<Album> albumsInGenre = helper.getAlbumOfGenre(g.getGenreId());
			for (Album a : albumsInGenre) {
				if (!likedAlbums.contains(a)) {
					a.populateDataOfAlbum(helper);
					suggestAlbumsBasedOnGenreLiked.add(a);
				}
			}
		}
	}
	
	private void suggestBandBasedOnGenresLiked() {
		for (Genre g : likedGenres) {
			Set<Band> bandsInGenre = helper.getBandsOfGenre(g.getGenreId());
			for (Band b : bandsInGenre) {
				if (!likedBands.contains(b)) {
					b.getBandMembers(helper);
					suggestBandBasedOnGenreLiked.add(b);
				}
			}
		}
	}
	
	private void suggestArtistsBasedOnGenresLiked() {
		for (Genre g : likedGenres) {
			Set<Artist> artistInGenre = helper.getArtistsOfGenre(g.getGenreId());
			for (Artist a : artistInGenre) {
				if (!likedArtists.contains(a)) {
					suggestArtistBasedOnGenreLiked.add(a);
				}
			}
		}
	}
	
	private void suggestSongsBasedOnGenresLiked() {
		for (Genre g : likedGenres) {
			Set<Song> songsInGenre = helper.getSongsOfGenre(g.getGenreId());
			for (Song s : songsInGenre) {
				if (!likedSongs.contains(s)) {
					s.populateDetailsOfSong(helper);
					suggestSongBasedOnGenreLiked.add(s);
				}
			}
		}
	}

	private void getRecommendationsFromLikedConcerts() {
		for (Concert c: likedConcert) {
			Set<Band> suggestedBands = c.suggestBands(helper);
			for (Band b : suggestedBands) {
				if (!likedBands.contains(b)) {
					suggestedBandBasedOnConcert.add(b);
				}
			}
			Set<Artist> suggestedArtists = c.suggestArtists(helper);
			for (Artist a : suggestedArtists) {
				if (!likedArtists.contains(a)) {
					suggestedArtistBasedOnConcert.add(a);
				}
			}
			Set<Concert> suggestedConcerts = c.suggestConcerts(helper);
			for (Concert c1 : suggestedConcerts) {
				if (!likedConcert.contains(c1)) {
					suggestedConcertBasedOnConcert.add(c1);
				}
			}
		}
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
		JsonArray suggBandConcertArray = new JsonArray();
		for (Band b: suggestedBandBasedOnConcert) {
			suggBandConcertArray.add(b.getJsonObject());
		}
		mainObj.add("bandsBasedOnConcert", suggBandConcertArray);
		JsonArray suggArtistConcertArray = new JsonArray();
		for (Artist a: suggestedArtistBasedOnConcert) {
			suggArtistConcertArray.add(a.getJsonObject());
		}
		mainObj.add("artistsBasedOnConcert", suggArtistConcertArray);
		JsonArray suggConcertOnConcertArray = new JsonArray();
		for (Concert c: suggestedConcertBasedOnConcert) {
			suggConcertOnConcertArray.add(c.getJsonObject());
		}
		mainObj.add("concertsBasedOnConcert", suggConcertOnConcertArray);
		JsonArray suggAlbumOnGenreArray = new JsonArray();
		for (Album a: suggestAlbumsBasedOnGenreLiked) {
			suggAlbumOnGenreArray.add(a.getJsonObject());
		}
		mainObj.add("albumsBasedOnGenre", suggAlbumOnGenreArray);
		JsonArray suggBandOnGenreArray = new JsonArray();
		for (Band b: suggestBandBasedOnGenreLiked) {
			suggBandOnGenreArray.add(b.getJsonObject());
		}
		mainObj.add("bandsBasedOnGenre", suggBandOnGenreArray);
		JsonArray suggArtistOnGenreArray = new JsonArray();
		for (Artist a: suggestArtistBasedOnGenreLiked) {
			suggArtistOnGenreArray.add(a.getJsonObject());
		}
		mainObj.add("artistsBasedOnGenre", suggArtistOnGenreArray);
		JsonArray suggSongsOnGenreArray = new JsonArray();
		for (Song s: suggestSongBasedOnGenreLiked) {
			suggSongsOnGenreArray.add(s.getJsonObject());
		}
		mainObj.add("songsBasedOnGenre", suggSongsOnGenreArray);
		JsonArray suggBandOnAlbumArray = new JsonArray();
		for (Band b: suggestBandBasedOnAlbumLiked) {
			suggBandOnAlbumArray.add(b.getJsonObject());
		}
		mainObj.add("bandsBasedOnAlbum", suggBandOnAlbumArray);
		JsonArray suggArtistOnBandArray = new JsonArray();
		for (Artist a: suggestArtistBasedOnBandLiked) {
			suggArtistOnBandArray.add(a.getJsonObject());
		}
		mainObj.add("artistsBasedOnBand", suggArtistOnBandArray);
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
