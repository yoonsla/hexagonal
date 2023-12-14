package com.example.blog.adapter.in;

import static com.example.blog.core.contants.Constants.HEADER_AUTHORIZATION;
import static com.example.blog.core.contants.Constants.HEADER_REFRESH_TOKEN;

import com.example.blog.adapter.in.command.CreateUserCommand;
import com.example.blog.application.port.in.UserUseCase;
import com.example.blog.core.adpter.command.ChangePasswordCommand;
import com.example.blog.core.adpter.response.RegenerateTokenResponse;
import com.example.blog.core.adpter.response.Response;
import com.example.blog.core.annotation.AnonymousCallable;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserUseCase userUseCase;

    @AnonymousCallable
    @PostMapping
    public Response<Void> create(@RequestBody CreateUserCommand request) {
        userUseCase.create(request);
        return Response.ok();
    }

    @AnonymousCallable
    @GetMapping(value = "/token")
    public Response<RegenerateTokenResponse> regenerate(
        @Parameter(description = "만료 된 access token") @RequestHeader(HEADER_AUTHORIZATION) String accessToken,
        @Parameter(description = "refresh token") @RequestHeader(HEADER_REFRESH_TOKEN) String refreshToken
    ) {
        return Response.from(RegenerateTokenResponse.from(userUseCase.regenerate(accessToken, refreshToken)));
    }

    @AnonymousCallable
    @PostMapping("/auth")
    public Response<Void> sendAuthCode(@RequestParam String email) {
        userUseCase.sendAuthCode(email);
        return Response.ok();
    }

    @PutMapping(value = "/change")
    public Response<Void> changePassword(@RequestBody ChangePasswordCommand request) {
        userUseCase.changePassword(request);
        return Response.ok();
    }
}
