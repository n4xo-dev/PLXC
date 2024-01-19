import { execSync } from 'child_process';


(async () => {
  const target = process.argv[2];
  if (!target) {
    console.error("No target specified.");
    return;
  }
  console.log("BUILD:");
  try {
    execSync("rm ./src/*.class ./src/Yylex.java ./src/sym.java ./src/parser.java", );
  } catch (err) {};
  try {
    execSync(`jflex ./src/${target}.flex`);
    execSync(`cup ./src/${target}.cup`);
    execSync("javac ./src/*.java");
  } catch (err) {
    console.error(err.stderr.toString());
    console.log(err.stdout.toString());
  }
})();