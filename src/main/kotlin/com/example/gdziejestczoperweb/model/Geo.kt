package com.example.gdziejestczoperweb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.*
import javax.persistence.*
import javax.xml.bind.annotation.XmlTransient
import java.io.Serializable
import java.util.HashSet

@Entity
@NamedQueries(NamedQuery(name = "geo.findAll", query = "SELECT g FROM Geo g"),
        NamedQuery(name = "geo.findByID", query = "SELECT g FROM Geo g WHERE g.id=:geoID"),
        NamedQuery(name = "geo.findLatestGeoByUser", query = "SELECT g.id,g.location, min(g.date), g.displayText, g.user FROM Geo g GROUP BY g.user"))
@Table(name = "geo")
@JsonIgnoreProperties("positions")
data class Geo(@Id
               val id: Long = 0,
               val location: String? = null,
               val date: Long = 0,
               val displayText: String? = null,

               @ManyToOne
               @JoinColumn(name = "user_id")
               var user: User? = null,

               @ManyToMany(cascade = [(CascadeType.ALL)])
               @JoinTable(name = "Position_Geo",
                       joinColumns = [(JoinColumn(
                               name = "geo_id",
                               referencedColumnName = "id"))],
                       inverseJoinColumns = [(JoinColumn(
                               name = "position_id",
                               referencedColumnName = "id"))])
               var positions: List<Position>  = mutableListOf()) : Serializable {

    override fun toString(): String {
        return "Geo(id=$id, location=$location, date=$date, displayText=$displayText)"
    }
}
