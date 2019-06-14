package glit.onetable.model.vo;

public class Unit {
	private int unitIdx;
	private String unitName;
	private double amount;
	private boolean minUnit;

	public boolean isMinUnit() {
		return minUnit;
	}

	public void setMinUnit(boolean minUnit) {
		this.minUnit = minUnit;
	}

	public int getUnitIdx() {
		return unitIdx;
	}

	public void setUnitIdx(int unitIdx) {
		this.unitIdx = unitIdx;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


}
