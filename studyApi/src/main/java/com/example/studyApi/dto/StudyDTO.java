package com.example.studyApi.dto;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyDTO {

    @NotBlank
    @Length(min = 4,max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9]{2,20}$",message = "2~20자의 영문 소문자,숫자를 입력해주세요, 특수 기호는 불가능합니다.")
    private String path;

    @NotBlank
    @Length(min = 4,max = 20)
    private String title;

    @NotBlank
    private String shortDescription;

    private String fullDescription;

    private Set<Tag> tags = new HashSet<>();

    private Set<Zone> zones = new HashSet<>();

    private Account manager;

    private boolean recruiting;
    private boolean published;
    private boolean closed;
}
