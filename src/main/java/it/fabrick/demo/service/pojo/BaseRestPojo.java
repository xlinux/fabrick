package it.fabrick.demo.service.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "errors", "payload" })
public class BaseRestPojo {

	@JsonProperty("status")
	private String status;
	@JsonProperty("error")
	private List<Error> error = null;
	@JsonIgnore
	@JsonProperty("payload")
	private Object payload;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Error> getError() {
		return error;
	}

	public void setError(List<Error> error) {
		this.error = error;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

}
