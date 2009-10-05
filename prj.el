;; (my-get-classpath)

(set-variable 'jde-global-classpath
              '("./bin/main/java"
                "./bin/test/java"
                "./lib/core.jar"
                "./lib/junit-4.5.jar"
                "./lib/log4j-1.2.15.jar"
                "./lib/logback-classic-0.9.17.jar"
                "./lib/slf4j-api-1.5.8.jar"))

(set-variable 'jde-sourcepath (mapcar 'jde-normalize-path
                                      (list "./src/main/java"
                                            "./src/test/java")))

(message "Loaded prj.el")





