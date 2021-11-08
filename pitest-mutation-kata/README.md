# **What is the Pitest Mutation Kata?**
The Pitest Mutation Kata demonstrates how to improve the quality of your jUnit tests using 
[Pitest](http://pitest.org).\
You are a hunter, and your mission is to hunt mutants.

# **What will I learn?**
* What are mutants? Why should I hunt them?  
* What is PIT? 
* How to start using PIT in my project?
* How to view and analyze PIT reports?
* How to improve PIT score?

# **Quick Intro**
As a part of mutation testing faults (or mutants) are automatically seeded into your code, then your tests are run.
If your tests fail the mutants are killed, if your tests pass then the mutants survive. 
You hunt the mutants down by improving your tests so that they always fail when code mutations are introduced.
The quality of your tests can be gauged from the percentage of mutations killed.
[Pitest](http://pitest.org) is the mutation system we use.


# **How to execute PIT test coverage maven target?**
Run `mvn -pl pitest-mutation-kata clean test org.pitest:pitest-maven:mutationCoverage`.\
Build will fail with `"Mutation score of 72 is below threshold of 100"` message.\
Open PIT dashboard (target/pit-reports/index.html) in any web browser and check mutation coverage report for more details.\
You will discover that `Position` class has 100% code coverage, but only 72% mutation test coverage. 

# **What is my mission?**
Your mission is to analyze PIT coverage reports and to modify `PositionTest` to hunt all the mutants down and increase mutation score to 100 and to get the build target to pass.\
Keep re-running PIT target and checking dashboard to make sure mutation score improves with your changes.\
Good luck!



