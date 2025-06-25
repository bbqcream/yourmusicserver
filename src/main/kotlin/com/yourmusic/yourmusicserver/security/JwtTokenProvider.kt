package com.yourmusic.yourmusicserver.security

import com.yourmusic.yourmusicserver.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenProvider (
    private val userService: UserService,
    private val userDetailsService: UserDetailsService
){

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    // 30분
    private val tokenValidTime: Long = 30 * 60 * 1000L


    // TODO: JWT 생성/검증 로직 작성
    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected fun init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    // 토큰 생성하기
    public fun createToken(userPk: String, roles: List<String>): String {
        val claims: Claims = Jwts.claims().setSubject(userPk) // jwt에 저장되는 저장 단위
        claims.put("roles", roles)
        val now = Date()
        return Jwts.builder()
            .setClaims(claims) // 정보 저장
            .setIssuedAt(now)
            .setExpiration(Date(now.getTime() + tokenValidTime)) // Expire Time 설정
            .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
            // signature 에 들어갈 secret값 세팅
            .compact();
    }
    // jwt 토큰에서 인증 정보 조회
    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(this.getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities())
    }

    // 토큰에서 회원 정보 추출
    public fun getUserPk(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).body.subject
    }

    // header에서 값 들고오기
    public fun resolveToken(req: HttpServletRequest): String? {
        return req.getHeader("X-Auth-Token")
    }
    // 토큰 만료일자, 유효성 확인
    public fun vaildateToken(jwtToken: String) : Boolean{
        try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken)
            return !claims.body.expiration.before(Date())
        }catch(e:Exception){
            return false
        }
    }

}
