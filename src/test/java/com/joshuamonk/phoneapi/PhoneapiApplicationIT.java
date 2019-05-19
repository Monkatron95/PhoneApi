package com.joshuamonk.phoneapi;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void testRetreiveCustomerPhoneNumbers() throws JSONException {

        long customerId = 1;

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        String requestUrl = "http://localhost:" + port + "/customers/ " + customerId +"/numbers";

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET, entity, String.class);

        String expected = "[\"07876765434\"]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testRetreiveCustomerPhoneNumbers404NotFound() {

        long customerId = 200;

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        String requestUrl = "http://localhost:" + port + "/customers/ " + customerId +"/numbers";

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testActivateCustomerPhoneNumber() {

        long customerId = 1;

        // Different method for making Http Requests required to overcome PATCH issue
        // See - https://github.com/spring-cloud/spring-cloud-netflix/issues/1777

        HttpHeaders patchHeaders = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "merge-patch+json");
        patchHeaders.setContentType(mediaType);

        HttpEntity<String> entity = new HttpEntity<String>("{\"number\": \"01234567894\"}", patchHeaders);

        String requestUrl = "http://localhost:" + port + "/customers/ " + customerId +"/numbers";

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.PATCH, entity, String.class);

        // Test for OK response and that response contains activated phone number
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("01234567894");
    }

    @Test
    public void testActivateCustomerPhoneNumber404CustomerNotFound() {

        long customerId = 200;

        HttpHeaders patchHeaders = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "merge-patch+json");
        patchHeaders.setContentType(mediaType);

        HttpEntity<String> entity = new HttpEntity<String>("{\"number\": \"99999999999\"}", patchHeaders);

        String requestUrl = "http://localhost:" + port + "/customers/ " + customerId +"/numbers";

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.PATCH, entity, String.class);

        // Test for 404
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testActivateCustomerPhoneNumber400InvalidPhoneNumber() {

        long customerId = 1;

        HttpHeaders patchHeaders = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "merge-patch+json");
        patchHeaders.setContentType(mediaType);

        HttpEntity<String> entity = new HttpEntity<String>("{\"number\": \"\"}", patchHeaders);

        String requestUrl = "http://localhost:" + port + "/customers/ " + customerId +"/numbers";

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.PATCH, entity, String.class);

        // Test for OK response and that response contains activated phone number
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testActivateCustomerPhoneNumber400PhoneNumberInUse() {

        long customerId = 1;

        HttpHeaders patchHeaders = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "merge-patch+json");
        patchHeaders.setContentType(mediaType);

        HttpEntity<String> entity = new HttpEntity<String>("{\"number\": \"07876765409\"}", patchHeaders);

        String requestUrl = "http://localhost:" + port + "/customers/ " + customerId +"/numbers";

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.PATCH, entity, String.class);

        // Test for OK response and that response contains activated phone number
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
