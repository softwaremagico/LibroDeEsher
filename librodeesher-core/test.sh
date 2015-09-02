#!/bin/sh

GLOBAL_STARTTIME=$(date +%s)
rm -r tests 2> /dev/null;
rm errors.log 2> /dev/null;
for i in `seq 1 100`
do
	STARTTIME=$(date +%s)
	echo "---- TEST $i ----"
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
	mv /tmp/importedMorticia.txt $folder;
	ENDTIME=$(date +%s)
	echo "Duration $(($ENDTIME - $STARTTIME)) seconds."
done;
GLOBAL_ENDTIME=$(date +%s)
echo "Tdtal Duration $(($GLOBAL_ENDTIME - $GLOBAL_STARTTIME)) seconds."

#Search for errors. -L for not contains "Failures: 0".
grep --include=\*.out -rnwL 'tests' -e "Failures: 0" > error.log

