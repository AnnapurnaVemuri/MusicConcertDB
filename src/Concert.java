import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Concert {
	int concertId;
	String concertName, venue, url;
	String date_of_concert;
	Set<Genre> genre = new HashSet<Genre>();
	List<Band> bandsPerforming = new ArrayList<Band>();
	List<Artist> artistsPerforming = new ArrayList<Artist>();
	Set<Artist> suggestedArtists = new HashSet<Artist>();
	Set<Band> suggestedBands = new HashSet<Band>();
	Set<Concert> suggestedConcerts = new HashSet<Concert>();
	
	public Concert(int concertId, String concertName, String dateStr, String venue, String url) {
		this.concertId = concertId;
		this.concertName = concertName;
		this.venue = venue;
		this.url = url;
		this.date_of_concert = dateStr;
	}
	
	public void populateDetailsOfConcert(DatabaseHelper helper) {
		bandsPerforming = helper.getBandsPerformingInConcert(concertId);
		artistsPerforming = helper.getArtistsPerformingInConcert(concertId);
		genre.addAll(helper.getGenreOfBands(bandsPerforming));
		genre.addAll(helper.getGenreOfArtists(artistsPerforming));
		for (Band band : bandsPerforming) {
			band.getBandMembers(helper);
		}
	}

	public String getJsonObjectAsString() {
		return getJsonObject().toString();
	}
	
	public JsonElement getJsonObject() {
		JsonObject mainObj = new JsonObject();
		mainObj.addProperty("name", concertName);
		mainObj.addProperty("date", date_of_concert);
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Concert.class && ((Concert)obj).concertId == this.concertId) {
			return true;
		}
		return false;
	}

	public Set<Band> suggestBands(DatabaseHelper helper) {
		Set<Band> suggestedBands = new HashSet<Band>();
		for (Genre g: genre) {
			suggestedBands.addAll(helper.getBandsOfGenre(g.getGenreId()));
		}
		for (Band b : suggestedBands) {
			b.getBandMembers(helper);
		}
		this.suggestedBands = suggestedBands;
		return suggestedBands;
	}

	public Set<Artist> suggestArtists(DatabaseHelper helper) {
		Set<Artist> suggestedArtists = new HashSet<Artist>();
		for (Genre g: genre) {
			suggestedArtists.addAll(helper.getArtistsOfGenre(g.getGenreId()));
		}
		this.suggestedArtists = suggestedArtists;
		return suggestedArtists;
	}

	public Set<Concert> suggestConcerts(DatabaseHelper helper) {
		Set<Concert> suggestedConcerts = new HashSet<Concert>();
		if (suggestedBands != null) {
			for (Band b : suggestedBands) {
				suggestedConcerts.addAll(helper.getConcertsOfBand(b.getId()));
			}
		}
		if (suggestedArtists != null) {
			for (Artist a : suggestedArtists) {
				suggestedConcerts.addAll(helper.getConcertsOfArtist(a.getId()));
			}
		}
		for (Concert c: suggestedConcerts) {
			c.populateDetailsOfConcert(helper);
		}
		this.suggestedConcerts = suggestedConcerts;
		return suggestedConcerts;
	}
}
