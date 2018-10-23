package com.example.gdziejestczoperweb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient
import java.io.Serializable

@Entity
@NamedQueries(NamedQuery(name = "position.findAll", query = "SELECT p FROM Position p"), NamedQuery(name = "position.findByID", query = "SELECT p FROM Position p WHERE p.id=:positionID"))
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "position_id")
@Table(name = "position")
@JsonIgnoreProperties("geos","user", allowSetters = true)
data class Position(@Id
                    var id: Long = 0,

                    var startDate: String? = null,
                    var endDate: String? = null,
                    var firstLocationDate: Long = 0,
                    var lastLocationDate: Long = 0,
                    var startLocation: String? = null,
                    var endLocation: String? = null,
                    var status: Int? = null,

                    @ManyToOne
                    @JoinColumn(name = "user_id")
                    var user: User? = null,

                    @ManyToMany(mappedBy = "positions")
                    var geos: List<Geo> = mutableListOf()): Serializable {

    override fun toString(): String {
        return "Position(id=$id, startDate=$startDate, endDate=$endDate, firstLocationDate=$firstLocationDate, lastLocationDate=$lastLocationDate, startLocation=$startLocation, endLocation=$endLocation, status=$status)"
    }
}
