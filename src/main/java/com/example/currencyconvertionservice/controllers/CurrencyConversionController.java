package com.example.currencyconvertionservice.controllers;

import com.example.currencyconvertionservice.models.CurrencyConversion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {
  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversion(@PathVariable String to, @PathVariable String from,
                                                        @PathVariable BigDecimal quantity){
    return new CurrencyConversion(1000L, from, "sandbox", to, BigDecimal.ONE, quantity, BigDecimal.ONE);
  }
}
