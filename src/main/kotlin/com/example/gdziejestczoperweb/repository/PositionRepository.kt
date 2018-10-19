package com.example.gdziejestczoperweb.repository

import com.example.gdziejestczoperweb.model.Position
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PositionRepository : JpaRepository<Position, Long>