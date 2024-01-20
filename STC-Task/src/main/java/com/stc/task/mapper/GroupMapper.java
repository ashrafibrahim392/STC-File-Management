package com.stc.task.mapper;

import com.stc.task.dto.GroupPermissionDTO;
import com.stc.task.dto.PermissionDTO;
import com.stc.task.model.Permission;
import com.stc.task.model.PermissionGroup;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);


    PermissionGroup toPermissionGroup (GroupPermissionDTO groupPermissionDTO);

    Permission toPermission (PermissionDTO permissionDTO);

    List<Permission> toPermissionList (List<PermissionDTO> permissionDTO);
}
