package com.stc.task.dto;

import com.stc.task.model.Item;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTORequest {
    @NotNull
    private String name;
    @NotNull
    private Item.Type type;
    private Long parentId;
    private Long groupId;
    private String requesterEmail;
}
