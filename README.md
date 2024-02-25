./gradlew build

docker build -t myapp .
docker build -t myapp --progress=plan --no-cache .

docker run -p 8080:8080 myapp
