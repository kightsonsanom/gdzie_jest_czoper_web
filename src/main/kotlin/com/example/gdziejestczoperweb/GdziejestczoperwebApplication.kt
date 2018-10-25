package com.example.gdziejestczoperweb

import com.example.gdziejestczoperweb.model.Geo
import com.example.gdziejestczoperweb.model.Position
import com.example.gdziejestczoperweb.model.User
import com.example.gdziejestczoperweb.repository.GeoRepository
import com.example.gdziejestczoperweb.repository.PositionRepository
import com.example.gdziejestczoperweb.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GdziejestczoperwebApplication: SpringBootServletInitializer() {


    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(GdziejestczoperwebApplication::class.java)
    }


    private val log = LoggerFactory.getLogger(GdziejestczoperwebApplication::class.java)

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            positionRepository: PositionRepository,
                            geoRepository: GeoRepository) = CommandLineRunner {

        val user1 = User(1, "tomek", "tomek", "tomek")
        val user2 = User(2, "pawel", "pawel", "pawel")

        userRepository.save(user1)
        userRepository.save(user2)

        val positions1 = mutableListOf(Position(3, "14:05:06", "14:05:07", 1540425830005, 1540425630005, "Botaniczna", "Sucharskiego", 3, user2),
                Position(4, "14:05:06", "14:05:07", 1540425860005, 1540423830005, "Botaniczna", "Sucharskiego", 3, user2),
                Position(6, "14:05:06", "14:05:07", 1550425830005, 1550425830005, "Botaniczna", "Sucharskiego", 1, user2))

        val positions2 = mutableListOf(Position(1, "14:05:06", "14:05:07", 1530425830005, 1530425830005, "Botaniczna", "Sucharskiego", 3, user1),
                Position(2, "14:05:06", "14:05:07", 1540425830005, 1540425830005, "Botaniczna", "Sucharskiego", 3, user1))

        positionRepository.saveAll(positions1)
        positionRepository.saveAll(positions2)

        val geos1 = mutableListOf(Geo(1, "51.941067, 15.504336", 12, "botan12", user1, positions1),
                Geo(2, "51.941067, 15.504336", 13, "ptasia13", user1, positions1))

        val geos2 = mutableListOf(Geo(3, "51.941067, 15.504336", 1, "sucharskiego1", user2, positions2),
                Geo(4, "51.941067, 15.504336", 25, "robocza23", user2, positions2))


        geoRepository.saveAll(geos1)
        geoRepository.saveAll(geos2)

    }
}


fun main(args: Array<String>) {
    runApplication<GdziejestczoperwebApplication>(*args)
}

