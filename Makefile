# The Github continuous integration actions for this course use this Makefile,
# so do not change it unless you really know what you are doing. If the CI
# process fails because you changed this file, it is on you.

# Note that the syntax used here is typical for most forms of Unix. For
# Windows, at minimum "/" has to be replaced by "\" and ":" (colon) has to 
# be replaced by ";" (semicolon). Other changes may be necessary.

tests: compile
	java -cp resources/junit-3.8.2.jar:resources/kalah20200717.jar:bin junit.textui.TestRunner kalah.test.TestKalah

play: compile
	java -cp resources/junit-3.8.2.jar:resources/kalah20200717.jar:bin kalah.Kalah
  
compile:
	mkdir -p bin
	javac -d bin -cp resources/junit-3.8.2.jar:resources/kalah20200717.jar:bin:src src/kalah/Kalah.java
