 docker build -t test_db_container_2 .
 docker run -p 8453:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres test_db_container_2