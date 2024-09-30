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
- ``activeProfile`` 을 ``ollama``로 설정할 경우, ollama 호출이 발생하며, dog에 대한 질의를 합니다.
  - 아래와 같은 작업이 선행되어야 합니다.
    - ``build.gradle`` 에 ``spring-ai-openai-spring-boot-starter`` 를 주석으로 처리하고, ``spring-ai-ollama-spring-boot-starter``를 주입합니다.
    - ollama 에 ``llama3, minlm`` 이 설치되어 있어야 합니다. 
    - 로컬에서 ``ollama run llama3`` 를 통해 llama3 서버가 켜져있어야 합니다.
    - ``vector_store.sql`` 에서 ``ollama`` 하위 쿼리를 실행합니다.