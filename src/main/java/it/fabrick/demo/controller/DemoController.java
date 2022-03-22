package it.fabrick.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.fabrick.demo.controller.bean.EsecuzioneBonificoBean;
import it.fabrick.demo.controller.bean.EsecuzioneBonificoResponseBean;
import it.fabrick.demo.controller.bean.TransazioniBean;
import it.fabrick.demo.service.ApiService;
import it.fabrick.demo.service.pojo.list.Lists;

@RestController
@RequestMapping("api")
public class DemoController {

	@Autowired
	private ApiService apiService;

	@GetMapping("listaSaldo")
	public String retrieveSaldo() throws JsonMappingException, JsonProcessingException {
		return apiService.getSaldo();
	}

	@GetMapping("listaTransazioni/{fromAccountingDate}/{toAccountingDate}")
	public List<TransazioniBean> listaTransazioni(@PathVariable("fromAccountingDate") String fromAccountingDate,
			@PathVariable("toAccountingDate") String toAccountingDate) {

		List<TransazioniBean> elencoTransazioni = new ArrayList<TransazioniBean>();

		Lists lists = apiService.getElencoTransazioni(fromAccountingDate, toAccountingDate);
		mapServiceResponse(elencoTransazioni, lists);

		return elencoTransazioni;
	}

	@PostMapping("esecuzioneBonifico")
	public EsecuzioneBonificoResponseBean esecuzioneBonifico(
			@RequestBody EsecuzioneBonificoBean esecuzioneBonificoBean) {

		return apiService.eseguiBonifico(esecuzioneBonificoBean);

	}

	private void mapServiceResponse(List<TransazioniBean> elencoTransazioni, Lists lists) {
		if (lists != null) {
			for (it.fabrick.demo.service.pojo.list.List l : lists.getPayload().getList()) {
				TransazioniBean tb = new TransazioniBean();
				BeanUtils.copyProperties(l, tb);
				elencoTransazioni.add(tb);
			}
		}
	}
}
