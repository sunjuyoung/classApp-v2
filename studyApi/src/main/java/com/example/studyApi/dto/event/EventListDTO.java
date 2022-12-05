package com.example.studyApi.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventListDTO implements Serializable {

    private String title;

    private String description;

    private LocalDate enrollmentEndTime;

    private LocalDate enrollmentStartTime;

    private LocalDate startDateTime;

    private LocalDate endDateTime;

    private int limitedNumber;





}
