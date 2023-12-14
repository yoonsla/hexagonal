package com.example.blog.core.adpter;

import static com.example.blog.core.contants.Constants.USER_EMAIL;

import com.example.blog.core.adpter.command.LoginCommand;
import com.example.blog.core.adpter.response.Response;
import com.example.blog.core.adpter.response.RsaResponse;
import com.example.blog.core.adpter.response.UserWithTokenResponse;
import com.example.blog.core.annotation.AnonymousCallable;
import com.example.blog.core.service.port.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginUseCase loginUseCase;

    @AnonymousCallable
    @GetMapping("/rsa")
    public Response<RsaResponse> getPublicKey() {
        return Response.from(RsaResponse.from(loginUseCase.getPublicKey()));
    }

    @AnonymousCallable
    @PostMapping("/login")
    public Response<UserWithTokenResponse> login(@RequestBody LoginCommand request) {
        return Response.from(UserWithTokenResponse.from(loginUseCase.login(request)));
    }

    @AnonymousCallable
    @DeleteMapping("/logout")
    public Response<Void> logout(@RequestAttribute(USER_EMAIL) String email) {
        loginUseCase.logout(email);
        return Response.ok();
    }
}
