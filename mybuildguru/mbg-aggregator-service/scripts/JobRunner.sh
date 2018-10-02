#!/bin/bash
#
# Script Name: JobRunner.sh
#
# Author: Naveen Kumar H N
# Date : 30/01/2016
#
# Description: The following script runs the aggregator service with a specific job 
#
# Run Information: This script can be either setup as cron job or run with some scheduler like quartz, 
#  bash JobRunner.sh -h  - to get help
# 
#


# Usage info
show_help() {
cat << EOF
Usage: ${0##*/} [-h] [-j JOBNAME] [ANY_JOBARGS]...
    -h          display this help and exit
    -j JOBNAME  Give the JOBNAME to run.
EOF
}





clear
# get the argumentsi
MBG_AGGREGATOR_JAR=/home/mowgli/mbg/mbg-aggregator-service.jar 
if [ -z ${JAVA_HOME} ]
 then 
     echo "JAVA_HOME is not set, can you please check your profile"
 fi

while getopts "hj:" opt; do
  case $opt in
    h)
      show_help
      exit 0
      ;;
    j)
        echo "----------MBG Aggregator Service - Starting --------------"
	java -jar $MBG_AGGREGATOR_JAR $OPTARG &      # You send it in background
        MyPID=$!                        # You sign it's PID
        echo $MyPID   
      exit 0
      ;;
    \?)
      echo "Invalid option:" >&2
      ;;
  esac
done



