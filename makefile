NAME = "Main"

all:
	javac -sourcepath src src/*.java -d ./out --module-path $(JAVA_FX_HOME)/lib --add-modules javafx.controls,javafx.media
	cp src/Resources/*.mp3 out/Resources

run: all
	@echo "Running..."
		java -cp out --module-path $(JAVA_FX_HOME)/lib --add-modules javafx.controls,javafx.media $(NAME)

clean:
	-@rm -rf out/*.class
	-@rm out/Resources/*.mp3
