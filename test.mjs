import { readdir, writeFileSync, rmSync, rm } from "fs";
import { execSync } from "child_process";

readdir("./tests", tests);

function tests(err, files) {
  const target = process.argv[2] || "src";
  const verbose = process.argv[3] === "-v";
  let successfull = 0;
  for(const file of files) {
    const myTdc = myPlxc(file, target).toString();
    const tdc = plxc(file).toString();
    if (myTdc === tdc) {
      console.log(`✅ ${file} OK`);
      successfull++;
      continue;
    }
    writeFileSync(`./temp/my${file}.tdc`, myTdc, { flag: 'w', flush: true });
    writeFileSync(`./temp/${file}.tdc`, tdc, { flag: 'w', flush: true });
    const myResult = ctd(`./temp/my${file}.tdc`).toString();
    const result = ctd(`./temp/${file}.tdc`).toString();
    if (myResult === result) {
      console.log(`✅ ${file} OK`);
      successfull++;
      continue;
    }
    console.error(`❌ ${file} FAIL`);
    verbose && console.error(`\tExpected: ${result.trim()}`);
    verbose && console.error(`\tReceived: ${myResult.trim()}`);
    verbose && console.error('-'.repeat(25));
    rmSync(`./temp/my${file}.tdc`);
    rmSync(`./temp/${file}.tdc`);
  }

  console.log(`\n${successfull}/${files.length} tests passed.`);
}

function myPlxc(file, target) {
  try {
    const tdc = execSync(`cd ./${target} && java PLXC ../tests/${file}`);
    if (tdc.toString().toLowerCase().includes("error")) {
      return "";
    }
    return tdc.toString();
  } catch (err) {
    return "";
  }
}

function plxc(file) {
  try {
    return execSync(`plxc tests/${file}`);
  } catch (err) {
    return "";
  }
}

function ctd(tdcFile) {
  try {
    return execSync(`ctd ${tdcFile}`);
  } catch (err) {
    return "";
  }
}