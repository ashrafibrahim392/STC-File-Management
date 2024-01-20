package com.stc.task.service.imp;

import com.stc.task.dto.GroupPermissionDTO;
import com.stc.task.mapper.GroupMapper;
import com.stc.task.repository.PermessionGroupRepository;
import com.stc.task.service.PermissionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PermissionManagementServiceImp implements PermissionManagementService {

    private final PermessionGroupRepository permessionGroupRepository;
    @Override
    @Transactional
    public void createGroup(GroupPermissionDTO groupPermissionDTO) {
        permessionGroupRepository.save (GroupMapper.INSTANCE.toPermissionGroup(groupPermissionDTO));
    }
}
