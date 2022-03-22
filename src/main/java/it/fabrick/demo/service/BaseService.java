package it.fabrick.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.fabrick.demo.exception.DemoException;
import it.fabrick.demo.service.pojo.error.ErrorResponse;

@Component
public class BaseService {

	@Value("${base.url}")
	private String baseUrl;
	@Value("${authSchema}")
	private String authSchema;
	@Value("${apiKey}")
	private String apiKey;

	@Autowired
	private ObjectMapper om;

	public ObjectMapper getOm() {
		return om;
	}

	public void setOm(ObjectMapper om) {
		this.om = om;
	}

	@Autowired
	private RestTemplate restTemplate;

	public String getBaseUrl() {
		return baseUrl;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getAuthSchema() {
		return authSchema;
	}

	public void setAuthSchema(String authSchema) {
		this.authSchema = authSchema;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void manageError(HttpStatusCodeException httpEx) {
		String jsonBody = ((org.springframework.web.client.HttpStatusCodeException) httpEx).getResponseBodyAsString();

		ErrorResponse b = extracted(jsonBody);
		DemoException.throwException(b.getErrors().get(0).getCode(), b.getErrors().get(0).getDescription());
	}

//	public void manageError(HttpServerErrorException httpEx) {
//		String jsonBody = ((org.springframework.web.client.HttpStatusCodeException) httpEx).getResponseBodyAsString();
//
//		ErrorResponse b = extracted(jsonBody);
//		DemoException.throwException(b.getErrors().get(0).getCode(), b.getErrors().get(0).getDescription());
//	}

	private ErrorResponse extracted(String jsonBody) {
		ErrorResponse b = null;
		try {
			b = getOm().readValue(jsonBody, ErrorResponse.class);
		} catch (Exception e) {
			return null;
		}
		return b;
	}

	public HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Auth-Schema", getAuthSchema());
		headers.set("apiKey", getApiKey());
		return headers;
	}

}
