package com.twitter.x.controllers

import com.twitter.x.entities.User
import org.bson.types.ObjectId
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Specification
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest extends Specification {

    @LocalServerPort
    private int port = 8082

    String baseUrl = "http://localhost:" + port
    String userAID = "662fcd06af0c7a5cb3d93ca3"
    String userBID = "662fcd1faf0c7a5cb3d93ca4"
    String userCID = "60735a4d64a5b50c5055e7fc"

    private final TestRestTemplate restTemplate = new TestRestTemplate()

    def "Test get all users endpoint"() {
        given:
        def expectedStatusCode = HttpStatus.OK

        when:
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/api/users", List)

        then:
        response.statusCode == expectedStatusCode
        response.body.size() > 0
    }

    def "Test get user by id endpoint"() {
        given:
        ObjectId userId = new ObjectId(userBID)
        def expectedStatusCode = HttpStatus.OK

        when:
        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + "/api/users/$userId", User)

        then:
        response.statusCode == expectedStatusCode
    }

    def "Test subscribe to user endpoint"() {
        given:
        ObjectId userId = new ObjectId(userAID)
        ObjectId targetUserId = new ObjectId(userBID)
        def expectedStatusCode = HttpStatus.NO_CONTENT

        when:
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/api/users/$userId/subscribe/$targetUserId", HttpMethod.PUT, null, Void.class)

        then:
        response.statusCode == expectedStatusCode
    }

    def "Test unsubscribe from user endpoint"() {
        given:
        ObjectId userId = new ObjectId(userAID)
        ObjectId targetUserId = new ObjectId(userBID)
        def expectedStatusCode = HttpStatus.NO_CONTENT

        when:
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/api/users/$userId/unsubscribe/$targetUserId", HttpMethod.PUT, null, Void.class)

        then:
        response.statusCode == expectedStatusCode
    }

    def "Test delete user endpoint"() {
        given:
        ObjectId userId = new ObjectId(userCID)
        def expectedStatusCode = HttpStatus.NO_CONTENT

        when:
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/api/users/$userId", HttpMethod.DELETE, null, Void.class)

        then:
        response.statusCode == expectedStatusCode
    }
}