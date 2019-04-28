package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientsSubject {
	private String IngredientsSubjectUUID;
	private String variety;

	public String getIngredientsSubjectUUID() {
		return IngredientsSubjectUUID;
	}

	public void setIngredientsSubjectUUID(String ingredientsSubjectUUID) {
		IngredientsSubjectUUID = ingredientsSubjectUUID;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}


}
