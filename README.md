use docker-compose form now on.

docker-compose up -d
docker-compose down

Ports: (Free these up in docker environment or change them in docker-compose)
- databases: 3306-3309
- api-gateway: 9000
- Eureka server: 8761
- Microservices: 9001-9004

So far, docker compose will pull from my docker hub ttran8800:

things to do:

angular front end (currently working on it)
- hash tag
- 
- likes (optional)

- fix css style for all components / pages (just making it prettier, not really fix)

back end
- zipkin
- config-service (setup for privacy repo using either ssh keys or user/password authentication)
- kafka-service (next to last)
- then finally kube it up (last)
