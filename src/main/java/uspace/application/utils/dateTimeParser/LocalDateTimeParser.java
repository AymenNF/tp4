package uspace.application.utils.dateTimeParser;

import jakarta.inject.Inject;
import uspace.domain.exceptions.InvalidDateFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {

    private final DateTimeFormatter dateTimeFormatter;

    @Inject
    public LocalDateTimeParser(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public LocalDateTime parse(String dateTimeStr) {
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
        } catch (Exception e) {
            throw new InvalidDateFormatException();
        }

        return dateTime;
    }
}
