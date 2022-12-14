package com.example.studyApi.dto.event;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Study;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventDTO {

    @NotBlank
    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentEndTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentStartTime;

    private LocalDate startDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateTime;

    @NotBlank
    private String limitedNumber;
}
