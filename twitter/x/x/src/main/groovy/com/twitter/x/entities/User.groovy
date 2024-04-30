package com.twitter.x.entities

import groovy.transform.Canonical
import groovy.transform.TupleConstructor
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@TupleConstructor
@Canonical
@Document(collection = "users")
class User {

    @Id
    private ObjectId id
    private String username
    private String password
    private String email
    private Date registrationDate
    private Set<ObjectId> subscriptions = []
    private Set<ObjectId> followers = []
    private Role role

    ObjectId getId() {
        return id
    }

    String getUsername() {
        return username
    }

    String getPassword() {
        return password
    }

    String getEmail() {
        return email
    }

    Date getRegistrationDate() {
        return registrationDate
    }

    Set<ObjectId> getSubscriptions() {
        return subscriptions
    }

    Set<ObjectId> getFollowers() {
        return followers
    }

    Role getRole() {
        return role
    }

    void setId(ObjectId id) {
        this.id = id
    }

    void setUsername(String username) {
        this.username = username
    }

    void setPassword(String password) {
        this.password = password
    }

    void setEmail(String email) {
        this.email = email
    }

    void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate
    }

    void setSubscriptions(Set<ObjectId> subscriptions) {
        this.subscriptions = subscriptions
    }

    void setFollowers(Set<ObjectId> followers) {
        this.followers = followers
    }

    void setRole(Role role) {
        this.role = role
    }

    static Builder builder() {
        return new Builder()
    }
}

class Builder {

    private ObjectId id
    private String username
    private String password
    private String email
    private Role role
    private Date registrationDate

    Builder id(ObjectId id) {
        this.id = id
        return this
    }

    Builder username(String username) {
        this.username = username
        return this
    }

    Builder password(String password) {
        this.password = password
        return this
    }

    Builder email(String email) {
        this.email = email
        return this
    }

    Builder role(Role role) {
        this.role = role
        return this
    }

    Builder registrationDate(Date registrationDate) {
        this.registrationDate = registrationDate
        return this
    }

    User build() {
        return new User(username: username, password: password, email: email, role: role)
    }
}
