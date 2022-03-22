package it.fabrick.Demo;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

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
import it.fabrick.demo.service.ApiService;
import it.fabrick.demo.service.pojo.balance.Balance;
import it.fabrick.demo.service.pojo.list.List;
import it.fabrick.demo.service.pojo.list.Lists;
import it.fabrick.demo.service.pojo.list.Payload;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@ActiveProfiles(profiles = "test")
class ListTransactionApplicationTests {

	@Value("${accountId}")
	private String accountId;

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	private ApiService apiService;

	@Test
	void listTest_OK() throws RestClientException, MalformedURLException, URISyntaxException {
		Lists list = new Lists();
		list.setPayload(new Payload());
		List l = new List();
		l.setAccountingDate("2019-11-29");
		l.setAmount(new Double(123));
		l.setCurrency("EUR");
		l.setDescription("PD VISA CORPORATE 10");
		l.setTransactionId("2131232");
		l.setOperationId("123456789");
		l.setValueDate("2019-12-01");
		list.getPayload().setList(new ArrayList<List>());
		list.getPayload().getList().add(l);

		ResponseEntity<Lists> response = new ResponseEntity<Lists>(list, HttpStatus.ACCEPTED);

		Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<Lists>>any())).thenReturn(response);

		Lists listReturned = apiService.getElencoTransazioni("2022-12-01", "2022-01-01");

		Assert.assertEquals(listReturned.getPayload().getList().get(0).getOperationId(), "123456789");

	}

	@Test
	void listTest_KO_checkData() throws RestClientException, MalformedURLException, URISyntaxException {
		Lists list = new Lists();
		list.setPayload(new Payload());
		List l = new List();
		l.setAccountingDate("2019-11-29");
		l.setAmount(new Double(123));
		l.setCurrency("EUR");
		l.setDescription("PD VISA CORPORATE 10");
		l.setTransactionId("2131232");
		l.setOperationId("123456789");
		l.setValueDate("2019-12-01");
		list.getPayload().setList(new ArrayList<List>());
		list.getPayload().getList().add(l);

		ResponseEntity<Lists> response = new ResponseEntity<Lists>(list, HttpStatus.ACCEPTED);

		Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<Lists>>any())).thenReturn(response);
		try {
			apiService.getElencoTransazioni("2022-fsa-01", "2022-01-01");
		} catch (DemoException de) {
			Assert.assertEquals(de.getDescription(), "Invalid date format");
		}

	}

	@Test
	void listTest_KO() throws RestClientException, MalformedURLException, URISyntaxException {
		try {
			String error = "{ \"status\" : \"KO\", \"errors\" : [{\"code\":\"REQ017\",\"description\":\"Invalid date format\",\"params\":\"\"}], \"payload\": {}}";

			HttpClientErrorException http = HttpClientErrorException.Unauthorized.create(HttpStatus.BAD_REQUEST, null,
					null, error.getBytes(), null);

			Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
					ArgumentMatchers.any(), ArgumentMatchers.<Class<Balance>>any())).thenThrow(http);

			apiService.getElencoTransazioni("2022-12-01", "2022-01-01");
		} catch (DemoException de) {
			Assert.assertEquals(de.getCode(), "REQ017");
		}

	}

}
