 docker build -t db_connector-db .
 docker run -p 8451:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres db_connector-db