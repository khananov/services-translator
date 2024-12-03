package ru.khananov.services_translator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.khananov.services_translator.rest.Records;
import ru.khananov.services_translator.services.TranslateService;

@RestController
public class TranslatorController {
  private final TranslateService translateService;

  @Autowired
  public TranslatorController(TranslateService translateService) {
    this.translateService = translateService;
  }

  @PostMapping("/translate")
  public String translate(@RequestBody String text) {
    return translateService.translate(text);
  }
}
