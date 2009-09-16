#! /usr/bin/env bash
echo $@
java -cp bin/main/java/:lib/log4j-1.2.15.jar -Djava.rmi.server.codebase=file:bin/main/java/ -Dmsg=$@ se.umu.cs.jsgajn.gcom.Server
