import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Artist {
	int id;
	String name, webpage;

	public Artist(int id, String name, String webpage) {
		this.id = id;
		this.name = name;
		this.webpage = webpage;
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
		return mainObj;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Artist.class && ((Artist)obj).id == this.id) {
			return true;
		}
		return false;
	}
}
