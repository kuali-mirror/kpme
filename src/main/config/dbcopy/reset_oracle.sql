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

PURGE RECYCLEBIN;
DECLARE
    CURSOR cur (type VARCHAR2) IS
        SELECT object_type, object_name FROM USER_OBJECTS WHERE object_type = type AND object_name NOT LIKE 'BIN%' ORDER BY object_name;
BEGIN
    FOR obj IN cur('TABLE') LOOP
        EXECUTE IMMEDIATE 'DROP ' || obj.object_type || ' ' || obj.object_name || ' CASCADE CONSTRAINTS PURGE';
    END LOOP;
    FOR obj IN cur('INDEX') loop
        EXECUTE IMMEDIATE 'DROP ' || obj.object_type || ' ' || obj.object_name;
    END LOOP;
    FOR obj IN cur('VIEW') loop
        EXECUTE IMMEDIATE 'DROP ' || obj.object_type || ' ' || obj.object_name;
    END LOOP;
    FOR obj IN cur('SEQUENCE') loop
        EXECUTE IMMEDIATE 'DROP ' || obj.object_type || ' ' || obj.object_name;
    END LOOP;
END;
/
