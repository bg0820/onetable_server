package Model;

public class Variety {
	// ssg
	private String name;
	private String img;
	private String price;
	private String unit = "정보 미제공";
	// db
	private  String UUID;
	private String variety;

	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uuid) {
		this.UUID = uuid;
	}
	public String getVariety() {
		return variety;
	}
	public void setVariety(String vairety) {
		this.variety = vairety;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

}
