package com.rest.core.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentDTO {
    private String firstname;
    private String lastname;
    private String email;
    private Long schoolId;
}
