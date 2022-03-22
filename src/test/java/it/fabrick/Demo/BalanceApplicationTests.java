package it.fabrick.Demo;

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
import org.springframework.web.client.RestTemplate;

import it.fabrick.demo.exception.DemoException;
import it.fabrick.demo.service.AccountService;
import it.fabrick.demo.service.pojo.balance.Balance;
import it.fabrick.demo.service.pojo.balance.BalancePayload;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@ActiveProfiles(profiles = "test")
class BalanceApplicationTests {

	@Value("${accountId}")
	private String accountId;

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	private AccountService accountService;

	@Test
	void balanceTest_OK() {
		Balance balance = new Balance();
		balance.setPayload(new BalancePayload());
		balance.getPayload().setAvailableBalance(new Double(15.56));
		ResponseEntity<Balance> response = new ResponseEntity<Balance>(balance, HttpStatus.ACCEPTED);

		Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<Balance>>any())).thenReturn(response);

		Balance balanceReturned = accountService.getBalance(accountId);

		Assert.assertEquals(balanceReturned.getPayload().getAvailableBalance(),
				balance.getPayload().getAvailableBalance());

	}

	@Test
	void balanceTest_KO() {
		try {
			String error = "{ \"status\" : \"KO\", \"errors\" : [{\"code\":\"REQ004\",\"description\":\"Invalid account identifier\",\"params\":\"\"}], \"payload\": {}}";

			HttpClientErrorException http = HttpClientErrorException.Unauthorized.create(HttpStatus.BAD_REQUEST, null,
					null, error.getBytes(), null);

			Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
					ArgumentMatchers.any(), ArgumentMatchers.<Class<Balance>>any())).thenThrow(http);

			accountService.getBalance("badAccountId");
		} catch (DemoException de) {
			Assert.assertEquals(de.getCode(),"REQ004");
		}

	}

}
