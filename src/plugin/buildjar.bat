javac -d ./bin ./StateLoader/*.java
jar cvf jar/JsonLoader.jar bin/JsonLoader.class
jar cvf jar/XMLLoader.jar bin/XMLLoader.class