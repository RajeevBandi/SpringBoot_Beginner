package com.facilitiesportal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


/**
 * Controller class to handle requests related to date and time.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
public class DateTimeController {

	
	/**
     * Retrieves the current date and time in IST (Indian Standard Time) format.
     *
     * @return A map containing the current date and time, with keys "date" and "time".
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/current-datetime")
    public Map<String, String> getCurrentDateTimeInIST() {
        // Get current date and time in UTC
        LocalDateTime utcDateTime = LocalDateTime.now(ZoneId.of("UTC"));

        // Convert to IST (Indian Standard Time) by adding 5 hours and 30 minutes
        LocalDateTime istDateTime = utcDateTime.plusHours(5).plusMinutes(30);

        // Format the date and time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = istDateTime.format(dateFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = istDateTime.format(timeFormatter);

        Map<String, String> dateTimeMap = new HashMap<>();
        dateTimeMap.put("date", formattedDate);
        dateTimeMap.put("time", formattedTime);

        return dateTimeMap;
    }
}
