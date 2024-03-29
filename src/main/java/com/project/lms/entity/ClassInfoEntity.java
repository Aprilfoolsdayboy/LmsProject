package com.project.lms.entity;

import com.project.lms.entity.member.EmployeeInfo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class_info")
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ClassInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_seq")
    private Long ciSeq;

    @Column(name = "ci_name", nullable = false, length = 20)
    private String ciName;

    @Column(name = "ci_limit", nullable = false)
    private Integer ciLimit;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "ci_mi_seq", nullable = false)
    private EmployeeInfo employee;
    
    @Column(name = "ci_rating", nullable = false)
    private Integer ciRating;

    public Long getCiSeq(){
        return ciSeq;
    }
}
