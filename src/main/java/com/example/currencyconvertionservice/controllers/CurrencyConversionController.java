package com.example.currencyconvertionservice.controllers;

import com.example.currencyconvertionservice.models.CurrencyConversion;
import com.example.currencyconvertionservice.proxies.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {
  @Autowired
  private CurrencyExchangeProxy proxy;
  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversion(@PathVariable String to, @PathVariable String from,
                                                        @PathVariable BigDecimal quantity){
    HashMap<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);
    ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
            CurrencyConversion.class, uriVariables);
    CurrencyConversion c = responseEntity.getBody();
    return new CurrencyConversion(c.getId(), from, c.getEnvironment()+ " " + "Rest Template", to, c.getConversionMultiple(), quantity, quantity.multiply(c.getConversionMultiple()));
  }

  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String to, @PathVariable String from,
                                                        @PathVariable BigDecimal quantity){

    CurrencyConversion c = proxy.retrieveExchangeValue(from, to);
    return new CurrencyConversion(c.getId(), from, c.getEnvironment()+ " " + "Feign", to, c.getConversionMultiple(), quantity, quantity.multiply(c.getConversionMultiple()));
  }
}
