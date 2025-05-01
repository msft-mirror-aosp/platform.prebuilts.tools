A simple JAR rewriter written by Gemini: https://g.co/gemini/share/d6eda34891c1

to remove a certain method in the given binary jar
(to workaround https://youtrack.jetbrains.com/issue/IJPL-183045)

Usage
---
```
$ ../dependency-source-checkouts/kotlin/gradlew rewriteJar \
  -PinputJarPath=../intellij-core/intellij-core.jar \
  -PoutputJarPath=output/intellij-core.jar \
  -PtargetClass=com/intellij/mock/MockApplication \
  -PtargetMethodName=logInsufficientIsolation \
  -PtargetMethodDesc="(Ljava/lang/String;[Ljava/lang/Object;)V"
```
