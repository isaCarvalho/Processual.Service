# Processual Service

# Rodar testes

Para rodar os testes, deve-se primeiro rodar o banco de teste com o seguinte comando:

```
docker run --name processual_service_test_db -p 5554:5432 -e POSTGRES_USER=admin_user -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=test_db -d postgres:11
```

Tambem deve configurar as variaveis de ambiente para teste. Para remover uma imagem:

```
docker rm processual_service_test_db --force
```