package uz.aim.trellorestapi.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:11
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReCaptchaResponse {
    private boolean success;
    private String challenge_ts;
    private String hostname;

}