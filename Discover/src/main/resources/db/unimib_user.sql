CREATE USER 'unimib' IDENTIFIED BY 'unimib';
GRANT USAGE ON *.* TO 'unimib'@localhost IDENTIFIED BY 'unimib';
GRANT ALL PRIVILEGES ON *.* TO 'unimib'@'localhost' WITH GRANT OPTION;
GRANT ALL privileges ON discover.* TO 'unimib'@localhost;
FLUSH PRIVILEGES;
