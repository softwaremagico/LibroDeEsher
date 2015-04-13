#!/bin/sh

rm -r tests >/dev/null;
rm errors.log >/dev/null;
for i in `seq 1 100`
do
	folder="tests/test-$i";
	mkdir -p $folder >/dev/null;
	#mvn test > $folder/ouput.txt;
	mvn test | awk '/^\[ERROR\]/ { print  > "error.log"; next; }; 1' > $folder/ouput.txt;
	mv /tmp/characterAsJson.txt $folder;
	mv /tmp/importedCharacterSheet.txt $folder;
	mv /tmp/importedLevelSheet.txt $folder;
	mv /tmp/originalCharacterSheet.txt $folder;
	mv /tmp/originalSheet.txt $folder;
	mv /tmp/testLevelJson.txt $folder;
	mv /tmp/testStandard.txt $folder;	
done;

