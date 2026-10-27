package se.amt.webshopauthgroup7.dto;

import java.util.List;

public record SessionResponse(
        long expiresIn,
        String username,
        List<String> roles
) {
}
