package com.example.gdziejestczoperweb.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "dom")
data class Dom( @Id
                @Column(name = "dom_id")
                var dom_id: Int = 0,

                var nazwa: String? = null) : Serializable
