#!/bin/bash
mysql -uroot -p$MYSQL_ROOT_PASSWORD <<EOF

source /mysql/bilheteira.sql
