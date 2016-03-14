.SUFFIXES: .java .class
.java.class:
	javac $*.java
CLASSES = \
	dbengineTCP.java \
	dbclientTCP.java \
	dbengineUDP.java \
	dbclientUDP.java
default: classes
classes: $(CLASSES:.java=.class)
clean:
	$(RM) *.class