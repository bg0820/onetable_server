package glit.onetable.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Register {
	@NotBlank(message="아이디를 입력해 주세요.")
	@Size(max = 20)
	private String id;
	
	@NotBlank(message="비밀번호를 입력해 주세요.")
	@Size(min = 8)
	//@Min(value=2 message="최소 8자 이상이어야합니다.")
	private String pw;
	
	@NotBlank(message="닉네임을 입력해 주세요.")
	private String nickname;
	
	@NotBlank(message="이메일을 입력해 주세요.")
	@Email(message = "이메일의 양식을 지켜주세요.")
	private String email;
	
	@NotBlank(message="생년월일을 입력해 주세요.")
	private String birthday;

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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	
}
