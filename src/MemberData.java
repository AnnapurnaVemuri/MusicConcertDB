import java.util.ArrayList;
import java.util.List;

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
		return null;
	}

	private void populateDetailsOfConcerts() {
		for (Concert concert: likedConcert) {
			concert.populateDetailsOfConcert(helper);
		}
	}
	
}
