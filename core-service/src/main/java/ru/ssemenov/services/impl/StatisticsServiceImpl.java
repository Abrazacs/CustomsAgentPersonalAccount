package ru.ssemenov.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ssemenov.entities.CustomsDeclaration;
import ru.ssemenov.exceptions.ResourceNotFoundException;
import ru.ssemenov.repositories.CustomsDeclarationRepository;
import ru.ssemenov.services.StatisticsService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final CustomsDeclarationRepository customsDeclarationRepository;

    @Override
    public LocalTime getAverageDurationOfRelease() {
        return LocalTime.ofSecondOfDay((long) customsDeclarationRepository.findAll()
                .stream()
                .mapToLong(declaration -> Duration
                        .between(declaration
                                .getDateOfSubmission(), declaration.getDateOfRelease())
                        .toSeconds())
                .average().orElseThrow(() -> new ResourceNotFoundException ("No data available")));
    }

    @Override
    public Double getShareOfSubmittedDeclarationsBeforeNoonByMonth() {
        return Math.round(((double) customsDeclarationRepository.findAll()
                .stream()
                .filter(declaration -> declaration.getDateOfSubmission().getMonth() == LocalDateTime.now().getMonth())
                .filter(declaration -> declaration.getDateOfSubmission().getHour() <= 12)
                .count() / customsDeclarationRepository.findAll().size() * 100) * 10.0) / 10.0;
    }

    @Override
    public Double getShareOfReleasedDeclarationsWithinOneDayByMonth() {
        return Math.round(((double) customsDeclarationRepository.findAll()
                .stream()
                .filter(declaration -> declaration.getDateOfSubmission().getMonth() == LocalDateTime.now().getMonth())
                .mapToLong(declaration -> Duration
                        .between(declaration
                                .getDateOfSubmission(), declaration.getDateOfRelease())
                        .toHours())
                .filter(d -> d <= 24L)
                .count() / customsDeclarationRepository.findAll().size() * 100) * 10.0) / 10.0;
    }

    @Override
    public Long getDeclarationsCountInProgress() {
        return customsDeclarationRepository.findAll()
                .stream()
                .filter(declaration -> declaration.getStatus() == CustomsDeclaration.Status.SUBMITTED).count();
    }
}
