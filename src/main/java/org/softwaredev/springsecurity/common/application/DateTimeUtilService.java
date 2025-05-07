package org.softwaredev.springsecurity.common.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import org.softwaredev.springsecurity.common.exceptions.InvalidDateFormatException;
import org.springframework.stereotype.Service;

@Service
public class DateTimeUtilService {
  // Generic Method
  public String formatDateReturnOnlyDate(Date date, String location) {
    return convertDateToLocalDate(date, location);
  }

  // Generic Method
  public @NotBlank String formatDateReturnDateAndTime(@NotNull Date date, String location) {
    return convertDateToLocalDateTime(date, location);
  }

  public String convertDateToLocalDate(Date date, String location) {
    LocalDate localDate = date.toInstant().atZone(ZoneId.of(location)).toLocalDate();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return localDate.format(formatter);
  }

  public String convertDateToLocalDateTime(Date date, String location) {
    return convertDateToLocalDateTimeWithPattern(date, location, "HH:mm:ss dd.MM.yyyy");
  }

  public String convertDateToLocalDateTimeWithPattern(
      Date date, String location, String dateTimePattern) {
    try {
      LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.of(location)).toLocalDateTime();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
      return localDateTime.format(formatter);
    } catch (DateTimeParseException e) {
      throw new InvalidDateFormatException(
          "Date : " + date + " is invalid. Correct Format : " + dateTimePattern);
    }
  }

  public Date convertOnlyDateToDateObject(String date, String dateTimePattern) {
    try {
      LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateTimePattern));
      return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    } catch (DateTimeParseException e) {
      throw new InvalidDateFormatException(
          "Date : " + date + " is invalid. Correct Format : " + dateTimePattern);
    }
  }

  public Date convertOnlyDateToDateObject(String date) {
    return convertOnlyDateToDateObject(date, "dd.MM.yyyy");
  }
}
