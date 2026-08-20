#!/usr/bin/env groovy
package com.example

class Maven implements Serializable {

    def script

    Maven(script) {
        this.script = script
    }

    def incrementVersion() {
        script.echo 'incrementing version...'
        script.sh 'mvn build-helper:parse-version versions:set \
-DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
 versions:commit'
        def matcher = script.readFile('pom.xml') =~ /<version>(.+)<\/version>/
        def version = matcher[0][1]
        def newVersion = "${version}"
//        def newVersion = "${version}-${BUILDNAME}"
        script.echo "new version: ${newVersion}"
        return newVersion
    }
}
