#!/bin/sh

rm -r tests 2> /dev/null;
rm errors.log 2> /dev/null;
for i in `seq 1 100`
do
	folder="tests/test-$i";
	mkdir -p $folder >/dev/null;
	mvn test > $folder/ouput.out;
	mv /tmp/character_l1.json $folder;
	mv /tmp/importedCharacterSheet.txt $folder;
	mv /tmp/importedLevelSheet.txt $folder;
	mv /tmp/originalCharacterSheet.txt $folder;
	mv /tmp/originalSheet.txt $folder;
	mv /tmp/testLevelJson.json $folder;
	mv /tmp/testStandard.txt $folder;	
done;

#Search for errors.
grep --include=\*.out -rnw 'tests' -e "Tests run: 15, Failures: 1" > error.log

