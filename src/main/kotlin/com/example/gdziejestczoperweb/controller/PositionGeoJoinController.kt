package com.example.gdziejestczoperweb.controller

import com.example.gdziejestczoperweb.model.PositionGeoJoin
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.springframework.transaction.annotation.Transactional


import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Query
import javax.validation.Valid

@RestController
@RequestMapping("/api")
@Transactional
class PositionGeoJoinController {

    @PersistenceContext
    protected lateinit var em: EntityManager

    @PostMapping("/assignGeoToPosition")
    fun assignGeoToPosition(@Valid @RequestBody positionGeoJoin: PositionGeoJoin) {
        println("assignGeoToPosition with geoID: " + positionGeoJoin.geoId + " and positionID: " + positionGeoJoin.positionId)

        insertPositionGeoJoin(positionGeoJoin)
    }

    @Transactional
    fun insertPositionGeoJoin(positionGeoJoin: PositionGeoJoin) {
        log.print("PositionGeoJoin = $positionGeoJoin")
        var query = em.createNativeQuery("INSERT INTO position_geo(geo_id, position_id) VALUES (:geoID, :positionID)")
                .setParameter("geoID", positionGeoJoin.geoId)
                .setParameter("positionID", positionGeoJoin.positionId)

        query.executeUpdate()
    }


    @PostMapping("/assignGeoToPosition/list")
    fun assignGeoToPositionList(@Valid @RequestBody positionGeoJoinList: List<PositionGeoJoin>) {
        for (p in positionGeoJoinList) {
            insertPositionGeoJoin(p)
        }
    }
}
