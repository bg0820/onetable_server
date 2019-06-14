package glit.onetable.model.vo;

public class Evaluate {

	int userIdx;
	int recipeIdx;
	double gradePoint;

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public int getRecipeIdx() {
		return recipeIdx;
	}

	public void setRecipeIdx(int recipeIdx) {
		this.recipeIdx = recipeIdx;
	}

	public double getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(double gradePoint) {
		this.gradePoint = gradePoint;
	}

	@Override
	public String toString() {
		return "evaluate [userIdx=" + userIdx + ", recipeIdx=" + recipeIdx + ", gradePoint=" + gradePoint + "]";
	}

}
