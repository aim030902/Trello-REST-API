package uz.aim.trellorestapi.services.recaptcha;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uz.aim.trellorestapi.dtos.response.ReCaptchaResponse;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 12:03
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
public class ReCaptchaService {

    private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateCaptcha(String captchaResponse){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        String RECAPTCHA_SECRET = "6LeYNvkkAAAAAEXMiByqL53bSzNJ3-VBkrgJf8gv";
        requestMap.add("secret", RECAPTCHA_SECRET);
        requestMap.add("response", captchaResponse);

        ReCaptchaResponse recaptchaResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, requestMap, ReCaptchaResponse.class);
        if(recaptchaResponse == null){
            return false;
        }

        return Boolean.TRUE.equals(recaptchaResponse.isSuccess());
    }
}