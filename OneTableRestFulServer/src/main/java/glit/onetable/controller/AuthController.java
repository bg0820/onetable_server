package glit.onetable.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import glit.onetable.enums.ErrorCode;
import glit.onetable.exception.CustomException;
import glit.onetable.mapper.AuthMapper;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.EmailServiceImpl;
import glit.onetable.model.request.Login;
import glit.onetable.model.request.Register;
import glit.onetable.model.vo.User;
import glit.onetable.util.Util;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

	@Autowired
	AuthMapper authMapper;
	@Autowired
	private JavaMailSender javaMailSender;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> login(
			@RequestHeader(value = "API_Version") String version,
			@NotBlank(message = "아이디를 입력해 주세요.") @Size(max = 20) @RequestParam String id,
			@NotBlank(message = "비밀번호를 입력해 주세요.") @Size(min = 8) @RequestParam String pw)
			throws CustomException {

		ApiResponseResult<Object> resResult =
				new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;
		String pwHash = Util.SHA256(pw);

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		User user = new User();
		user.setId(id);
		user.setPw(pwHash);

		User resultUser = authMapper.login(user);
		if (resultUser == null)
			throw new CustomException(ErrorCode.LOGIN_FAILED);

		resResult.setData(resultUser);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ApiResponseResult> register(
			@RequestHeader(value = "API_Version") String version,
			@RequestParam String id,
			@RequestParam String pw,
			@RequestParam String nickname,
			@Email @RequestParam String email,
			@RequestParam String birthday) throws CustomException {
		ApiResponseResult<Object> resResult =
				new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.CREATED;

		String pwHash = Util.SHA256(pw);

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		// token Generator
		UUID uuid = UUID.randomUUID();
		Calendar calendar = Calendar.getInstance();
		Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode(id.getBytes());
		String accessToken =
				new String(encodedBytes) + "_" + uuid.toString() + "-" + calendar.getTimeInMillis();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		java.util.Date birthdayDate = null;
		try {
			birthdayDate = sdf.parse(birthday);
		} catch (ParseException e) {
			throw new CustomException(ErrorCode.EMAIL_TYPE_INVALID);
		}

		User user = new User();
		user.setId(id);
		user.setPw(pwHash);
		user.setNickname(nickname);
		user.setEmail(email);
		user.setBirthday(new java.sql.Date(birthdayDate.getTime()));
		user.setToken(accessToken.replaceAll("-", "").replaceAll("_", "").replaceAll("=", ""));

		authMapper.registerInsert(user);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/register/duplicate/id", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> registerDuplicateId(
			@RequestHeader(value = "API_Version") String version,
			@RequestParam String id) throws CustomException {
		ApiResponseResult<Object> resResult =
				new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		int idExists = authMapper.registerDuplicateId(id);
		resResult.setData(idExists);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/register/duplicate/email", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> registerDuplicateEmail(
			@RequestHeader(value = "API_Version") String version,
			@RequestParam String email) throws CustomException {
		ApiResponseResult<Object> resResult =
				new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		int emailExists = authMapper.registerDuplicateEmail(email);
		resResult.setData(emailExists);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/register/duplicate/nickname", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> registerDuplicateNickname(
			@RequestHeader(value = "API_Version") String version,
			@RequestParam String nickname) throws CustomException {
		ApiResponseResult<Object> resResult =
				new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		int nicknameExists = authMapper.registerDuplicateNickname(nickname);
		resResult.setData(nicknameExists);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/user/{token}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> getUser(
			@RequestHeader(value = "API_Version") String version,
			@PathVariable String token) throws CustomException {
		ApiResponseResult<Object> resResult =
				new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		User resultUser = authMapper.getUser(token);
		if (resultUser == null)
			throw new CustomException(ErrorCode.UNTOKENIZED);

		resResult.setData(resultUser);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}


	@RequestMapping(value = "/find/id", method = RequestMethod.POST)
	public ResponseEntity<ApiResponseResult> findId(
			@RequestHeader(value = "API_Version") String version,
			@RequestParam String email) throws CustomException {
		ApiResponseResult<Object> resResult =
				new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		// 가입되어 있지 않은 이메일일 경우
		int emailExists = authMapper.registerDuplicateEmail(email);
		if (emailExists == 0)
			throw new CustomException(ErrorCode.NON_REGISTERED);


		User user = authMapper.idFindToEmailChange(email);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("onetable@onetable.com");
        message.setTo(email);
        message.setSubject("한상차림  - 아이디 찾기");
        message.setText("아이디 : " + user.getId());
        javaMailSender.send(message);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/find/pw", method = RequestMethod.POST)
	public ResponseEntity<ApiResponseResult> findId(
			@RequestHeader(value = "API_Version") String version,
			@RequestParam String id,
			@RequestParam String email) throws CustomException {
		ApiResponseResult<Object> resResult =
				new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		int emailExists = authMapper.registerDuplicateEmail(email);
		if (emailExists == 0)
			throw new CustomException(ErrorCode.NON_REGISTERED);

		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
			int rIndex = rnd.nextInt(3);
			switch (rIndex) {
				case 0:
					// a-z
					temp.append((char) ((int) (rnd.nextInt(26)) + 97));
					break;
				case 1:
					// A-Z
					temp.append((char) ((int) (rnd.nextInt(26)) + 65));
					break;
				case 2:
					// 0-9
					temp.append((rnd.nextInt(10)));
					break;
			}
		}

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("onetable@onetable.com");
        message.setTo(email);
        message.setSubject("한상차림 - 비밀번호 찾기");
        message.setText("변경된 비밀번호 : " + temp.toString());
        javaMailSender.send(message);
        

		User user = new User();
		user.setEmail(email);
		user.setPw(Util.SHA256(temp.toString()));

		authMapper.pwFindToEmailChange(user);


		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}
}
