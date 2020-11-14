 docker build -t test_db_container_3 .
 docker run -p 8454:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres test_db_container_3