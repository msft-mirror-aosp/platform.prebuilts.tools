A simple JAR rewriter written by Gemini: https://g.co/gemini/share/d6eda34891c1
and extended by Gemini: https://g.co/gemini/share/155b0c938ff6
(along with some manual adjustments and additions)
to rewrite types, their instantiation, member access, etc.
(to workaround https://youtrack.jetbrains.com/issue/KT-79870)

Usage
---
For Kotlinc 2.2.20:
```
$ ../dependency-source-checkouts/kotlin/gradlew rewriteType \
  -PinputJarPath=../kotlin-compiler/kotlin-compiler.jar \
  -PoutputJarPath=output/kotlin-compiler.jar
```
