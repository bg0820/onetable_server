package glit.onetable.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {
	
	@NotBlank(message="아이디를 입력해 주세요.")
	@Size(max = 20)
	private String id;
	
	
	@NotBlank(message="비밀번호를 입력해 주세요.")
	@Size(min = 8)
	private String pw;

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
	
	
}
