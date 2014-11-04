import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Concert {
	int concertId;
	String concertName, venue, url;
	Date date_of_concert;
	Set<Genre> genre;
	List<Band> bandsPerforming = new ArrayList<Band>();
	List<Artist> artistsPerforming = new ArrayList<Artist>();
	
	public Concert(int concertId, String concertName, String dateStr, String venue, String url) {
		this.concertId = concertId;
		this.concertName = concertName;
		this.venue = venue;
		this.url = url;
		try {
			this.date_of_concert = new SimpleDateFormat("dd-MMM-yy").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void populateDetailsOfConcert(DatabaseHelper helper) {
		bandsPerforming = helper.getBandsPerformingInConcert(concertId);
		artistsPerforming = helper.getArtistsPerformingInConcert(concertId);
		genre.addAll(helper.getGenreOfBands(bandsPerforming));
		genre.addAll(helper.getGenreOfArtists(artistsPerforming));
	}
}
