package glit.onetable.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
	private int userIdx;
	private String token;
	private String id;
	private String pw;
	private String nickname;
	private String email;
	private Date birthday;
	private Timestamp regTime;
	private String profileImgUrl;
	private int status;

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}





	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPw() {
		return pw;
	}


	public void setPw(String pw) {
		this.pw = pw;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public Timestamp getRegTime() {
		return regTime;
	}


	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}


	public String getProfileImgUrl() {
		return profileImgUrl;
	}


	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}


	public int getUserIdx() {
		return userIdx;
	}


	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

	


}
