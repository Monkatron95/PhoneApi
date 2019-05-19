package com.joshuamonk.phoneapi;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhoneapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneapiApplicationIT {

	@LocalServerPort
    private long port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testRetreiveAllPhoneNumbers() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        String requestUrl = "http://localhost:" + port + "/customers/numbers";

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET, entity, String.class);

        String expected = "[\"07876765434\",\"07876765435\",\"07876765436\",\"07876765437\",\"07876765438\",\"07876765439\"," +
                "\"07876765410\",\"07876765409\",\"07876765408\",\"07876765407\",\"07876765406\"]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

}