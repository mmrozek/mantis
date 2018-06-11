{ stdenv, pkgs, mantisSrc, sbtVerify, protobuf3_5 }:

stdenv.mkDerivation {
  name = "mantis";
  src = mantisSrc;

  buildInputs = with pkgs; [ scala sbt sbtVerify unzip protobuf3_5 openjdk8 ];

  outputs = [ "out" "zip" ];

  configurePhase = ''
    export HOME="$NIX_BUILD_TOP"
    cp -r ${sbtVerify}/.ivy .
    cp -r ${sbtVerify}/.sbt .
    cp -r ${sbtVerify}/target .
    chmod -R u+w .ivy .sbt target

    # Get sbt to pre-fetch its dependencies. The cleanest way I've
    # found of doing this is to get it to list the available projects.
    sbt -Dsbt.global.base=.sbt/1.0 -Dsbt.ivy.home=.ivy projects

    # We have to patch the executable embedded inside protoc-jar. :-(
    mkdir -p bin/3.5.1
    cp ${protobuf3_5}/bin/protoc  bin/3.5.1/protoc-3.5.1-linux-x86_64.exe
    jar uf .ivy/cache/com.github.os72/protoc-jar/jars/protoc-jar-3.5.1.jar  bin/3.5.1/protoc-3.5.1-linux-x86_64.exe
  '';

  buildPhase = ''
    sbt -Dsbt.global.base=.sbt/1.0 -Dsbt.ivy.home=.ivy 'set test in Test := {}' dist
  '';

  installPhase = ''
    cp target/universal/mantis-1.0-daedalus-rc1.zip $zip

    mkdir $out
    unzip $zip
    mv mantis-1.0-daedalus-rc1/* $out
  '';
}
