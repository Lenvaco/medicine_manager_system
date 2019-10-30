package com.medicine.manager.common.utils;

import com.medicine.manager.bean.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author lenvaco
 * @date 2019/9/26 19:08
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 6391021095715243485L;

	private Clock clock = DefaultClock.INSTANCE;
	//设置过期时间为1个小时
	@Value("${jwt.expiration}")
	private long EXPIRATION = 1000*60*60*1;
	//选择记住我后过期时间设置为7天
	private static final long EXPIRATION_REMEMBER = 1000*60*60*24*7;
	//私钥
	@Value("${jwt.secret}")
	private String TOKEN_SECRET;
	//加密算法
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
	@Value("${jwt.header}")
	private String tokenHeader;
	/**
	 * claims相当于一个map，包含信息
	 * @param token
	 * @param claimsResolver
	 * @param <T>
	 * @return
	 */
	public  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	private  Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(TOKEN_SECRET)
				.parseClaimsJws(token)
				.getBody();
	}
	/**
	 * @param token
	 * @return	签发时间
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	/**
	 *
	 * @param token
	 * @return 签发日期
	 */
	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}
	/**
	 *
	 * @param token
	 * @return 过期日期
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * 判断token是否过期
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(clock.now());
	}

	/**
	 * 判断token创建时间是否比更新信息时间晚
	 * @param createTokenTime
	 * @param updateTime
	 * @return
	 */
	private Boolean isCreatedBeforeUpadate(Date createTokenTime, Date updateTime) {
		return (updateTime != null && createTokenTime.before(updateTime));
	}

	/**
	 * 忽略token的过期
	 * @param token
	 * @return
	 */
	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	/**
	 * 计算过期时间
	 * @param createdDate
	 * @return
	 */
	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + EXPIRATION_REMEMBER);
	}

	/**
	 *
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(SIGNATURE_ALGORITHM, TOKEN_SECRET)
				.compact();
	}

	/**
	 * 生成token
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	/**
	 * 判断token是否可以被刷新
	 * @param token
	 * @param updateDate
	 * @return
	 */
	public Boolean canTokenBeRefreshed(String token, Date updateDate) {
		final Date created = getIssuedAtDateFromToken(token);
		return !isCreatedBeforeUpadate(created, updateDate)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	/**
	 * 刷新token
	 * @param token
	 * @return
	 */
	public String refreshToken(String token) {
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);
		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SIGNATURE_ALGORITHM, TOKEN_SECRET)
				.compact();
	}
	public Boolean validateToken(String token, UserDetails userDetails) {
		JwtUser user = (JwtUser) userDetails;
		final Date created = getIssuedAtDateFromToken(token);
		//        final Date expiration = getExpirationDateFromToken(token);
		//        如果token存在，且token创建日期 > 最后修改密码的日期 则代表token有效
		return (!isTokenExpired(token)
				&& !isCreatedBeforeUpadate(created, user.getModifyTime())
		);
	}

}
