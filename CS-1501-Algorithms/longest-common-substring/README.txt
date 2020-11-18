Longest Common Subsequence
Solomon Heisey
November 2019


INTRODUCTION

This program finds the Longest Common Subsequence of two strings. This uses a top-down recursive algorithm, implementing
a memo to keep track of overlapping subproblems. In this case the memo keeps track of the size of the substring at (i,j).
The program works by starting at the base index of (0,0) and recursing through both strings, trying every possibility and
keeping track of the best one.


INSTALLATION

The following files should be present:

     LCS.java
     TestLCS.java
     README.txt


USAGE

Compile and run with Java 2 1.5 or later:

     javac TestLCS.java
     java TestLCS <string1> <string2>

For instance:

     java TestLCS axxbyyycz 1a2b333c44444

outputs:

     LCS of axxbyyycz and 1a2b333c44444: abc

Also, the command:

     java TestLCS cat dog

outputs:

     LCS of cat and dog:

since the LCS of "cat" and "dog" is the empty string.

PROBLEMS

No known problems.

