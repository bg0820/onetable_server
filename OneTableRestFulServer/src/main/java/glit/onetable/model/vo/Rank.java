package glit.onetable.model.vo;

import java.sql.Date;

public class Rank {

	String nickName;
	String recipeName; // recipe에 대한 이름
	Date birth;
	int age; // birth 된 거에서 계산할 것
	int userIdx;
	int recipeIdx;
	double gradePoint;
	int rank;
	int recipeCount;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

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

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRecipeCount() {
		return recipeCount;
	}

	public void setRecipeCount(int recipeCount) {
		this.recipeCount = recipeCount;
	}

	@Override
	public String toString() {
		return "Rank [nickName=" + nickName + ", recipeName=" + recipeName + ", birth=" + birth
				+ ", age=" + age + ", userIdx=" + userIdx + ", recipeIdx=" + recipeIdx
				+ ", gradePoint=" + gradePoint + ", rank=" + rank + ", recipeCount=" + recipeCount
				+ "]";
	}



}
