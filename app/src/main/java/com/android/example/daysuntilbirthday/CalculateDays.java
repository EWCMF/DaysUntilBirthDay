package com.android.example.daysuntilbirthday;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Year;
import org.threeten.bp.temporal.ChronoUnit;

public class CalculateDays {
    public String calcuteDays(int day, int month) {
        int year = Year.now().getValue();
        LocalDate localDateNow = LocalDate.now();
        LocalDate localDateBirthDay;
        if (!localDateNow.isLeapYear() && day == 29 && month == 2) {
            localDateBirthDay = LocalDate.of(year, 3, 1);
        }
        else {
            localDateBirthDay = LocalDate.of(year, month, day);
        }
        long daysBetween = ChronoUnit.DAYS.between(localDateNow, localDateBirthDay);
        long result;
        if (daysBetween < 0) {
            LocalDate localDateNextYear = LocalDate.of(year + 1, 1, 1);
            LocalDate nextBirthDay;
            if (!localDateNextYear.isLeapYear() && day == 29 && month == 2) {
                nextBirthDay = LocalDate.of(year + 1, 3, 1);
            }
            else {
                nextBirthDay = LocalDate.of(year + 1, month, day);
            }
            result = ChronoUnit.DAYS.between(localDateNow, nextBirthDay);
        }
        else if (daysBetween > 0) {
            result = daysBetween;
        }
        else {
            return "Your birthday is today. Happy Birthday.";
        }
        return "There are " + result + " days till your birthday";
    }
}
