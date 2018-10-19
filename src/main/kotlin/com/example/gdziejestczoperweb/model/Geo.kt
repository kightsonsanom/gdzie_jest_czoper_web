package com.example.gdziejestczoperweb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.*
import javax.persistence.*
import javax.xml.bind.annotation.XmlTransient
import java.io.Serializable
import java.util.HashSet

@Entity
@NamedQueries(NamedQuery(name = "geo.findAll", query = "SELECT g FROM Geo g"), NamedQuery(name = "geo.findByID", query = "SELECT g FROM Geo g WHERE g.id=:geoID"), NamedQuery(name = "geo.findLatestGeoByUser", query = "SELECT g.id,g.location, min(g.date), g.displayText, g.user FROM Geo g GROUP BY g.user"))
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "geo_id")
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

               @ManyToMany(cascade = arrayOf(CascadeType.ALL))
               @JoinTable(name = "Position_Geo", joinColumns = arrayOf(JoinColumn(name = "geo_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "position_id", referencedColumnName = "id")))
               var positions: List<Position>  = mutableListOf()) : Serializable {

    override fun toString(): String {
        return "Geo(id=$id, location=$location, date=$date, displayText=$displayText)"
    }
}
