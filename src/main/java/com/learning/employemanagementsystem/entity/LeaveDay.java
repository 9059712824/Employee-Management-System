package com.learning.employemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "leave_day")
public class LeaveDay {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "leave_uuid")
    @JsonBackReference
    private Leave leave;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @Column(nullable = false)
    @Size(max = 50)
    private String reason;

    @Size(max=50)
    private String comments;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedTime;

}
