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
