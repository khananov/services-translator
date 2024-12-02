package ru.khananov.services_translator.services;

import ru.khananov.services_translator.rest.Records;

public interface TranslateService {
  String translate(Records.TranslateInput translateInput);
}
