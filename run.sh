mvn clean package -DskipTests
docker build -t lab3 .
docker run --rm --network host lab3