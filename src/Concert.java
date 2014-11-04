import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

	public String getJsonObjectAsString() {
		return getJsonObject().toString();
	}
	
	public JsonElement getJsonObject() {
		JsonObject mainObj = new JsonObject();
		mainObj.addProperty("name", concertName);
		mainObj.addProperty("date", date_of_concert.toString());
		mainObj.addProperty("venue", venue);
		mainObj.addProperty("url", url);
		JsonArray bandArray = new JsonArray();
		for (Band b: bandsPerforming) {
			bandArray.add(b.getJsonObject());
		}
		mainObj.add("bandsPerf", bandArray);
		JsonArray artistArray = new JsonArray();
		for (Artist a: artistsPerforming) {
			artistArray.add(a.getJsonObject());
		}
		mainObj.add("artistsPerf", artistArray);
		return mainObj;
	}
}
