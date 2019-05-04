# ToeicTimer
`Version: 0.0.1`    
`Editor: Tearsyu`    
This is a tool which provids a simple TOEIC exercise timer.   
Choosing the number of questions you need to finish and the estimate time then we start.     

![]("preview1.PNG")
![]("preview2.PNG")


## NOTICE
There is a memery lacking problem... when I exit the program, if the timer thead doesn't finish, then it won't release resource, I don't know how to stop it even I do cancel the thread with: 
```java
timer.cancel();
```