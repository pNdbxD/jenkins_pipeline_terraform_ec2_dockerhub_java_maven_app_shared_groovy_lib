#!/user/bin/env groovy
package com.example

class Git implements Serializable {

    def script

    Git(script) {
        this.script = script
    }

    def commitVersion(String version) {
        script.sshagent(credentials: ['gitlab-jenkins-ssh-key']) {
            script.sh """
    echo "Committing version ${version} to Git repository..."
    pwd
    ls -la
    git fetch origin
    git checkout -B "${script.env.BRANCH_NAME}" "origin/${script.env.BRANCH_NAME}"
    git remote get-url origin
    git remote -v
    git status

    git config user.name "Jenkins"
    git config user.email "jenkins@example.com"

    git add pom.xml
    git commit -m "Bump version to ${version}"
    git push origin HEAD:"${script.env.BRANCH_NAME}"
    ls
    """
        }
    }
}