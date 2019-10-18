package com.medicine.manager.web.controller.authorization;

import cn.hutool.core.util.IdUtil;
import com.google.common.io.BaseEncoding;
import com.medicine.manager.bean.AuthenticationInfo;
import com.medicine.manager.bean.AuthorizationUser;
import com.medicine.manager.bean.ImageResult;
import com.medicine.manager.bean.JwtUser;
import com.medicine.manager.common.utils.JwtTokenUtil;
import com.medicine.manager.common.utils.SecurityUtil;
import com.medicine.manager.exception.BadRequestException;
import com.medicine.manager.service.JwtUserService;
import com.medicine.manager.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.medicine.manager.common.utils.VerifyCodeUtil.drawCaptchaImage;

/**
 * @author lenvaco
 * @date 2019/9/26 19:05
 */
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthorizationController {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private BCryptPasswordEncoder  bCryptPasswordEncoder;
	@Autowired
	private RedisService redisService;
	@Autowired
	private JwtUserService jwtUserService;

	@PostMapping(value = "login")
	public ResponseEntity login(@Validated @RequestBody AuthorizationUser authorizationUser){
		// 查询验证码
		String captcha = redisService.getCodeVal(authorizationUser.getUuid());
		// 清除验证码
		redisService.delete(authorizationUser.getUuid());
		if (Strings.isBlank(captcha)) {
			throw new BadRequestException("验证码已过期");
		}
		if (Strings.isBlank(authorizationUser.getCaptcha()) || !authorizationUser.getCaptcha().equalsIgnoreCase(captcha)) {
			throw new BadRequestException("验证码错误");
		}
		final JwtUser jwtUser = (JwtUser) jwtUserService.loadUserByUsername(authorizationUser.getUsername());
		if(!bCryptPasswordEncoder.matches(authorizationUser.getPassword(), jwtUser.getPassword())){
			System.out.println("jwtUser.getPassword(), is " + jwtUser.getPassword() + "     authorizationUser.getPassword() is "+authorizationUser.getPassword() );
			throw new AccountExpiredException("密码错误");
		}
		// 生成令牌
		final String token = jwtTokenUtil.generateToken(jwtUser);
		// 返回 token
		return ResponseEntity.ok(new AuthenticationInfo(token,jwtUser));
	}

	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public ImageResult captcha() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		String uuid = IdUtil.simpleUUID();
		String verifyCode = drawCaptchaImage(byteArrayOutputStream);
		redisService.saveCode(uuid,verifyCode);
		try {
			return new ImageResult(uuid, BaseEncoding.base64().encode(byteArrayOutputStream.toByteArray()));
		} catch (Exception ex) {
			return null;
		} finally {
			byteArrayOutputStream.close();
		}
	}

	/**
	 * 获取用户信息
	 * @return
	 */
	@GetMapping(value = "info")
	public ResponseEntity getUserInfo(){
		JwtUser jwtUser = (JwtUser)jwtUserService.loadUserByUsername(SecurityUtil.getUsername());
		return ResponseEntity.ok(jwtUser);
	}
}
