package ru.ssemenov.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.ssemenov.exceptions.MailException;
import ru.ssemenov.services.MailService;
import ru.ssemenov.utils.MailUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailUtil mailUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<String> getMailingList(String vatCode) {
        if (!redisTemplate.hasKey(vatCode)) {
            redisTemplate.opsForValue().set(vatCode, new ArrayList<>());
        }
        return (List<String>) redisTemplate.opsForValue().get(vatCode);
    }

    @Override
    public List<String> editMailingList(String vatCode, List<String> emails) {
        UUID trace = UUID.randomUUID();
        log.info("Start edit mailing list for vat={}, emails={}, trace={}", vatCode, emails, trace);
        List<String> validateEmails = emails.stream().filter(this::emailValidate).collect(Collectors.toList());
        redisTemplate.opsForValue().set(vatCode, validateEmails);
        log.info("Edit mailing list for vat={} is done, validateEmails={}, trace={}", vatCode, emails, trace);
        return validateEmails;
    }

    @Override
    public void notifyMailRecipients(String vatCode, String title, String body) {
        UUID trace = UUID.randomUUID();
        log.info("Start mailing by vat={}, trace={}", vatCode, trace);
        List<String> emails = getMailingList(vatCode);
        if (!emails.isEmpty()) {
            log.info("Selling to addresses={}, trace={}", emails, trace);
            mailUtil.sendEmailsToRecipients(emails, title, body, trace);
        } else {
            log.info("Mailing list is empty by vat={}, mailing is finish, trace={}", vatCode, trace);
            throw new MailException("Список рассылки пуст! Отправка письма невозможна");
        }
    }

    private boolean emailValidate(String emailAddress) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(emailAddress)
                .matches();
    }
}
