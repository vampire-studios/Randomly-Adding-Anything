const fs = require("fs")
const path = require("path")
const langfolderpath = path.join(".", "src","main","resources","assets","raa","lang")
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
        console.log(en_us_keys[y])
    }
}