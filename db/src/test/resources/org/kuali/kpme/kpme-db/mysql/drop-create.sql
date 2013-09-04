--
-- Copyright 2004-2013 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- Drop/create the users and databases KPME needs for unit testing
DROP DATABASE IF EXISTS tk;
GRANT USAGE ON *.* TO 'tk'@'%' IDENTIFIED BY 'tk_tk_tk';
GRANT USAGE ON *.* TO 'tk'@'localhost' IDENTIFIED BY 'tk_tk_tk';
DROP USER 'tk'@'%';
DROP USER 'tk'@'localhost';
CREATE DATABASE IF NOT EXISTS tk DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_bin';
CREATE USER 'tk'@'%' IDENTIFIED BY 'tk_tk_tk';
CREATE USER 'tk'@'localhost' IDENTIFIED BY 'tk_tk_tk';
GRANT ALL ON tk.* TO 'tk'@'%' WITH GRANT OPTION;
GRANT ALL ON tk.* TO 'tk'@'localhost' WITH GRANT OPTION;
DROP DATABASE IF EXISTS krtt;
GRANT USAGE ON *.* TO 'krtt'@'%' IDENTIFIED BY 'krtt';
GRANT USAGE ON *.* TO 'krtt'@'localhost' IDENTIFIED BY 'krtt';
DROP USER 'krtt'@'%';
DROP USER 'krtt'@'localhost';
CREATE DATABASE IF NOT EXISTS krtt DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_bin';
CREATE USER 'krtt'@'%' IDENTIFIED BY 'krtt';
CREATE USER 'krtt'@'localhost' IDENTIFIED BY 'krtt';
GRANT ALL ON krtt.* TO 'krtt'@'%' WITH GRANT OPTION;
GRANT ALL ON krtt.* TO 'krtt'@'localhost' WITH GRANT OPTION;
DROP DATABASE IF EXISTS tk_test;
CREATE DATABASE IF NOT EXISTS tk_test DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_bin';
GRANT ALL ON tk_test.* TO 'tk'@'%' WITH GRANT OPTION;
GRANT ALL ON tk_test.* TO 'tk'@'localhost' WITH GRANT OPTION;