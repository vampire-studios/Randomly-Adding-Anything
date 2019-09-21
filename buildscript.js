const fs = require("fs")
const consts = {
    travis_pull_request: process.env.TRAVIS_PULL_REQUEST,
    travis_branch: process.env.TRAVIS_BRANCH
}

fs.writeFile("./buildscript.bash", texttowrite(), (err) => {
    if (err) {
        console.log(err)
    }
})
function texttowrite() {
    if (consts.travis_pull_request === "true") {
        return "./gradlew build"
    } else if (consts.travis_branch === "release/1.15") {
        return "./gradlew build githubRelease curseforge"
    } else if (consts.travis_branch === "dev/1.15") {
        return "./gradlew build githubRelease"
    } else {
        return "./gradlew build"
    }
}