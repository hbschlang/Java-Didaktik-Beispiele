
install_cli:
	mvn clean compile assembly:single
	sudo cp -f target/didaktik-0.1.0-jar-with-dependencies.jar /usr/local/share/java/jars/didaktik.jar
	sudo cp -f cli.sh /usr/local/bin/didaktik.java

.phony: install_cli
