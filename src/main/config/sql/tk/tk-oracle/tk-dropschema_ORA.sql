BEGIN
  FOR i IN (SELECT sequence_name FROM user_sequences)
    LOOP
      EXECUTE IMMEDIATE('DROP SEQUENCE ' || user || '.' || i.sequence_name);
    END LOOP;
 
    FOR i IN (SELECT table_name FROM user_tables)
    LOOP
      EXECUTE IMMEDIATE('DROP TABLE ' || user || '.' || i.table_name || ' CASCADE CONSTRAINTS');
    END LOOP;
END;
/
