
package it.fabrick.demo.service.pojo.bonifico.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "debtorAmount", "debtorCurrency", "creditorAmount", "creditorCurrency", "creditorCurrencyDate",
		"currencyRatio" })
public class Amount {

	@JsonProperty("debtorAmount")
	private Integer debtorAmount;
	@JsonProperty("debtorCurrency")
	private String debtorCurrency;
	@JsonProperty("creditorAmount")
	private Integer creditorAmount;
	@JsonProperty("creditorCurrency")
	private String creditorCurrency;
	@JsonProperty("creditorCurrencyDate")
	private String creditorCurrencyDate;
	@JsonProperty("currencyRatio")
	private Integer currencyRatio;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("debtorAmount")
	public Integer getDebtorAmount() {
		return debtorAmount;
	}

	@JsonProperty("debtorAmount")
	public void setDebtorAmount(Integer debtorAmount) {
		this.debtorAmount = debtorAmount;
	}

	@JsonProperty("debtorCurrency")
	public String getDebtorCurrency() {
		return debtorCurrency;
	}

	@JsonProperty("debtorCurrency")
	public void setDebtorCurrency(String debtorCurrency) {
		this.debtorCurrency = debtorCurrency;
	}

	@JsonProperty("creditorAmount")
	public Integer getCreditorAmount() {
		return creditorAmount;
	}

	@JsonProperty("creditorAmount")
	public void setCreditorAmount(Integer creditorAmount) {
		this.creditorAmount = creditorAmount;
	}

	@JsonProperty("creditorCurrency")
	public String getCreditorCurrency() {
		return creditorCurrency;
	}

	@JsonProperty("creditorCurrency")
	public void setCreditorCurrency(String creditorCurrency) {
		this.creditorCurrency = creditorCurrency;
	}

	@JsonProperty("creditorCurrencyDate")
	public String getCreditorCurrencyDate() {
		return creditorCurrencyDate;
	}

	@JsonProperty("creditorCurrencyDate")
	public void setCreditorCurrencyDate(String creditorCurrencyDate) {
		this.creditorCurrencyDate = creditorCurrencyDate;
	}

	@JsonProperty("currencyRatio")
	public Integer getCurrencyRatio() {
		return currencyRatio;
	}

	@JsonProperty("currencyRatio")
	public void setCurrencyRatio(Integer currencyRatio) {
		this.currencyRatio = currencyRatio;
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
