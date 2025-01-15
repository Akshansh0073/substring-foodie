package com.substring.foodie.substring_foodie.entity;

import com.substring.foodie.substring_foodie.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "foodie_users")
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    @Size(min = 3, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;

    @Column(nullable = false)
    private String email;
    private String password;
    private String address;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isAvailable = true; // applicable for delivery boy
    private LocalDate createdDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurant> restaurants = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roleEntities = new ArrayList<>();

    @PrePersist
    public void preSave() {
        this.createdDate = LocalDate.now();
    }

    @PostPersist
    public void postSave() {
        System.out.println("entity saved : " + this.getId());
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("entity updated : " + this.getId());
    }

}
