package com.mcms.music.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "name", length = 100)
    private String studentName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "status", columnDefinition = "TINYINT default 1")
    private boolean status;

    @ManyToMany(mappedBy = "students")
    private Set<Class> classes;
}
