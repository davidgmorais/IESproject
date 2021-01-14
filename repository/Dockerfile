FROM mysql:8
RUN mkdir -p /mysql
COPY bilheteira.sql /mysql
COPY setup.sh /docker-entrypoint-initdb.d

RUN chmod a+x /docker-entrypoint-initdb.d/setup.sh
