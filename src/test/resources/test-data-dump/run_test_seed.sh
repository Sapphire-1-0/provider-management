#!/bin/bash

USERNAME=neo4j
PASSWORD=password
HOST=localhost
PORT=7687
DBNAME=test   # <-- set your database name here
echo "PWD is: $(pwd)"
while IFS= read -r file
do
    echo "Running $file on database $DBNAME ..."
    echo "Read file: '$file'"
    cypher-shell -u $USERNAME -p $PASSWORD -a bolt://$HOST:$PORT -d $DBNAME -f "$file"
done < master_cypher_list.txt