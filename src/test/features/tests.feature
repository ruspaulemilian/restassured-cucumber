Feature: R3pi Assignment

  Background:
    Given The application is up and running

  Scenario: Verify that the address details is printed and the email is in the right format
    When I get an existing user '/users/1'
    Then the status code is 200
    When I print the user address from the response
    Then The email address it`s in the right format

  Scenario: Verify that the associated post of a certain user is dispayed along with the details
    When I get all the associated posts of the user '/posts/1'
    Then the status code is 200
    And The response includes the following
      | userId | 1                                                                                                                                                                 |
      | id     | 1                                                                                                                                                                 |
      | title  | sunt aut facere repellat provident occaecati excepturi optio reprehenderit                                                                                        |
      | body   | quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto |

  Scenario: Verify that the user details are successully created as a resource on the server when performing a post request
    When I perform a post using the same userID with a valid title and body
    Then the status code is 201
    And The response includes the following
      | userId | 1              |
      | id     | 101            |
      | title  | My test        |
      | body   | This is a test |








