package it.fabrick.demo.service.pojo.bonifico;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "creditor", "executionDate", "uri", "description", "amount", "currency" })
public class BonificoRequest {

	@JsonProperty("creditor")
	private Creditor creditor;
	@JsonProperty("executionDate")
	private String executionDate;
	@JsonProperty("uri")
	private String uri;
	@JsonProperty("description")
	private String description;
	@JsonProperty("amount")
	private Double amount;
	@JsonProperty("currency")
	private String currency;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("creditor")
	public Creditor getCreditor() {
		return creditor;
	}

	@JsonProperty("creditor")
	public void setCreditor(Creditor creditor) {
		this.creditor = creditor;
	}

	@JsonProperty("executionDate")
	public String getExecutionDate() {
		return executionDate;
	}

	@JsonProperty("executionDate")
	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	@JsonProperty("uri")
	public String getUri() {
		return uri;
	}

	@JsonProperty("uri")
	public void setUri(String uri) {
		this.uri = uri;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("amount")
	public Double getAmount() {
		return amount;
	}

	@JsonProperty("amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@JsonProperty("currency")
	public String getCurrency() {
		return currency;
	}

	@JsonProperty("currency")
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
