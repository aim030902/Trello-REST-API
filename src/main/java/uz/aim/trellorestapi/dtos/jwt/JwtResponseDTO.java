package uz.aim.trellorestapi.dtos.jwt;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:06
 * Project : Trello-REST-API / IntelliJ IDEA
 */

public record JwtResponseDTO(
        String accessToken,
        String refreshToken,
        String tokenType) {
}
