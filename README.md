# 실행 전 준비
- Docker-compose 를 통해 postgresql-compose.yaml 실행
```shell
cd josh-long-meetup
docker-compose -f ./postgresql-compose.yaml up -d
```

- dogs.sql pg 덤프 파일로 DB restore 진행
```shell
cat dogs.sql| docker exec -i {containerId} psql -d mydatabase -U myuser
```

- shell 설정에 openAI API key 설정
```shell
-- ~/.zshrc
export SPRING_AI_OPENAI_API_KEY=${your_openai_api_key}
```


# 테스트 해보기
- Post /dogs/45/adoptions 호출을 통해 dog owner 를 설정할 수 있습니다.
```shell
curl -d '{"name":"owner"}' -XPOST -H"content-type: application/json" http://localhost:8080/dogs/45/adoptions
```

- application 의 ``spring.ai.vectorstore.pgvector.initialize-schema=false``를 ``true`` 로 바꾸고 실행하면 vector store를 초기화합니다.

- ``activeProfile`` 을 ``ai``로 설정할 경우, openai 호출이 발생하며, dog 에 대한 질의를 합니다.