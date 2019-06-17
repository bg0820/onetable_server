package glit.onetable.model.vo;

import java.util.List;

public class OnetableUser {
	private int onetablesetIdx;
	private String name;
	private int userIdx;
	private String nickName;
	private String profileImg;
	
	private List<OnetableDetail> onetableDetail;

	public int getOnetablesetIdx() {
		return onetablesetIdx;
	}

	public void setOnetablesetIdx(int onetablesetIdx) {
		this.onetablesetIdx = onetablesetIdx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public List<OnetableDetail> getOnetableDetail() {
		return onetableDetail;
	}

	public void setOnetableDetail(List<OnetableDetail> onetableDetail) {
		this.onetableDetail = onetableDetail;
	}

}
