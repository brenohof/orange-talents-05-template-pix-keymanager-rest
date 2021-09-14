API Rest para consumir o módulo Grpc da aplicação pix-keymanager, baseada no sistema de chave pix.

```(shell)
mvn clean build
```
or 
```(shell)
sudo docker build image -t pix-keymanager-rest . 
sudo docker run -dp 8080:8080 pix-keymanager-rest
```
