package com.stc.task.dto;

import lombok.*;


import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupPermissionDTO {
@NotNull
   private String groupName;
    @NotNull
    private List<PermissionDTO> permissions;
}
