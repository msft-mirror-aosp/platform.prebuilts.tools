#!/bin/bash

# usage: ./fix_aar.sh <your.aar>
# Small helper script that fixes aar files.

# AAR files produced by AGP contain classes.jar that causes bazel to fail,
# because ijar can't parse that jar.

unzip -d . $1 classes.jar
zip -F classes.jar --out classes.jar1
mv classes.jar1 classes.jar
zip $1 classes.jar
rm classes.jar
