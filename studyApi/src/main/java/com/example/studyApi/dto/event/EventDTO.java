package com.example.studyApi.dto.event;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Enrollment;
import com.example.studyApi.domain.Event;
import com.example.studyApi.domain.Study;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO implements Serializable {

    private String title;

    private String study;

    private String manager;

    private String description;

    private LocalDate enrollmentEndTime;
    private LocalDate enrollmentStartTime;
    private LocalDate startDateTime;
    private LocalDate endDateTime;

    private int limitedNumber;

    private List<Enrollment> enrollments = new ArrayList<>();





}
