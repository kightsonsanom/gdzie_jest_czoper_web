package com.example.gdziejestczoperweb.controller

import com.example.gdziejestczoperweb.model.Geo
import com.example.gdziejestczoperweb.repository.GeoRepository
import com.example.gdziejestczoperweb.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class GeoController(private val geoRepository: GeoRepository,
                    private val userRepository: UserRepository) {

    @PersistenceContext
    protected lateinit var em: EntityManager

    @GetMapping("/geo")
    fun getAllGeos(): List<Geo> =
            geoRepository.findAll()


    @Synchronized
    @PostMapping("/geo/{userid}")
    fun createNewGeo(@PathVariable(value = "userid") userid: Int,
                     @Valid @RequestBody geo: Geo): Geo {
        val user = userRepository.getOne(userid)
        geo.user = user
        return geoRepository.save(geo)
    }

    @GetMapping("/geo/{id}")
    fun getGeoById(@PathVariable(value = "id") geoId: Long): ResponseEntity<Geo> {
        return geoRepository.findById(geoId).map { geo ->
            ResponseEntity.ok(geo)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/geo/latestGeoForUsers")
    fun getlatestGeoForUsers(): List<Geo> {

        val query = em.createNativeQuery("SELECT * "
                + " FROM geo g  INNER JOIN ("
                + " SELECT user_id, MAX(date) maxCzas"
                + " FROM geo"
                + " GROUP BY user_id "
                + " ) t ON g.user_id = t.user_id AND g.date = t.maxCzas", Geo::class.java)

        return query.resultList as List<Geo>
    }

    @Synchronized
    @PutMapping("/geo/{userid}")
    fun edit(@PathVariable(value = "userid") userid: Int,
             @Valid @RequestBody newGeo: Geo) {
        val user = userRepository.getOne(userid)
        newGeo.user = user
        geoRepository.findById(newGeo.id).map { existingGeo ->
            val updatedGeo = existingGeo.copy(
                    id = newGeo.id,
                    location = newGeo.location,
                    date = newGeo.date,
                    displayText = newGeo.displayText,
                    user = newGeo.user
            )
            geoRepository.save(updatedGeo)
        }.orElse(geoRepository.save(newGeo))
    }

    @Synchronized
    @PutMapping("/geo/geoList/{userid}")
    fun edit(@PathVariable(value = "userid") userid: Int, @Valid @RequestBody newGeoList: List<Geo>) {
        val user = userRepository.getOne(userid)
        newGeoList.forEach { it ->
            it.user = user
            geoRepository.findById(it.id).map { existingGeo ->
                val updatedGeo = existingGeo.copy(
                        id = it.id,
                        location = it.location,
                        date = it.date,
                        displayText = it.displayText,
                        user = it.user
                )
                geoRepository.save(updatedGeo)
            }.orElse(geoRepository.save(it))
        }
    }


//
//    @DeleteMapping("/articles/{id}")
//    fun deleteArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Void> {
//
//        return articleRepository.findById(articleId).map { article  ->
//            articleRepository.delete(article)
//            ResponseEntity<Void>(HttpStatus.OK)
//        }.orElse(ResponseEntity.notFound().build())
//
//    }


}