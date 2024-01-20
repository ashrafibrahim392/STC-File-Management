package com.stc.task.service.imp;

import com.stc.task.dto.ItemDTORequest;
import com.stc.task.dto.ItemDTOResponse;
import com.stc.task.mapper.ItemMapper;
import com.stc.task.model.File;
import com.stc.task.model.Item;
import com.stc.task.model.Permission;
import com.stc.task.model.PermissionGroup;
import com.stc.task.exception.FunctionalException;
import com.stc.task.repository.FileRepository;
import com.stc.task.repository.ItemRepository;
import com.stc.task.repository.PermessionGroupRepository;
import com.stc.task.service.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileManagementServiceImp implements FileManagementService {
    private final ItemRepository itemRepository;
    private final PermessionGroupRepository permessionGroupRepository;
    private final FileRepository fileRepository;
    private final ItemMapper itemMapper;

    @Override
    public void createItem(ItemDTORequest itemDTORequest) throws FunctionalException {
        Item parent = null;
        PermissionGroup permissionGroup;
        if (Item.Type.SPACE.equals(itemDTORequest.getType())) {
            if (itemDTORequest.getParentId() != null) {
                throw new FunctionalException(HttpStatus.FORBIDDEN, "Can't create space under another one");
            }
            permissionGroup = permessionGroupRepository.findById(itemDTORequest.getGroupId())
                    .orElseThrow(() -> new FunctionalException(HttpStatus.BAD_REQUEST, "Can't find group Id"));
        } else if (Item.Type.FOLDER.equals(itemDTORequest.getType())) {

            if (itemDTORequest.getParentId() == null) {
                throw new FunctionalException(HttpStatus.FORBIDDEN, "Can't create Folder without Parent");
            }
            parent = itemRepository.findById(itemDTORequest.getParentId())
                    .orElseThrow(() -> new FunctionalException(HttpStatus.BAD_REQUEST, "Can't find parent Id"));

            permissionGroup = parent.getPermissionGroup();
        } else {
            throw new FunctionalException(HttpStatus.BAD_REQUEST, "Missing binary data");
        }

        itemRepository.save(ItemMapper.INSTANCE.toItem(itemDTORequest, permissionGroup, parent));

    }

    @Override
    @Transactional
    public void uploadFile(MultipartFile file, ItemDTORequest itemDTORequest) throws FunctionalException, IOException {
        byte[] data = file.getBytes();
        if (itemDTORequest.getParentId() == null) {
            throw new FunctionalException(HttpStatus.FORBIDDEN, "Can't create file without Parent");
        }
        Item parent = itemRepository.findById(itemDTORequest.getParentId())
                .orElseThrow(() -> new FunctionalException(HttpStatus.BAD_REQUEST, "Can't find parent Id"));
        boolean isNameAlreadyExistOnSameItem = itemRepository.findByParentId(itemDTORequest.getParentId()).stream()
                .anyMatch(i -> i.getName().equals(itemDTORequest.getName()) && Item.Type.FILE.equals(i.getType()));
        if (isNameAlreadyExistOnSameItem)
            throw new FunctionalException(HttpStatus.FORBIDDEN, String.format("File with name %s already exist ", itemDTORequest.getName()));
        Item item = ItemMapper.INSTANCE.toItem(itemDTORequest, parent.getPermissionGroup(), parent);
        File fileModel = File.builder().itemId(item).data(data).build();

        itemRepository.save(item);
        fileRepository.save(fileModel);
    }

    @Override
    public Optional<Permission> findPermissionByEmailAndItemId(String email, Long id) throws FunctionalException {
        if (StringUtils.isEmpty(email) || id == null || id < 0)
            return Optional.empty();
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new FunctionalException(HttpStatus.BAD_REQUEST, "Can't create file without Parent"));
        return item.getPermissionGroup().getPermissions().stream()
                .filter(p -> p.getUserEmail().equals(email))
                .findFirst();
    }

    @Override
    public ItemDTOResponse getFileMetadata(Long ItemId) throws FunctionalException {
        return itemRepository.findById(ItemId).map(itemMapper::toItemResponse)
                .orElseThrow(() -> new FunctionalException(HttpStatus.BAD_REQUEST, "Can't find File"));
    }

}
