# seckill
A backend service that support short-period high concurrent request in Java using Spring Boot, Mybatis and MySQL.


- Built a backend service that supports high concurrent request in **Java** using **Spring Boot**, **Mybatis** and **MySQL**.
- Applied **Redis** as cache layer to improve QPS performance for this "read more write less" use case.
- Adopted **RabbitMQ** to enable asynchronous transaction workflow and reduce the peak QPS for load balancing.
- Utilized **Spring AOP** to intercept request and limit access times of each user with the fixed-window strategy.
