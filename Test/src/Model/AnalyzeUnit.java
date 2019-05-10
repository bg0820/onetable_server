package Model;

public class AnalyzeUnit {
	private double unitAmount;
	private String unitStr;


	public double getUnitAmount() {
		return unitAmount;
	}

	public void setUnitAmount(double unitAmount) {
		this.unitAmount = unitAmount;
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
		return "[unitAmount=" + unitAmount + ", unitStr=" + unitStr+ "]";
	}

}
