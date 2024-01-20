package com.stc.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stc.task.dto.ItemDTORequest;
import com.stc.task.model.Item;
import com.stc.task.model.Permission;
import com.stc.task.model.PermissionGroup;
import com.stc.task.repository.ItemRepository;
import com.stc.task.repository.PermessionGroupRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import static org.springframework.web.servlet.function.ServerResponse.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
public class FileManagementControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PermessionGroupRepository permessionGroupRepository;
    @Autowired
    private ItemRepository itemRepository;
   private Item space;
   private Item folder;
    @BeforeAll
    public void startUp() {
        permessionGroupRepository.save(getPermissionGroup());
         space = itemRepository.save(Item.builder()
                .name("test00")
                .type(Item.Type.SPACE)
                .permissionGroup(getPermissionGroup()).build());
         folder = itemRepository.save(Item.builder()
                .name("test00")
                .type(Item.Type.FOLDER)
                .parent(space)
                .permissionGroup(getPermissionGroup()).build());
    }

    @Test
    public void WhenSaveSpace_ThenOk() throws Exception {
        ItemDTORequest itemDTORequest = ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.SPACE)
                .groupId(1L)
                .requesterEmail("test@test.com")
                .build();
        mvc.perform(MockMvcRequestBuilders
                        .post("/v1/item")
                        .content(new ObjectMapper().writeValueAsString(itemDTORequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void WhenCreateFolderWithNoPermission_ThenFailed() throws Exception {
        ItemDTORequest itemDTORequest = ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.FOLDER)
                .parentId(space.getId())
                .requesterEmail("test001@test.com")
                .build();
        mvc.perform(MockMvcRequestBuilders
                        .post("/v1/item")
                        .content(new ObjectMapper().writeValueAsString(itemDTORequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void WhenCreateFolderWithPermission_ThenOk() throws Exception {

        Item space = itemRepository.save(Item.builder()
                .name("test00")
                .type(Item.Type.SPACE)
                .permissionGroup(getPermissionGroup()).build());

        ItemDTORequest itemDTORequest = ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.FOLDER)
                .parentId(space.getId())
                .requesterEmail("test@test.com")
                .build();
        mvc.perform(MockMvcRequestBuilders
                        .post("/v1/item")
                        .content(new ObjectMapper().writeValueAsString(itemDTORequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void WhenCreateFileWithPermission_ThenOk() throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );


        ItemDTORequest itemDTORequest = ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.FOLDER)
                .parentId(folder.getId())
                .requesterEmail("test@test.com")
                .build();

        MockMultipartFile itemDto
                = new MockMultipartFile(
                "item",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                new ObjectMapper().writeValueAsString(itemDTORequest).getBytes()
        );
        mvc.perform(MockMvcRequestBuilders
                        .multipart("/v1/file")
                        .file(itemDto)
                        .file(file))
                .andExpect(status().isCreated());
    }
    @Test
    public void WhenCreateFileWithNoPermission_ThenFailed() throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );


        ItemDTORequest itemDTORequest = ItemDTORequest.builder()
                .name("test00")
                .type(Item.Type.FOLDER)
                .parentId(folder.getId())
                .requesterEmail("test001@test.com")
                .build();

        MockMultipartFile itemDto
                = new MockMultipartFile(
                "item",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                new ObjectMapper().writeValueAsString(itemDTORequest).getBytes()
        );
        mvc.perform(MockMvcRequestBuilders
                        .multipart("/v1/file")
                        .file(itemDto)
                        .file(file))
                .andExpect(status().isUnauthorized());
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
