package it.fabrick.demo.service.pojo.bonifico.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import it.fabrick.demo.service.pojo.BaseRestPojo;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "errors", "payload" })
public class BonificoResponse extends BaseRestPojo {

	@JsonProperty("payload")
	private Payload payload;

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

}
