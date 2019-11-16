const fs = require("fs")
const path = require("path")
const langfolderpath = path.join("..", "src","main","resources","assets","raa","lang")
const langfiles = fs.readdirSync(langfolderpath)
var en_us = {}
en_us = JSON.parse(fs.readFileSync(path.join(langfolderpath, "en_us.json")));
for (var i = 0; i < langfiles.length; i++) {
    if (langfiles[i] == "en_us.json") {
        continue;
    }
    var langfilepath = path.join(langfolderpath, langfiles[i])
    var langfile = JSON.parse(fs.readFileSync(langfilepath))
    var langFileKeys = Object.entries(langfile);
    var en_us_keys = Object.entries(en_us)
    var newObject = {}
    for(var y = 0; y < en_us_keys.length; y++) {
        var doesContain = false;
        for(var j = 0; j < langFileKeys.length; j++) {
            if (en_us_keys[y][0] == langFileKeys[j][0]) {
                doesContain = true;
                newObject[en_us_keys[y][0]] = langFileKeys[j][1];
                break;
            }
        }

        if (!doesContain) {
            newObject[en_us_keys[y][0]] = en_us_keys[y][1];
        }
    }
    fs.writeFileSync(langfilepath, JSON.stringify(newObject))
}