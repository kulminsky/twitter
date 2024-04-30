package com.twitter.x.controllers

import com.twitter.x.entities.Comment
import org.bson.types.ObjectId
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import spock.lang.Specification
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerTest extends Specification {

    @LocalServerPort
    private int port = 8082

    String baseUrl = "http://localhost:" + port
    String userAID = "662fcd06af0c7a5cb3d93ca3"
    String postID = "662fd464b7ee076f61f1d478"
    String commentID = "662fd6c88e9fbb792b3d435c"

    private final TestRestTemplate restTemplate = new TestRestTemplate()

    def "Test create comment endpoint"() {
        given:
        def expectedStatusCode = HttpStatus.CREATED
        def comment = new Comment("Test comment", new ObjectId(userAID))

        when:
        ResponseEntity<Comment> response = restTemplate.postForEntity(baseUrl + "/api/posts/$postID/comments?userId=$userAID", comment, Comment)

        then:
        response.statusCode == expectedStatusCode
        response.body.content == comment.content
    }

    def "Test get comments by post id endpoint"() {
        given:
        def expectedStatusCode = HttpStatus.OK

        when:
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/api/posts/$postID/comments", List)

        then:
        response.statusCode == expectedStatusCode
    }

    def "Test update comment endpoint"() {
        given:
        def updatedComment = new Comment("Updated comment")

        when:
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/api/posts/$postID/comments/$commentID", HttpMethod.PUT, new HttpEntity<>(updatedComment), Void.class)

        then:
        response.statusCode == HttpStatus.NO_CONTENT
    }

    def "Test delete comment endpoint"() {
        given:
        def commentId = new ObjectId()

        when:
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/api/posts/$postID/comments/$commentId", HttpMethod.DELETE, null, Void.class)

        then:
        response.statusCode == HttpStatus.NO_CONTENT
    }
}
