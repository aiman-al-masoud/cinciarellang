JAR_PATH='./out.jar'
SRC_PATH='./src'
BIN_PATH='./bin2'
C_FLAGS='-Xlint:unchecked'
MAIN_CLASS='com.luxlunaris.cincia.backend.shell.Repl'

find "$SRC_PATH" | grep '\.java' > source-files.txt
javac $C_FLAGS -d $BIN_PATH $(cat source-files.txt)
cd $BIN_PATH
jar cvfe $JAR_PATH $MAIN_CLASS com/
# java -jar out.jar # to run jar
