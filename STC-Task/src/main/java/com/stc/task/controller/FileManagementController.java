package com.stc.task.controller;

import com.stc.task.dto.ItemDTORequest;
import com.stc.task.dto.ItemDTOResponse;
import com.stc.task.dto.ResponseMessage;
import com.stc.task.exception.FunctionalException;
import com.stc.task.service.FileManagementService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@Slf4j
@Tag(name = "Items Management", description = "Items controller to add ,edit and list items")
public class FileManagementController {
    private final FileManagementService fileManagementService;

    @PostMapping(value = "/item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "403", description = "Not valid action ")
    })
    @PreAuthorize("hasEditPermission(#item)")
    public ResponseEntity<ResponseMessage> createItem(@Valid @RequestBody ItemDTORequest item) throws FunctionalException {
        fileManagementService.createItem(item);
        return new ResponseEntity<>(new ResponseMessage("ITEM_CREATED_SUCCESSFULLY"), HttpStatus.CREATED);
    }


    @PostMapping("/file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "403", description = "Not valid action ")
    })
    @PreAuthorize("hasEditPermission(#item)")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file,
                                                      @Valid @RequestPart ItemDTORequest item) throws FunctionalException, IOException {
        fileManagementService.uploadFile(file,item);
        return new ResponseEntity<>(new ResponseMessage("ITEM_CREATED_SUCCESSFULLY"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "403", description = "Not valid action ")
    })
    @PreAuthorize("hasViewPermission(#requesterEmail, #id)")
    public ResponseEntity<ItemDTOResponse> getFileMetaData(@PathVariable Long id ,  @RequestParam String requesterEmail ) throws FunctionalException, IOException {

        return new ResponseEntity<>(fileManagementService.getFileMetadata(id), HttpStatus.OK);
    }
}