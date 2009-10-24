#! /usr/bin/env bash
# (mapconcat '(lambda (path)
#               (car (cdr (split-string path default-directory))))
#             (my-get-classpath) ":")
cp=bin/main/java:bin/test/java:lib/core.jar:lib/junit-4.5.jar:lib/logback-classic-0.9.17.jar:lib/logback-core-0.9.17.jar:lib/slf4j-api-1.5.8.jar
java -cp $cp -Djava.rmi.server.codebase=file:bin/main/java/ se.umu.cs.jsgajn.gcom.management.GNSImpl $@