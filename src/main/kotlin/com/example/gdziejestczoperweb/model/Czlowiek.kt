package com.example.gdziejestczoperweb.model

import javax.persistence.*
import java.io.Serializable

@Entity
@Table(name = "dom")
data class Czlowiek( @Id
                @Column(name = "dom_id")
                var dom_id: Int = 0,
                var nazwa: String? = null) : Serializable
