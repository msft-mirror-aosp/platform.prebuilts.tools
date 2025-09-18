A simple JAR rewriter written by Gemini: https://g.co/gemini/share/d6eda34891c1

to remove a certain method in the given binary jar
(to workaround https://youtrack.jetbrains.com/issue/IJPL-183045)

or remove a certain constructor body: https://g.co/gemini/share/01ddfcdfbe92
(to workaround https://youtrack.jetbrains.com/issue/IJPL-200136)

---

and extended by Gemini: https://g.co/gemini/share/155b0c938ff6
(along with some manual adjustments and additions)

to rewrite types, their instantiation, member access, etc.
(to workaround https://youtrack.jetbrains.com/issue/KT-79870)

Usage
---
For 2025.1:
```
$ ../dependency-source-checkouts/kotlin/gradlew wipeMethod \
  -PinputJarPath=../intellij-core/intellij-core.jar \
  -PoutputJarPath=output/intellij-core.jar \
  -PtargetClass=com/intellij/mock/MockApplication \
  -PtargetMethodName=logInsufficientIsolation \
  -PtargetMethodDesc="(Ljava/lang/String;[Ljava/lang/Object;)V"
```
For 2025.2:
```
$ ../dependency-source-checkouts/kotlin/gradlew wipeConstructor \
  -PinputJarPath=../intellij-core/intellij-core.jar \
  -PoutputJarPath=output/intellij-core.jar
```
---
For Kotlinc 2.2.20:
```
$ ../dependency-source-checkouts/kotlin/gradlew rewriteType \
  -PinputJarPath=../kotlin-compiler/kotlin-compiler.jar \
  -PoutputJarPath=output/kotlin-compiler.jar
```
