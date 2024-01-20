package com.stc.task.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    public enum Level {
        EDIT,VIEW
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    @Enumerated(EnumType.STRING)
    private Level permissionLevel;
    @OneToOne
    private PermissionGroup group;
}
