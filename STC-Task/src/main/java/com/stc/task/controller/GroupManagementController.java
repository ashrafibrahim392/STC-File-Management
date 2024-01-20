package com.stc.task.controller;


import com.stc.task.dto.GroupPermissionDTO;
import com.stc.task.dto.ResponseMessage;
import com.stc.task.service.PermissionManagementService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@Slf4j
@Tag(name = "Group Management", description = "Group controller to add ,edit and list Group")
public class GroupManagementController {
    private final PermissionManagementService permissionManagementService;
    @PostMapping(value = "/group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "403", description = "Not valid action ")
    })

    public ResponseEntity<ResponseMessage> createGroup(@Valid @RequestBody GroupPermissionDTO groupPermissionDTO)  {
        permissionManagementService.createGroup(groupPermissionDTO);
        return new ResponseEntity<>(new ResponseMessage("GROUP_CREATED_SUCCESSFULLY"), HttpStatus.OK);
    }
}
