package ru.khananov.services_translator.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.khananov.services_translator.rest.Records;
import ru.khananov.services_translator.services.TranslateService;

import java.util.Collections;

@Service
public class TranslateServiceImpl implements TranslateService {
  private static final String YA_API_TRANSLATE_URL = "https://translate.api.cloud.yandex.net/translate/v2/translate";
  private final RestTemplate restTemplate;

  @Value("${ya.api.folder-id}")
  private String folderId;
  @Value("${ya.api.api-key}")
  private String apiKey;

  public TranslateServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public String translate(String text) {
    if (text == null || text.isEmpty()) return "";

    String targetLanguage = detectLanguage(text);
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.set(HttpHeaders.AUTHORIZATION, "Api-Key " + apiKey);
    Records.TranslateRequest request = new Records.TranslateRequest(targetLanguage, text, folderId);
    HttpEntity<Records.TranslateRequest> entity = new HttpEntity<>(request, headers);
    Records.TranslateResponse input = restTemplate.postForObject(YA_API_TRANSLATE_URL, entity, Records.TranslateResponse.class);
    if (input == null || input.translations() == null || input.translations().isEmpty()) return "Ошибка перевода";
    return input.translations().getFirst().text();
  }

  private String detectLanguage(String text) {
    if (text == null || text.isEmpty()) return "";

    boolean hasCyrillic = text.chars().anyMatch(c -> Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC);
    boolean hasLatin = text.chars().anyMatch(c -> Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN);

    if (hasCyrillic && !hasLatin) {
      return "en";
    } else if (hasLatin && !hasCyrillic) {
      return "ru";
    } else {
      return "ru";
    }
  }
}
