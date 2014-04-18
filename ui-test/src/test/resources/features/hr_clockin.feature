Feature: Clock In/Out

Scenario: Clock In/Out users with multiple assignments

Given that a clock user with multiple assignments logs into Time Managment
When she clocks in to multiple assignments and clocks out of the assignments
Then the Time Detail displays the correct assignments on the correct timeblocks
