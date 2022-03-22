package it.fabrick.demo.service.pojo.balance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import it.fabrick.demo.service.pojo.BaseRestPojo;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "errors", "payload" })
public class Balance extends BaseRestPojo {

	@JsonProperty("payload")
	private BalancePayload payload;

	public BalancePayload getPayload() {
		return payload;
	}

	public void setPayload(BalancePayload payload) {
		this.payload = payload;
	}

}
