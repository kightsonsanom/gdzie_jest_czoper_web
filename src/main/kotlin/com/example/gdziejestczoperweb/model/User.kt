package com.example.gdziejestczoperweb.model


import javax.persistence.*
import javax.persistence.Id

import java.io.Serializable


@Entity
@NamedQueries(NamedQuery(name = "user.findAll", query = "SELECT f FROM User f"),
        NamedQuery(name = "user.findByName", query = "SELECT f FROM User f WHERE f.name = :name"),
        NamedQuery(name = "user.findByLogin", query = "SELECT f FROM User f WHERE f.login = :login"),
        NamedQuery(name = "user.findByLoginAndPassword", query = "SELECT f FROM User f WHERE EXISTS (SELECT f FROM User f WHERE (f.login = :login AND f.password = :password))"))

@Table(name = "user")
data class User(@Id
                @Column(name = "userID")
                var userID: Int = 0,

                var name: String? = null,
                var login: String? = null,
                var password: String? = null) : Serializable

