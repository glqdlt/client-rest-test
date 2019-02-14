package com.glqdlt.tdd.clientresttest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
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

        ResponseEntity<String> responseEntity = restClient.getForEntity(urlBuild.toUriString(), String.class, entity);
        Map resultRaw = parseJson(responseEntity.getBody());
        Map args = (Map) resultRaw.get("args");
        String echo = (String) args.get("greet");
        Assert.assertEquals(200,responseEntity.getStatusCodeValue());
        Assert.assertEquals(stub,echo);
    }

    @Test
    public void POST_으로_연결_후_200을_예상() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String,Object> body = new HashMap<>();
        body.put("greet","hello");

        final String stub = generateJsonString(body);

        HttpEntity<String> entity = new HttpEntity<>(stub,httpHeaders);

        ResponseEntity<String> responseEntity = restClient.postForEntity(POST_URL, entity, String.class);
        Map resultRaw = parseJson(responseEntity.getBody());

        String jsonBody = (String) resultRaw.get("data");
        Assert.assertEquals(200,responseEntity.getStatusCodeValue());
        Assert.assertEquals(stub,jsonBody);
    }

    @Test
    public void simple_텍스트파일이_있을_것이다() throws Exception {
        FileSystemResource textFile = new FileSystemResource(ClassLoader.getSystemResource("simple.txt").getPath());
        Assert.assertTrue(textFile.exists());
    }

    @Test
    public void POST_MULTI_FORM_200을_예상() throws Exception {
        final String stub = "hello";
        FileSystemResource textFile = new FileSystemResource(ClassLoader.getSystemResource("simple.txt").getPath());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("fileName",textFile.getFilename());
        body.add("file",textFile);

        HttpEntity<MultiValueMap> entity = new HttpEntity<>(body,httpHeaders);

        ResponseEntity<String> aaaa = restClient.postForEntity(POST_URL, entity, String.class);

        Map resultRaw = parseJson(aaaa.getBody());
        Map file = (Map) resultRaw.get("files");
        String simpleText = (String) file.get("file");
        Assert.assertEquals(stub,simpleText);
    }

    private String generateJsonString(Map obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
    }

    private Map parseJson(String body){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception",e);
        }
    }

}