package com.bca.adam.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "login")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Login {

    @Id
    @Column(name = "sessionid", updatable = false, nullable = false)
    private String sessionid;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "updated_date", nullable = true)
    private Date updatedDate;

    @Column(name = "is_valid", nullable = false)
    private boolean isValid;

    public Login(String userId) {
        this.sessionid = UUID.randomUUID().toString().replaceAll("-", "");
        this.userId = userId;
        this.isValid = true;
        this.createdDate = Calendar.getInstance().getTime();
    }

}
