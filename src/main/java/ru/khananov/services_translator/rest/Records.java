package ru.khananov.services_translator.rest;

import java.util.List;

public class Records {
  private Records() {
  }

  public record TranslateRequest(String targetLanguageCode, String texts, String folderId) {
  }

  public record TranslateResponse(List<Translation> translations) {
    public record Translation(String detectedLanguageCode, String text) {
    }
  }
}
