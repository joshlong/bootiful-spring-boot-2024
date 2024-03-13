#!/usr/bin/env bash 

for  table  in product line_item cart ; do
  echo "drop table $table cascade  " |  PGPASSWORD=secret psql -U myuser -h localhost mydatabase
done

#PGPASSWORD=secret psql -U myuser -h localhost mydatabase

