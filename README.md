# concurrent-computing
[AGH-LAB] The subject introduces abstract models of concurrent processing and methods of analysis of concurrent systems used in the software engineering practice. Based on Consumer-Producer problem in different perspectives I acquired complex knowledge of problems in concurrency and how to handle with them.

## Tasks
- \[[lab1](https://github.com/MexAsimov/concurrent-computing/tree/main/lab1)] Naive approach to concurrency - threads change common buffer and result is unpredictable.
- \[[lab2](https://github.com/MexAsimov/concurrent-computing/tree/main/lab2)] Consumer-Producer problem with synchronized blocks. Problem with **deadlock** has occured.
- \[[lab3.1](https://github.com/MexAsimov/concurrent-computing/tree/main/lab3-starvation)] Consumer-Producer problem with one **lock** and two **conditions**. Heavy consumer/producer adopted to solution. Problem with **starvation** has occured - if one of the threads is waiting for resources and finally get information that probably he can access to it, there is a chance that other objects change buffer's value and he has to keep on waiting.
- **Additional problem**: If maximal buffer size is less than twice as large as maximum size of portion - then problem with **deadlock** is occured (example below)
![Deadlock - wrong buffer size](https://user-images.githubusercontent.com/58474974/142747944-9bcb871d-f684-4fee-8df8-7a4f47dd869d.png)
- \[[lab3.2](https://github.com/MexAsimov/concurrent-computing/tree/main/lab3-hasWaiters)] Consumer-Producer problem with one **lock** and four **conditions** with hasWaiters() method - incorrect approach cause of probable **deadlock** - if e.g. one of the consumer threads enter 'while with hasWaiters()' in time between releasing consumer A from first condition and entering lock again by this consumer, then we again have problem with **deadlock**.
![Deadlock - hasWaiters()](https://user-images.githubusercontent.com/58474974/142747853-53e8624c-5322-4e7e-915b-42726da6d01c.png)
- \[[lab3.3](https://github.com/MexAsimov/concurrent-computing/tree/main/lab3-booleans)] Consumer-Producer problem with one **lock**, four **conditions** and two **boolean variables** - removed problem with **deadlock** from [lab3.2](https://github.com/MexAsimov/concurrent-computing/tree/main/lab3-hasWaiters).
- \[[lab4-5](https://github.com/MexAsimov/concurrent-computing/tree/main/lab4)] Consumer-Producer problem - performance comparison. [Boolean variables](https://github.com/MexAsimov/concurrent-computing/tree/main/lab4/lab4-4cond%2B2bool) vs [3 locks](https://github.com/MexAsimov/concurrent-computing/tree/main/lab4/lab4-three-lock).
- \[[lab6-7](https://github.com/MexAsimov/concurrent-computing/tree/main/lab5)] Consumer-Producer problem based on **Active Object** design pattern - decoupled method invocation from method execution. That provides us possibility to store requests on queue and executing these requests asynchronously.
- **Additional problem**: Is it worth to synchronize Future? **Answer**: It depends - if additional task is busying for a longer time, it is profitable.
- \[[lab8-11](https://github.com/MexAsimov/concurrent-computing/tree/main/lab7)] Consumer-Producer problem approach using JCSP library (included in files). Using channels to communicate between processes.
- \[lab13-14] Petri nets - theory and models on PIPE
