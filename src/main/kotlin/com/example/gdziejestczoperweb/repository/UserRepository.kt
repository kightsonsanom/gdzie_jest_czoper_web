package com.example.gdziejestczoperweb.repository

import com.example.gdziejestczoperweb.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int>