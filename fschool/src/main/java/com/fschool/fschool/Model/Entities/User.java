package com.fschool.fschool.Model.Entities;

import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "UserIDSequence",
                        sequenceName = "UserIDSequence", 
                        allocationSize = 1,
                        initialValue = 18010001
                        )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "UserIDSequence")
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, name = "hashedPassword")
    private String password;

    @Column(nullable = false)
    private String role;

    @ManyToMany(mappedBy = "members", cascade = {CascadeType.ALL})
    @JsonBackReference
    Set<Course> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "User [birthDate=" + birthDate + ", firstName=" + firstName + ", password=" + password
                + ", id=" + id + ", lastName=" + lastName + ", role=" + role + ", sex=" + sex + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id != null && id.equals(((User) o).getId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }


    

    
}
