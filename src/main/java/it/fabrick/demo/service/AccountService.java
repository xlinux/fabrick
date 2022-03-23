package it.fabrick.demo.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import it.fabrick.demo.exception.DemoException;
//import it.fabrick.demo.service.pojo.Account;
import it.fabrick.demo.service.pojo.balance.Balance;
import it.fabrick.demo.service.pojo.bonifico.BonificoRequest;
import it.fabrick.demo.service.pojo.bonifico.response.BonificoResponse;
import it.fabrick.demo.service.pojo.list.Lists;

@Service
public class AccountService extends BaseService {

	Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Value("${url.list.of.accounts}")
	private String serviceName;

	@Value("${url.list.of.transactions}")
	private String serviceNameListTransactions;

	@Value("${url.account.balance}")
	private String serviceNameBalance;

	@Value("${url.esecuzione.bonifico}")
	private String serviceNameEseguiBonifico;

	public Lists getListOfTransaction(String from, String to, String accountId) {
		String url = getBaseUrl() + serviceNameListTransactions.replace("{accountId}", accountId);
		ResponseEntity<Lists> result = null;

		try {
			URIBuilder b = new URIBuilder(url);
			b.addParameter("fromAccountingDate", from);
			b.addParameter("toAccountingDate", to);

			HttpMethod httpMethod = HttpMethod.GET;

			HttpHeaders headers = buildHeaders();

			HttpEntity<String> entity = new HttpEntity<>(headers);
			logger.info("call url: " + url);
			result = getRestTemplate().exchange(b.build().toURL().toString(), httpMethod, entity, Lists.class);
		} catch (HttpServerErrorException | HttpClientErrorException httpEx) {
			manageError(httpEx);
		} catch (URISyntaxException | RestClientException | MalformedURLException e) {
			DemoException.throwException("KO", "Generic Error");
		}

		return result.getBody();
	}

	public Balance getBalance(String accountId) {
		String url = getBaseUrl() + serviceNameBalance.replace("{accountId}", accountId);
		HttpMethod httpMethod = HttpMethod.GET;

		HttpHeaders headers = buildHeaders();

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<Balance> result = null;
		try {
			logger.info("call url: " + url);
			result = getRestTemplate().exchange(url, httpMethod, entity, Balance.class);
		} catch (HttpServerErrorException | HttpClientErrorException httpEx) {
			manageError(httpEx);
		}

		return result.getBody();
	}

	public BonificoResponse eseguiBonifico(BonificoRequest bonificoRequest, String accountId) {
		String url = getBaseUrl() + serviceNameEseguiBonifico.replace("{accountId}", accountId);
		HttpMethod httpMethod = HttpMethod.POST;

		HttpHeaders headers = buildHeaders();

		HttpEntity<BonificoRequest> entity = new HttpEntity<BonificoRequest>(bonificoRequest, headers);

		ResponseEntity<BonificoResponse> result = null;
		try {
			logger.info("call url: " + url);
			result = getRestTemplate().exchange(url, httpMethod, entity, BonificoResponse.class);
		} catch (HttpServerErrorException | HttpClientErrorException httpEx) {
			manageError(httpEx);
		}

		return result.getBody();
	}

}
