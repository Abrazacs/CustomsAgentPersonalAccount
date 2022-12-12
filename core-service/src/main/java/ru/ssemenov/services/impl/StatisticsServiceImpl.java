package ru.ssemenov.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ssemenov.entities.CustomsDeclaration;
import ru.ssemenov.exceptions.ResourceNotFoundException;
import ru.ssemenov.repositories.CustomsDeclarationRepository;
import ru.ssemenov.services.StatisticsService;
import ru.ssemenov.utils.CustomsDeclarationStatusEnum;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final CustomsDeclarationRepository customsDeclarationRepository;

    private final List<CustomsDeclaration> declarations = customsDeclarationRepository.findAll();

    @Override
    public LocalTime getAverageDurationOfRelease() {
        return LocalTime.ofSecondOfDay((long) declarations
                .stream()
                .mapToLong(declaration -> Duration
                        .between(declaration
                                .getDateOfSubmission(), declaration.getDateOfRelease())
                        .toSeconds())
                .average().orElseThrow(() -> new ResourceNotFoundException ("No data available")));
    }

    @Override
    public Double getShareOfSubmittedDeclarationsBeforeNoonByMonth() {
        return Math.round(((double) declarations
                .stream()
                .filter(declaration -> declaration.getDateOfSubmission().getMonth() == LocalDateTime.now().getMonth())
                .filter(declaration -> declaration.getDateOfSubmission().getHour() <= 12)
                .count() / declarations.size() * 100) * 10.0) / 10.0;
    }

    @Override
    public Double getShareOfReleasedDeclarationsWithinOneDayByMonth() {
        return Math.round(((double) declarations
                .stream()
                .filter(declaration -> declaration.getDateOfSubmission().getMonth() == LocalDateTime.now().getMonth())
                .filter(declaration -> declaration.getStatus().equals(CustomsDeclarationStatusEnum.RELEASE.name()))
                .mapToLong(declaration -> Duration
                        .between(declaration
                                .getDateOfSubmission(), declaration.getDateOfRelease())
                        .toHours())
                .filter(d -> d <= 24L)
                .count() / declarations.size() * 100) * 10.0) / 10.0;
    }

    @Override
    public Long getDeclarationsCountInProgress() {
        return declarations
                .stream()
                .filter(declaration -> declaration.getStatus() != CustomsDeclarationStatusEnum.RELEASE.name() || declaration.getStatus() != CustomsDeclarationStatusEnum.RELEASE_DENIED.name()).count();
    }
}
