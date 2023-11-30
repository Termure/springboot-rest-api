### postgres config and db creation
#### cd /etc/postgresql/12/main
#### sudo subl pg_hba.conf
#### change => local all postgres peer to md5
#### psql -p 5433 -U postgres
#### create database springboot_rest_api;