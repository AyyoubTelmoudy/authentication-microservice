package com.emsi.pfe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false,unique = true)
    private String publicId;
    @Column(nullable = false,unique = true)
    private String role;
    @ManyToMany(cascade = CascadeType.PERSIST,fetch=FetchType.EAGER)
    private List<User> users;
}
