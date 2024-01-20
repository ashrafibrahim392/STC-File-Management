package com.stc.task.service;

import com.stc.task.dto.ItemDTORequest;
import com.stc.task.dto.ItemDTOResponse;
import com.stc.task.exception.FunctionalException;
import com.stc.task.model.Permission;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

public interface FileManagementService {

    void createItem( ItemDTORequest itemDTORequest) throws FunctionalException;

    void uploadFile(MultipartFile file, ItemDTORequest itemDTORequest) throws FunctionalException, IOException;

    Optional<Permission> findPermissionByEmailAndItemId(String email, Long id) throws FunctionalException;

    ItemDTOResponse getFileMetadata(Long ItemId ) throws FunctionalException;
}
