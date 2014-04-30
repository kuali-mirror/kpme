Feature: Login

Scenario: A drunk user should not be able to login

Given that a user is drunk
When he tries to login
Then the computer squirts water in his eye

Scenario:
Given that blood alcohal level is high
When a user tries to drive a car
Then the car won't start