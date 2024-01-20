package com.stc.task.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    public enum Type {
        SPACE, FOLDER, FILE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String name;
    @OneToOne
    private PermissionGroup permissionGroup;
    @OneToOne
    private Item parent;

    private String contentType;

}
