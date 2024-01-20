package com.stc.task.mapper;

import com.stc.task.dto.ItemDTORequest;
import com.stc.task.dto.ItemDTOResponse;
import com.stc.task.model.Item;
import com.stc.task.model.PermissionGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", source = "itemDTORequest.type")
    @Mapping(target = "name", source = "itemDTORequest.name")
    @Mapping(target = "permissionGroup", source = "permissionGroup")
    @Mapping(target = "parent", source = "parent")
    Item toItem(ItemDTORequest itemDTORequest, PermissionGroup permissionGroup, Item parent);

    ItemDTOResponse toItemResponse (Item item);
}
