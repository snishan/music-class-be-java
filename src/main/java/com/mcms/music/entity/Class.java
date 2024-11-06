package com.mcms.music.entity;

import lombok.*;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "class")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String className;

    @Column(name = "class_date", length = 30)
    private String classDate;

    @Column(name = "class_start_time", length = 10)
    private String classStartTime;

    @Column(name = "status", columnDefinition = "TINYINT default 1")
    private boolean status;

    @ManyToMany
    @JoinTable(name = "class_teacher",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teachers;

    @ManyToMany
    @JoinTable(name = "class_student",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;
}
