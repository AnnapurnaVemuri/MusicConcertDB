import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Band {
	int id;
	String name, webpage;
	List<Artist> bandMembers;
	
	public Band(int id, String name, String webpage) {
		this.id = id;
		this.name = name;
		this.webpage = webpage;
	}
	
	public void getBandMembers(DatabaseHelper helper) {
		bandMembers = helper.getMembersOfBand(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	
	public String getJsonObjectString() {
		return getJsonObject().toString();
	}

	public JsonElement getJsonObject() {
		JsonObject mainObj = new JsonObject();
		mainObj.addProperty("name", name);
		mainObj.addProperty("webpage", webpage);
		JsonArray artistArray = new JsonArray();
		for (Artist a: bandMembers) {
			artistArray.add(a.getJsonObject());
		}
		mainObj.add("artistsInBand", artistArray);
		return mainObj;
	}
	
}
