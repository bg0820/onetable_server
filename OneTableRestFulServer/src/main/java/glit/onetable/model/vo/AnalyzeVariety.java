package glit.onetable.model.vo;

public class AnalyzeVariety {
	private String name;
	private String img;
	private String price;
	private String perUnit = "정보 미제공";
	// db
	private String idx;
	private String variety;
	//
	private String dispalyName;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPerUnit() {
		return perUnit;
	}

	public void setPerUnit(String perUnit) {
		this.perUnit = perUnit;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		idx = idx;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public String getDispalyName() {
		return dispalyName;
	}

	public void setDispalyName(String dispalyName) {
		this.dispalyName = dispalyName;
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
		return "[displayName=" + dispalyName + ", unitNum=" + unitNum + ", unitStr=" + unitStr
				+ "]";
	}



}
