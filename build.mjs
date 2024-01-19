import { execSync } from 'child_process';


(async () => {
  const target = process.argv[2];
  if (!target) {
    console.error("No target specified.");
    return;
  }
  console.log("BUILD:");
  try {
    execSync(`cd ./${target}; rm *.class Yylex.java sym.java parser.java`, );
  } catch (err) {};
  try {
    execSync(`cd ./${target}; jflex PLXC.flex`);
    execSync(`cd ./${target}; cup PLXC.cup`);
    execSync(`cd ./${target}; javac *.java`);
  } catch (err) {
    console.error(err.stderr.toString());
    console.log(err.stdout.toString());
  }
})();