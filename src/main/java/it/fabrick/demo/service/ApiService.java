package it.fabrick.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.fabrick.demo.controller.bean.EsecuzioneBonificoBean;
import it.fabrick.demo.controller.bean.EsecuzioneBonificoResponseBean;
import it.fabrick.demo.exception.DemoException;
import it.fabrick.demo.service.pojo.balance.Balance;
import it.fabrick.demo.service.pojo.bonifico.Account;
import it.fabrick.demo.service.pojo.bonifico.BonificoRequest;
import it.fabrick.demo.service.pojo.bonifico.Creditor;
import it.fabrick.demo.service.pojo.bonifico.response.BonificoResponse;
import it.fabrick.demo.service.pojo.list.Lists;

@Service
public class ApiService {

	@Value("${accountId}")
	private String accountId;

	@Autowired
	private AccountService accountService;

	public String getSaldo() throws JsonMappingException, JsonProcessingException {
		Balance balance = accountService.getBalance(accountId);
		if (balance != null) {
			if ("OK".equals(balance.getStatus())) {
				return balance.getPayload().getBalance().toString();
			}
		}
		throw new DemoException(balance.getError().get(0).getCode(), balance.getError().get(0).getDescription());

	}

	public Lists getElencoTransazioni(String from, String to) {

		String patternDate = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

		if (!from.matches(patternDate)) {
			DemoException.throwException("KO", "Invalid date format");
		}
		if (!to.matches(patternDate)) {
			DemoException.throwException("KO", "Invalid date format");
		}
		Lists lists = accountService.getListOfTransaction(from, to, accountId);
		return lists;

	}

	public EsecuzioneBonificoResponseBean eseguiBonifico(EsecuzioneBonificoBean esecuzioneBonificoBean) {

		String patternDate = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
		if (!esecuzioneBonificoBean.getExecutionDate().matches(patternDate)) {
			DemoException.throwException("KO", "Invalid date format");
		}
		
		EsecuzioneBonificoResponseBean webResponse = new EsecuzioneBonificoResponseBean();

		BonificoRequest bonificoRequest = new BonificoRequest();
		bonificoRequest.setAmount(Double.valueOf(esecuzioneBonificoBean.getAmount()));
		bonificoRequest.setCreditor(new Creditor());
		bonificoRequest.getCreditor().setAccount(new Account());
		bonificoRequest.getCreditor().setName(esecuzioneBonificoBean.getReceiverName());
		bonificoRequest.getCreditor().getAccount().setAccountCode(esecuzioneBonificoBean.getReceiverName());
		bonificoRequest.getCreditor().getAccount().setBicCode("SELBIT2BXXX");
		bonificoRequest.setExecutionDate(esecuzioneBonificoBean.getExecutionDate());
		bonificoRequest.setDescription(esecuzioneBonificoBean.getDescription());
		bonificoRequest.setCurrency("EUR");
		BonificoResponse response = accountService.eseguiBonifico(bonificoRequest, accountId);

		if (response != null) {
			if ("OK".equals(response.getStatus())) {
				webResponse.setCode(response.getStatus());
				webResponse.setDescription(response.getPayload().getStatus());
				webResponse.setCro(response.getPayload().getCro());
				webResponse.setTrn(response.getPayload().getTrn());
			} else {
				webResponse.setCode(response.getError().get(0).getCode());
				webResponse.setDescription(response.getError().get(0).getDescription());
			}
		}

		return webResponse;
	}
}
