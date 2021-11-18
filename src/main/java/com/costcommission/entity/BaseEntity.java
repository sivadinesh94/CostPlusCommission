package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private  LocalDateTime createdTimeStamp;
    private String createdBy;
    @Column(nullable = false, updatable = false)
    @UpdateTimestamp
    private  LocalDateTime updatedTimeStamp;
    private String updatedBy;
    @Column(nullable = false,columnDefinition = "boolean default false")
    private boolean deleted;

}
