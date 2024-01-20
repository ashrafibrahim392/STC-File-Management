package com.stc.task.config;



import com.stc.task.dto.ItemDTORequest;
import com.stc.task.exception.FunctionalException;
import com.stc.task.model.Item;
import com.stc.task.model.Permission;
import com.stc.task.service.FileManagementService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {


    private HttpServletRequest request;
    private Object filterObject;
    private Object returnObject;
    private Object target;

    FileManagementService fileManagementService;
    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean hasEditPermission(ItemDTORequest itemDTORequest) throws FunctionalException {
        if(Item.Type.SPACE.equals(itemDTORequest.getType()))
            return true;

        Optional<Permission> permissionOpt =  fileManagementService
                .findPermissionByEmailAndItemId(itemDTORequest.getRequesterEmail(),itemDTORequest.getParentId());

        if(permissionOpt.isEmpty())
            return false;

        return  permissionOpt
                .map(p-> p.getPermissionLevel().equals(Permission.Level.EDIT))
                .orElse(false);
    }
    public boolean hasViewPermission(String email, long id ) throws FunctionalException {
        Optional<Permission> permissionOpt =  fileManagementService.findPermissionByEmailAndItemId(email,id);
        if(permissionOpt.isEmpty())
            return false;

       return permissionOpt.map(p-> p.getPermissionLevel().equals(Permission.Level.VIEW) || p.getPermissionLevel().equals(Permission.Level.EDIT))
                .orElse(false);
    }
    public void setFileManagementService(FileManagementService fileManagementService){
        this.fileManagementService =fileManagementService;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }
}