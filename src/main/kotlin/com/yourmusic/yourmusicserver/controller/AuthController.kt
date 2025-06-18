import com.yourmusic.yourmusicserver.dto.LoginRequestDTO
import com.yourmusic.yourmusicserver.dto.SignupRequestDTO
import com.yourmusic.yourmusicserver.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController (
    private val userService: UserService,
){
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED) // 성공했을 때 HTTP형식으로 가게 하는 거
    fun signup(@RequestBody request: SignupRequestDTO) {
        userService.signup(request)
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody request: LoginRequestDTO) {
    }
}