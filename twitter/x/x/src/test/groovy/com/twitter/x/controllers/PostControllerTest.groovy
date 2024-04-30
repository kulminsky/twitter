package com.twitter.x.controllers

import com.twitter.x.entities.Post
import org.bson.types.ObjectId
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTest extends Specification {

    @LocalServerPort
    private int port = 8082

    String baseUrl = "http://localhost:" + port
    String userAID = "662fcd06af0c7a5cb3d93ca3"
    String postID = "662fd464b7ee076f61f1d478"

    private final TestRestTemplate restTemplate = new TestRestTemplate()

    def "Test create post endpoint"() {
        given:
        ObjectId userId = new ObjectId(userAID)
        Post post = new Post("Test Content")
        def expectedStatusCode = HttpStatus.CREATED

        when:
        ResponseEntity<Post> response = restTemplate.postForEntity(baseUrl + "/api/posts?userId=$userId", post, Post)

        then:
        response.statusCode == expectedStatusCode
        response.body.content == post.content
    }

    def "Test get all posts endpoint"() {
        given:
        def expectedStatusCode = HttpStatus.OK

        when:
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/api/posts", List)

        then:
        response.statusCode == expectedStatusCode
        response.body.size() > 0
    }

    def "Test get post by id endpoint"() {
        given:
        ObjectId postId = new ObjectId(postID)
        def expectedStatusCode = HttpStatus.OK

        when:
        ResponseEntity<Post> response = restTemplate.getForEntity(baseUrl + "/api/posts/$postId", Post)

        then:
        response.statusCode == expectedStatusCode
    }

    def "Test update post endpoint"() {
        given:
        ObjectId postId = new ObjectId(postID)
        Post updatedPost = new Post("Updated Content")
        def expectedStatusCode = HttpStatus.NO_CONTENT

        when:
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/api/posts/$postId", HttpMethod.PUT, new HttpEntity<>(updatedPost), Void.class)

        then:
        response.statusCode == expectedStatusCode
    }

    def "Test like post endpoint"() {
        given:
        ObjectId postId = new ObjectId(postID)
        def expectedStatusCode = HttpStatus.OK

        when:
        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl + "/api/posts/$postId/like", null, Void.class)

        then:
        response.statusCode == expectedStatusCode
    }

    def "Test unlike post endpoint"() {
        given:
        ObjectId postId = new ObjectId(postID)
        def expectedStatusCode = HttpStatus.OK

        when:
        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl + "/api/posts/$postId/unlike", null, Void.class)

        then:
        response.statusCode == expectedStatusCode
    }

    def "Test delete post endpoint"() {
        given:
        ObjectId postId = new ObjectId("60735a4d64a5b50c5055e7fc")
        def expectedStatusCode = HttpStatus.NO_CONTENT

        when:
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/api/posts/$postId", HttpMethod.DELETE, null, Void.class)

        then:
        response.statusCode == expectedStatusCode
    }
}