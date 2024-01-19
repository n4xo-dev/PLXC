import { readdir } from "fs";
import { exec } from "child_process";

readdir("./tests", tests);

function tests(err, files) {
  console.log(files);

  // Lexical test
  exec
}