package com.shaper.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private String id, title, createdAt, updatedAt;
    private boolean done;

}
