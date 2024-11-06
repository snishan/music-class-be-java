package com.mcms.music.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "name", length = 100)
    private String teacherName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "status", columnDefinition = "TINYINT default 1")
    private boolean status;

    @ManyToMany(mappedBy = "teachers")
    private Set<Class> classes;

}
