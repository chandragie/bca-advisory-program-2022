package com.bca.adam.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "pg-uuid")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hash_password", nullable = false)
    private String password;

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
}
