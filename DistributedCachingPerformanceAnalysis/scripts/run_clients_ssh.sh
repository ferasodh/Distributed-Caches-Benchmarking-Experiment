#!/bin/bash

# $1 = system name, $2 = data size

USERNAME1="test1" # "test1"
USERNAME2="test2" # "test2"
USERNAME3="test3" # "test3"
USERNAME4="test4" # "test4"

PASSWORD1="test@123" # "test1@3"
PASSWORD2="test@123" # "test1@3"
PASSWORD3="test@123" # "test1@3"
PASSWORD4="test@123" # "test1@3"

HOST1="192.168.1.201"
HOST2="192.168.1.202"
HOST3="192.168.1.203"
HOST4="192.168.1.204"

# run 1 2 4 8 16 32 64 128

for i in 1 2 4 8 16 32 64 128
do 
	for driver in "custom.complex3" #"get" "custom" "custom.complex1" "custom.complex2" "custom.complex3"
	do
	
		if [ $i == 1 ] || [ $i == 2 ]; then
			echo "INFO_SSH: Running $i clients on HOST1"  
			./run_clients_ind.sh $1 $2 $i $i $driver

			echo "INFO_SSH: SSH sleep 1 sec after finisihing everything"
			sleep 2s 
		else 
			result=$((i/4))

			echo "INFO_SSH: Distributing $i on 4 machines, each with $result"

			echo "INFO_SSH: Running on HOST2"  
			sshpass -p $PASSWORD2 ssh -o StrictHostKeyChecking=no -l $USERNAME2 $HOST2 ./run_clients_ind.sh $1 $2 $result $i $driver &
			
			echo "INFO_SSH: Running on HOST3"  
			sshpass -p $PASSWORD3 ssh -o StrictHostKeyChecking=no -l $USERNAME3 $HOST3 ./run_clients_ind.sh $1 $2 $result $i $driver &

			echo "INFO_SSH: Running on HOST4"    
			sshpass -p $PASSWORD4 ssh -o StrictHostKeyChecking=no -l $USERNAME4 $HOST4 ./run_clients_ind.sh $1 $2 $result $i $driver &

			echo "INFO_SSH: Running on HOST1" 
			./run_clients_ind.sh $1 $2 $result $i $driver

			echo "INFO_SSH: SSH sleeping after finisihing everything"
			case $i in
				1) echo "SSH  will sleep for 1 client"
					# sleep 4m
					sleep 2s
					;;
				2) echo "SSH  will sleep for 2 clients"
					# sleep 4m
					sleep 2s
					;;
				4) echo "SSH  will sleep for 4 clients"
					sleep 1m
					;;
				8) echo "SSH  will sleep for 8 clients"
					sleep 2m
					;;
				16) echo "SSH  will sleep for 16 clients"
					sleep 2m
					;;
				32) echo "SSH  will sleep for 32 clients"
					sleep 3m			
					;;
				64) echo "SSH  will sleep for 64 clients"
					sleep 3m
					;;
				128) echo "SSH  will sleep for 128 clients"
					sleep 4m			
					;;
				*) echo "sleep 2m"
					sleep 2m
					;;
			esac
		fi

		
	
#		case $i in
#			1) echo "SSH  will not sleep for 1 client"
#				# sleep 4m
#				sleep 2s
#				;;
#			2) echo "SSH  will not sleep for 2 clients"
#				# sleep 4m
#				sleep 2s
#				;;
#			4) echo "sleep 5m"
#				sleep 5m
#				;;
#			8) echo "sleep 6m"
#				sleep 6m
#				;;
#			16) echo "sleep 6m"
#				sleep 6m
#				;;
#			32) echo "sleep 7m"
#				sleep 7m			
#				;;
#			64) echo "sleep 7m"
#				sleep 7m
#				;;
#			128) echo "sleep 8m"
#				sleep 8m			
#				;;
#			*) echo "sleep 2m"
#				sleep 2m
#				;;
#		esac
	done
done 
