package se.amt.webshopauthgroup7.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.amt.webshopauthgroup7.model.AppUser;
import se.amt.webshopauthgroup7.repository.AppUserRepository;

import java.util.List;

@RestController
@RequestMapping("/appusers")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserRepository appUserRepository;

    @GetMapping
    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }
}
