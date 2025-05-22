Source for TextPrompt: https://github.com/tips4java/tips4java/blob/main/source/TextPrompt.java

The project above is meant to be used as a way to manage assignments for college courses based on their due date. 
The implementation of the ClassManager is done with a HashMap that assigns a college course (String) as the key
and a Task List in the form of a PriorityQueue. If an assignment is past its due date, it is automatically removed
from the Manager upon opening the application. 
