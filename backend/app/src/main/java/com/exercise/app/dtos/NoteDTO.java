package com.exercise.app.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDTO {

    private Long id;
    private String title;
    private String content;
    private Boolean archived;
    private List<String> categories;
}
