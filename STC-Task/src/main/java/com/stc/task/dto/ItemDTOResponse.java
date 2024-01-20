package com.stc.task.dto;

import com.stc.task.model.Item;
import com.stc.task.model.PermissionGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTOResponse {

    private Long id;

    private Item.Type type;
    private String name;

    private ItemDTOResponse parent;
}
