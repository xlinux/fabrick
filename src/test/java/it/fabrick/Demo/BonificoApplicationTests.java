package it.fabrick.Demo;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.fabrick.demo.exception.DemoException;
import it.fabrick.demo.service.AccountService;
import it.fabrick.demo.service.pojo.balance.Balance;
import it.fabrick.demo.service.pojo.bonifico.Account;
import it.fabrick.demo.service.pojo.bonifico.BonificoRequest;
import it.fabrick.demo.service.pojo.bonifico.Creditor;
import it.fabrick.demo.service.pojo.bonifico.response.BonificoResponse;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@ActiveProfiles(profiles = "test")
class BonificoApplicationTests {

	@Value("${accountId}")
	private String accountId;

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	private AccountService accountService;

	@Test
	void bonificoTest_OK() throws RestClientException, MalformedURLException, URISyntaxException {
		BonificoRequest bonificoRequest = buildRequest();
		
		BonificoResponse bonificoResponse = new BonificoResponse();
		bonificoResponse.setPayload(new it.fabrick.demo.service.pojo.bonifico.response.Payload());
		bonificoResponse.getPayload().setTrn("TRN00001");
		
		ResponseEntity<BonificoResponse> response = new ResponseEntity<BonificoResponse>(bonificoResponse, HttpStatus.ACCEPTED);

		Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<BonificoResponse>>any())).thenReturn(response);

		BonificoResponse bonificoServiceResponse = accountService.eseguiBonifico(bonificoRequest, accountId);

		Assert.assertEquals(bonificoServiceResponse.getPayload().getTrn(),"TRN00001");

	}

	private BonificoRequest buildRequest() {
		BonificoRequest bonificoRequest = new BonificoRequest();
		bonificoRequest.setAmount(new Double(15.5));
		bonificoRequest.setCreditor(new Creditor());
		bonificoRequest.getCreditor().setAccount(new Account());
		bonificoRequest.getCreditor().getAccount().setAccountCode("IT46546546464646465465");
		bonificoRequest.getCreditor().setName("Name");
		
		bonificoRequest.setCurrency("EUR");
		bonificoRequest.setDescription("DESCRIPTION");
		bonificoRequest.setExecutionDate("2022-03-22");
		return bonificoRequest;
	}

	@Test
	void bonificoTest_KO() throws RestClientException, MalformedURLException, URISyntaxException {
		BonificoRequest bonificoRequest = buildRequest();
		try {
			String error = "{ \"status\" : \"KO\", \"errors\" : [{\"code\":\"REQ017\",\"description\":\"Invalid date format\",\"params\":\"\"}], \"payload\": {}}";

			HttpClientErrorException http = HttpClientErrorException.Unauthorized.create(HttpStatus.BAD_REQUEST, null,
					null, error.getBytes(), null);

			Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
					ArgumentMatchers.any(), ArgumentMatchers.<Class<Balance>>any())).thenThrow(http);

			accountService.eseguiBonifico(bonificoRequest, accountId);
		} catch (DemoException de) {
			Assert.assertEquals(de.getCode(),"REQ017");
		} 

	}

}
