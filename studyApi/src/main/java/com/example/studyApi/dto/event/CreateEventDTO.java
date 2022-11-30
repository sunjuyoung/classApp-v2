package com.example.studyApi.dto.event;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Study;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventDTO {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private LocalDateTime enrollmentEndTime;
    @NotBlank
    private LocalDateTime enrollmentStartTime;

    private LocalDateTime startDateTime;
    @NotBlank
    private LocalDateTime endDateTime;

    @NotBlank
    private Integer limitedNumber;
}
