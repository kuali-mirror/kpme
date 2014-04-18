Feature: Login

Scenario: A drunk user should not be able to login

Given that a user is drunk
When he tries to login
Then the computer squirts water in his eye