package com.example.gdziejestczoperweb.model

import java.io.Serializable

data class PositionGeoJoin(var positionId: Long = 0,
                      var geoId: Long = 0) : Serializable {


    override fun toString(): String {
        return "PositionGeoJoin(positionId=$positionId, geoId=$geoId)"
    }
}


