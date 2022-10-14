JAR_PATH='./out.jar'
SRC_PATH='./src'
BIN_PATH='./dist'
C_FLAGS='-Xlint:unchecked' # compiler flags (options)
MAIN_CLASS='com.luxlunaris.cincia.backend.shell.Repl'
SH_WRAPPER='cincia.sh'
SRC_FILES_LIST='source-files.txt'

rm -r "$BIN_PATH" # remove (possible) old distrib folder from before
find "$SRC_PATH" | grep '\.java' > "$SRC_FILES_LIST" # save into a file paths of all source files 
javac $C_FLAGS -d $BIN_PATH $(cat $SRC_FILES_LIST)  # compile source files into bin dir
rm "$SRC_FILES_LIST" # remove this tmp buffer
cd $BIN_PATH # go into bin directory
jar cvfe $JAR_PATH $MAIN_CLASS com/ # package object files into a jar
echo "rlwrap java -jar $JAR_PATH" > "$SH_WRAPPER" # wrap jar in sh script, with rlwrap for commands history 
chmod +x "$SH_WRAPPER" # make sh script executable
rm -r 'com' # remove object files