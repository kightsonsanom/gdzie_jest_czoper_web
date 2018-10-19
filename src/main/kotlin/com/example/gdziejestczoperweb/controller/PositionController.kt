package com.example.gdziejestczoperweb.controller

import com.example.gdziejestczoperweb.model.Position
import com.example.gdziejestczoperweb.repository.PositionRepository
import com.example.gdziejestczoperweb.repository.UserRepository
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class PositionController(private val positionRepository: PositionRepository,
                         private val userRepository: UserRepository) {

    @PersistenceContext
    protected lateinit var em: EntityManager

    @GetMapping("/position")
    fun getAllPositions(): List<Position> =
            positionRepository.findAll()

    @GetMapping("/position/{id}")
    fun getPositionById(@PathVariable(value = "id") positionId: Long): ResponseEntity<Position> {
        return positionRepository.findById(positionId).map { position ->
            ResponseEntity.ok(position)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("/position/{userid}")
    fun createNewPosition(@PathVariable(value = "userid") userid: Int, @Valid @RequestBody position: Position): Position{
        val user = userRepository.getOne(userid)
        position.user = user
        return positionRepository.save(position)
    }

    @PutMapping("/position/{userid}")
    fun edit(@PathVariable(value = "userid") userid: Int, @Valid @RequestBody newPosition: Position) {
        val user = userRepository.getOne(userid)
        newPosition.user = user
        positionRepository.findById(newPosition.id).map { existingPosition ->

            val updatePosition = existingPosition.copy(
                    id = newPosition.id,
                    startDate = newPosition.startDate,
                    endDate = newPosition.endDate,
                    firstLocationDate = newPosition.firstLocationDate,
                    lastLocationDate = newPosition.lastLocationDate,
                    startLocation = newPosition.startLocation,
                    endLocation = newPosition.endLocation,
                    status = newPosition.status,
                    user = newPosition.user
            )
            positionRepository.save(updatePosition)
        }.orElse(positionRepository.save(newPosition))
    }


    @PutMapping("/position/positionList/{userid}")
    fun edit(@PathVariable(value = "userid") userid: Int, @Valid @RequestBody newPositionList: List<Position>) {
        val user = userRepository.getOne(userid)
        newPositionList.forEach { it ->
            it.user = user
            positionRepository.findById(it.id).map { existingPosition ->
                val updatedGeo = existingPosition.copy(
                        id = it.id,
                        startDate = it.startDate,
                        endDate = it.endDate,
                        firstLocationDate = it.firstLocationDate,
                        lastLocationDate = it.lastLocationDate,
                        startLocation = it.startLocation,
                        endLocation = it.endLocation,
                        status = it.status,
                        user = it.user
                )
                positionRepository.save(updatedGeo)
            }.orElse(positionRepository.save(it))
        }
    }


    @GetMapping("position/positionForDayAndUser")
    fun getPositionForDayAndUser(@RequestParam("userName") userName: String, @RequestParam("rangeFrom") rangeFrom: Long, @RequestParam("rangeTo") rangeTo: Long): List<Position> {

        log.print("getPositionForDayAndUser")
        val query = em.createNativeQuery("SELECT * FROM Position p INNER JOIN"
                + " (SELECT user_id FROM User u WHERE nazwa = :userName) t"
                + " ON p.user_id = t.user_id WHERE"
                + " (p.first_location_date > :rangeFrom AND p.last_location_date < :rangeTo)", Position::class.java)
                .setParameter("userName", userName)
                .setParameter("rangeFrom", rangeFrom)
                .setParameter("rangeTo", rangeTo)

        log.print("query.resultList as List<Position> = " + query.resultList as List<Position>)

        return query.resultList as List<Position>
    }
}


//    @GET
//    @Path("login")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<User> find(@QueryParam("login") String login, @QueryParam("password") String password) {
//
//        System.out.println("Login = " + login + " password = " + password);
//        List<User> czoperList = em.createNamedQuery("czoper.findByLoginAndPassword")
//                .setParameter("login", login)
//                .setParameter("password", password)
//                .getResultList();
//
//        if (czoperList.size() == 0){
//            System.out.println("nie znaleziono rekordu");
//            return null;
//
//        } else {
//
//            if (czoperList.get(0) != null){
//                User czoper = czoperList.get(0);
//                if (czoper.getLogin().equals("sanomik")) {
//                    czoperList = findAll();
//                }
//
//                return  czoperList;
//            } else {
//                return null;
//            }
//        }
//    }
