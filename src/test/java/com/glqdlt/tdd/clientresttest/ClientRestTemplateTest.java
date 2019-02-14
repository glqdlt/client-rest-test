package com.glqdlt.tdd.clientresttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

/**
 * @author glqdlt
 * 2019-02-14
 * @see <a href="https://resttesttest.com/">https://resttesttest.com/</a>
 */
public class ClientRestTemplateTest {

    private RestTemplate restClient;

    private String GET_URL = "https://httpbin.org/get";
    private String POST_URL = "https://httpbin.org/post";

    @Before
    public void setUp() throws Exception {
        ClientRestTemplate clientRestTemplate = new ClientRestTemplate();
        restClient = clientRestTemplate.restTemplate();

    }

    @Test
    public void 탬플릿생성() throws Exception {
        Assert.assertNotNull(restClient);
    }

    @Test
    public void GET_으로_연결_후_200을_예상() throws Exception {
        final String stub = "hello";

        HttpHeaders httpHeaders = new HttpHeaders();

        HttpEntity<MultiValueMap> entity = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder urlBuild = UriComponentsBuilder.fromHttpUrl(GET_URL);
        urlBuild.queryParam("greet",stub);

        ResponseEntity<String> aaa = restClient.getForEntity(urlBuild.toUriString(), String.class, entity);
        Map resultRaw = convertJson(aaa.getBody());
        Map args = (Map) resultRaw.get("args");
        String echo = (String) args.get("greet");
        Assert.assertEquals(stub,echo);

    }

    private Map convertJson(String body){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception",e);
        }
    }

}