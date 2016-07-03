#!/bin/bash

# $1 = system name, $2 = data size, $3 = portion of clients, $4 = number of clients, $5 = driver name

cd ~/ds-project

# Run clients concurrently
echo "INFO: Start running for $5 with number of clients $3..."
i=0
while [ $i -lt $3 ]
do
	echo $i 

	./bin/benchmark-manual-drivers-start.sh $1-config/$5.benchmark.properties &

	sleep 1s

	i=$((i+1))
done

#~/Desktop/monitor.sh &

echo "INFO: Running. Start sleeping..."

# Sleep 
#sleep 1m # depends on the time of warm up and duration of run
#sleep 2m # when running 16 it needs a good time so put the maximum

case $4 in
	1) echo "INFO: Sleeping 4m before moving the files"
		sleep 4m
		;;
	2) echo "INFO: Sleeping 4m before moving the files"
		sleep 4m
		;;
	4) echo "INFO: Sleeping 5m before moving the files"
		sleep 5m
		;;
	8) echo "INFO: Sleeping 6m before moving the files"
		sleep 6m
		;;
	16) echo "INFO: Sleeping 8m before moving the files"
		sleep 6m
		;;
	32) echo "INFO: Sleeping 10m before moving the files"
		sleep 6m			
		;;
	64) echo "INFO: Sleeping 12m before moving the files"
		sleep 6m
		;;
	128) echo "INFO: Sleeping 14m before moving the files"
		sleep 7m			
		;;
	*) echo "sleep 2m"
		sleep 2m
		;;
esac	


echo "INFO: Moving the files"

# Move the files 
mkdir ~/Desktop/$1/$1-$4-$2-$5

./move-results.sh $1 $1-$4-$2-$5

sleep 5s

echo "INFO: Finished running for $1-$4-$2-$5"
