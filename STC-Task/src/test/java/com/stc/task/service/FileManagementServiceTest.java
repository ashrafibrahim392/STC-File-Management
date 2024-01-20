package com.stc.task.service;

import com.stc.task.dto.ItemDTORequest;
import com.stc.task.exception.FunctionalException;
import com.stc.task.model.Item;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.stc.task.model.Permission;
import com.stc.task.model.PermissionGroup;
import com.stc.task.repository.ItemRepository;
import com.stc.task.repository.PermessionGroupRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
public class FileManagementServiceTest {

    @Autowired
    private FileManagementService fileManagementService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PermessionGroupRepository permessionGroupRepository;
    private Item space ;
    private Item folder;
    @BeforeAll
    public void startUp() {
        permessionGroupRepository.save(getPermissionGroup());
         space = itemRepository.save(Item.builder()
                .name("test00")
                .type(Item.Type.SPACE)
                .permissionGroup(getPermissionGroup()).build());
         folder = itemRepository.save(Item.builder()
                .name("hello")
                .type(Item.Type.FOLDER)
                .parent(space)
                .permissionGroup(getPermissionGroup()).build());

    }

    @Test
    void WhenAddingNewSpaceWithParentId_ThenFailed() throws Exception {
        assertThrows(FunctionalException.class, () -> fileManagementService.createItem(ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.SPACE)
                .groupId(1L)
                .parentId(1L)
                .build()));

    }

    @Test
    void WhenAddingNewSpaceWithOutParentId_ThenOk() throws Exception {

        assertDoesNotThrow(() -> fileManagementService.createItem(ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.SPACE)
                .groupId(1L)
                .build()));
    }

    @Test
    void WhenAddingNewFolderWithParentId_ThenOK() throws Exception {

        assertDoesNotThrow(() -> fileManagementService.createItem(ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.FOLDER)
                .parentId(space.getId())
                .build()));
    }

    @Test
    void WhenAddingNewFolderWithOutParentId_ThenFailed() throws Exception {

        assertThrows(FunctionalException.class, () -> fileManagementService.createItem(ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.FOLDER)
                .build()));
    }

    @Test
    void WhenAddingNewFolderNotExistParentId_ThenFailed() throws Exception {

        assertThrows(FunctionalException.class, () -> fileManagementService.createItem(ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.FOLDER)
                .parentId(77L)
                .build()));
    }

    @Test
    void WhenAddingNewFileWithParentId_ThenOK() throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        assertDoesNotThrow(() -> fileManagementService.uploadFile(file,
                ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.FILE)
                .parentId(folder.getId())
                .build()));
    }
    @Test
    void WhenAddingNewFileWithAlreadyExistName_ThenFailed() throws Exception {
        MockMultipartFile fileMock
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
       Item file = itemRepository.save(Item.builder()
                .name("hello.txt")
                .type(Item.Type.FILE)
                .parent(folder)
                .permissionGroup(getPermissionGroup()).build());
        assertThrows (FunctionalException.class,() -> fileManagementService.uploadFile(fileMock,
                ItemDTORequest.builder()
                        .name("hello.txt")
                        .type(Item.Type.FILE)
                        .parentId(folder.getId())
                        .build()));
    }
    @Test
    void WhenAddingNewFileNotExistParentId_ThenFailed() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        assertThrows(FunctionalException.class, () ->  fileManagementService.uploadFile(file,ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.FOLDER)
                .parentId(77L)
                .build()));
    }
    private PermissionGroup getPermissionGroup() {
        return PermissionGroup
                .builder()
                .id(1L)
                .groupName("test00")
                .permissions(Arrays.asList(
                        Permission.builder()
                                .id(1L)
                                .userEmail("test@test.com")
                                .permissionLevel(Permission.Level.EDIT)
                                .build(),
                        Permission.builder().id(2L)
                                .userEmail("test001@test.com")
                                .permissionLevel(Permission.Level.VIEW)
                                .build()
                ))
                .build();
    }


}
