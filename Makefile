build:
	./gradlew build

clean:
	./gradlew clean

run:
	./gradlew jar    
	@java -jar build/libs/y_lab-1.0-SNAPSHOT.jar
