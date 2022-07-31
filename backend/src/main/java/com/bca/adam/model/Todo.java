package com.bca.adam.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "todo")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "pg-uuid")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_date")
    @ColumnDefault("NOW()")
    private Date createdDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "is_done")
    private boolean isDone;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    public Todo(String title, String createdBy) {
        this.title = title;
        this.createdBy = createdBy;
        this.createdDate = Calendar.getInstance().getTime();
    }

}
