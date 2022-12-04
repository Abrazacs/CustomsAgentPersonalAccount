package ru.ssemenov.controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MailController {

    List<String> getMailingList(String vatCode);

    List<String> editMailingList(String vatCode, List<String> emails);

    ResponseEntity<String> notifyMailRecipients(String vatCode);
}
