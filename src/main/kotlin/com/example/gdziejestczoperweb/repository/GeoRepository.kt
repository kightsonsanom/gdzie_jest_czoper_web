package com.example.gdziejestczoperweb.repository


import com.example.gdziejestczoperweb.model.Geo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GeoRepository : JpaRepository<Geo, Long>