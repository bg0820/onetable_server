package glit.onetable.model.vo;

public class AnalyzeVariety {
	private String name;
	private String img;
	private int price;
	private String perUnit = "정보 미제공";
	// db
	private String itemId;
	//private String variety;
	//
	private String displayName;
	private double unitNum;
	private String unitStr;

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPerUnit() {
		return perUnit;
	}

	public void setPerUnit(String perUnit) {
		this.perUnit = perUnit;
	}


	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/*

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}
	*/

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public double getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(double unitNum) {
		this.unitNum = unitNum;
	}

	public String getUnitStr() {
		return unitStr;
	}

	public void setUnitStr(String unitStr) {
		this.unitStr = unitStr;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[displayName=" + displayName + ", unitNum=" + unitNum + ", unitStr=" + unitStr
				+ "]";
	}



}
