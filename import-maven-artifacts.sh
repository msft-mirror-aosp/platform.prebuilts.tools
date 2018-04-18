set -e


destRepo="$(cd $(dirname $0)/../.. && pwd)"
tempDir="/tmp/import-temp-work"
rm -rf $tempDir
mkdir -p $tempDir
cd $tempDir

function usage() {
  echo "Usage: $0 group:artifact:version[:classifier] [group:artifact:version[:classifier]...]

This script downloads the specified artifacts copies them into the appropriate subdirectory of $destRepo/prebuilts/"
  exit 1
}




inputRepo=m2repository
stageRepo=m2staged
destAndroidRepo=$destRepo/prebuilts/gradle-plugin
destThirdPartyRepo=$destRepo/prebuilts/tools/common/m2/repository

function createPom() {
  pomPath="$PWD/pom.xml"
  echo creating $pomPath
  pomPrefix='<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.google.android.build</groupId>
  <artifactId>m2repository</artifactId>
  <version>1.0</version>
  <repositories>
    <repository>
      <id>google</id>
      <name>Google</name>
      <url>https://maven.google.com</url>
    </repository>
    <repository>
      <id>jcenter</id>
      <name>JCenter</name>
      <url>https://jcenter.bintray.com</url>
    </repository>
  </repositories>
  <dependencies>
'

  pomSuffix='
  </dependencies>
  <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>2.8</version>
                <executions>
                    <execution>
                        <id>default-cli</id>
                        <configuration>
                            <includeScope>runtime</includeScope>
                            <addParentPoms>true</addParentPoms>
                            <copyPom>true</copyPom>
                            <useRepositoryLayout>true</useRepositoryLayout>
                            <outputDirectory>m2repository</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
'

  pomDependencies=""

  while [ "$1" != "" ]; do
    echo importing $1
    # determine whether a classifier is present
    if echo "$1" | grep ":.*:.*:" > /dev/null; then
      # classifier is present
      dependencyText="$(echo $1 | sed 's|\([^:]*\):\([^:]*\):\([^:]*\):\([^:]*\)|\n    <dependency>\n      <groupId>\1</groupId>\n      <artifactId>\2</artifactId>\n      <version>\3</version>\n    <classifier>\4</classifier>\n    </dependency>|')"
    else
      # classifier is not present
      dependencyText="$(echo $1 | sed 's|\([^:]*\):\([^:]*\):\([^:]*\)|\n    <dependency>\n      <groupId>\1</groupId>\n      <artifactId>\2</artifactId>\n      <version>\3</version>\n    </dependency>|')"
    fi
    pomDependencies="${pomDependencies}${dependencyText}"
    shift
  done

  if [ "${pomDependencies}" == "" ]; then
    usage
  fi

  echo "${pomPrefix}${pomDependencies}${pomSuffix}" > $pomPath
  echo done creating $pomPath
}


function downloadDependencies() {
  echo downloading and/or copying dependencies to $inputRepo
  rm -rf $inputRepo
  mvn dependency:copy-dependencies
  #mvn dependency:copy-dependencies -Dclassifier=javadoc
  mvn dependency:copy-dependencies -Dclassifier=sources
  echo done placing dependencies in $inputRepo
}

# generates an appropriately formatted repository for merging into existing repositories,
# by computing artifact metadata
function stageRepo() {
  echo staging to $stageRepo
  rm -rf $stageRepo
  
  for f in $(find $inputRepo -type f | grep -v '\.sha1$' | grep -v '\.md5'); do
      md5=$(md5sum $f | sed 's/ .*//')
      sha1=$(sha1sum $f | sed 's/ .*//')
      relPath=$(echo $f | sed "s|$inputRepo/||")
      relDir=$(dirname $relPath)
  
      fileName=$(basename $relPath)
      writeChecksums="true"
  
      destDir="$stageRepo/$relDir"
      destFile="$stageRepo/$relPath"
      if [ "$fileName" == "maven-metadata-local.xml" ]; then
        writeChecksums="false"
        destFile="$destDir/maven-metadata.xml"
      fi
  
      mkdir -p $destDir
      if [ "$writeChecksums" == "true" ]; then
        echo -n $md5 > "${destFile}.md5"
        echo -n $sha1 > "${destFile}.sha1"
      fi
      cp $f $destFile
  done
  
  echo done staging to $stageRepo
}

function announceCopy() {
  input=$1
  output=$2
  if stat $input > /dev/null 2>/dev/null; then
    echo copying "$input" to "$output"
    cp -rT $input $output
  fi
}

function export() {
  echo exporting
  announceCopy $stageRepo/com/android $destAndroidRepo/com/android
  rm -rf $stageRepo/com/android

  announceCopy $stageRepo/androidx $destAndroidRepo/androidx
  rm -rf $stageRepo/androidx

  announceCopy $stageRepo $destThirdPartyRepo
  echo done exporting
}


function main() {
  createPom "$@"
  downloadDependencies
  stageRepo
  export
}

main "$@"
