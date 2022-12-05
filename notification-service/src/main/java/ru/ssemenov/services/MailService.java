package ru.ssemenov.services;

import java.util.List;

public interface MailService {

    List<String> getMailingList(String vatCode);

    List<String> editMailingList(String vatCode, List<String> emails);

    void notifyMailRecipients(String vatCode, String title, String body);
}
