 docker build -t test_db_container_1 .
 docker run -p 8452:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres test_db_container_1