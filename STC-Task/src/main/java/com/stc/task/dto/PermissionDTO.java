package com.stc.task.dto;

import com.stc.task.model.Permission;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {

    private String userEmail;
    private Permission.Level permissionLevel;
}
