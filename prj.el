(set-variable 'jde-global-classpath
              (list "./bin/main/java"
                    "./bin/test/java"
                    "./lib/junit-4.5.jar"
                    "./lib/log4j-1.2.15.jar"))

(set-variable 'jde-sourcepath (mapcar 'jde-normalize-path
                                      (list "./src/main/java"
                                            "./src/test/java")))

(message "Loaded prj.el")





