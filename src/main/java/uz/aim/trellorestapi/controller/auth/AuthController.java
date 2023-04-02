package uz.aim.trellorestapi.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.dtos.auth.LoginDTO;
import uz.aim.trellorestapi.dtos.auth.RegisterDTO;
import uz.aim.trellorestapi.dtos.jwt.JwtResponseDTO;
import uz.aim.trellorestapi.dtos.jwt.RefreshTokenDTO;
import uz.aim.trellorestapi.dtos.response.ApiResponse;
import uz.aim.trellorestapi.services.auth.AuthService;
import uz.aim.trellorestapi.services.recaptcha.ReCaptchaService;

import javax.validation.Valid;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 13:00
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ReCaptchaService reCaptchaService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {
        ApiResponse<User> apiResponse = authService.register(dto);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PostMapping("/registerReCaptcha")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto, @RequestParam(name="g-recaptcha-response") String captcha) {
        if (reCaptchaService.validateCaptcha(captcha)) {
        ApiResponse<User> apiResponse = authService.register(dto);
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Verify Captcha");
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponseDTO> refreshToken(@RequestBody RefreshTokenDTO dto) {
        return ResponseEntity.ok(authService.refreshToken(dto));
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam(name = "activation_code") String activationCode) {
        return ResponseEntity.status(200).body(authService.activateUser(activationCode) ? "User successfully activate" : "Failed ... !");
    }

}
