package com.example.gdziejestczoperweb.controller

import com.example.gdziejestczoperweb.model.User
import com.example.gdziejestczoperweb.repository.UserRepository
import org.springframework.web.bind.annotation.*

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class UserController(private val userRepository: UserRepository) {

    @PersistenceContext
    protected lateinit var em: EntityManager


    @GetMapping("/user/all")
    fun getAllUsers(): List<User> =
            userRepository.findAll()


    @GetMapping("/user")
    fun find(@RequestParam("login") login: String, @RequestParam("password") password: String): List<User>? {

        println("Login = $login password = $password")

        val userList = em.createNamedQuery("user.findByLoginAndPassword")
                .setParameter("login", login)
                .setParameter("password", password)
                .resultList

        return if (userList.size == 0) {
            println("nie znaleziono rekordu")
            null
        } else {
            userList as List<User>
        }
    }


    @PostMapping("/user")
    fun createNewUser(@Valid @RequestBody user: User): User =
            userRepository.save(user)
}
