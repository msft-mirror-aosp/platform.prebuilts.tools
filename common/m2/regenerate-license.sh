#!/bin/bash

rm LICENSE
for file in $(find repository -name 'LICENSE*' -or -name 'NOTICE*' | sort); do
  echo ======================================================= >> LICENSE
  echo $file >> LICENSE
  echo >> LICENSE
  cat $file >> LICENSE
  echo >> LICENSE
done
