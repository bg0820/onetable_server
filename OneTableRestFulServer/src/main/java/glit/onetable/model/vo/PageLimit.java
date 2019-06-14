package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PageLimit {
	public int startNum;
	public int itemNum;

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

}
