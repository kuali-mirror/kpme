Errors:
  Unexpected value C:/java/projects/trojantime/src/main/resources: parameters must start with a '--'

Usage: java -jar liquibase.jar [options] [command]

Standard Commands:
 update                         Updates database to current version
 updateSQL                      Writes SQL to update database to current
                                version to STDOUT
 updateCount <num>              Applies next NUM changes to the database
 updateSQL <num>                Writes SQL to apply next NUM changes
                                to the database
 rollback <tag>                 Rolls back the database to the the state is was
                                when the tag was applied
 rollbackSQL <tag>              Writes SQL to roll back the database to that
                                state it was in when the tag was applied
                                to STDOUT
 rollbackToDate <date/time>     Rolls back the database to the the state is was
                                at the given date/time.
                                Date Format: yyyy-MM-dd HH:mm:ss
 rollbackToDateSQL <date/time>  Writes SQL to roll back the database to that
                                state it was in at the given date/time version
                                to STDOUT
 rollbackCount <value>          Rolls back the last <value> change sets
                                applied to the database
 rollbackCountSQL <value>       Writes SQL to roll back the last
                                <value> change sets to STDOUT
                                applied to the database
 futureRollbackSQL              Writes SQL to roll back the database to the 
                                current state after the changes in the 
                                changeslog have been applied
 updateTestingRollback          Updates database, then rolls back changes before
                                updating again. Useful for testing
                                rollback support
 generateChangeLog              Writes Change Log XML to copy the current state
                                of the database to standard out

Diff Commands
 diff [diff parameters]          Writes description of differences
                                 to standard out
 diffChangeLog [diff parameters] Writes Change Log XML to update
                                 the database
                                 to the reference database to standard out

Documentation Commands
 dbDoc <outputDirectory>         Generates Javadoc-like documentation
                                 based on current database and change log

Maintenance Commands
 tag <tag string>          'Tags' the current database state for future rollback
 status [--verbose]        Outputs count (list if --verbose) of unrun changesets
 validate                  Checks changelog for errors
 clearCheckSums            Removes all saved checksums from database log.
                           Useful for 'MD5Sum Check Failed' errors
 changelogSync             Mark all changes as executed in the database
 changelogSyncSQL          Writes SQL to mark all changes as executed 
                           in the database to STDOUT
 markNextChangeSetRan      Mark the next change changes as executed 
                           in the database
 markNextChangeSetRanSQL   Writes SQL to mark the next change 
                           as executed in the database to STDOUT
 listLocks                 Lists who currently has locks on the
                           database changelog
 releaseLocks              Releases all locks on the database changelog
 dropAll                   Drop all database objects owned by user

Required Parameters:
 --changeLogFile=<path and filename>        Migration file
 --username=<value>                         Database username
 --password=<value>                         Database password
 --url=<value>                              Database URL

Optional Parameters:
 --classpath=<value>                        Classpath containing
                                            migration files and JDBC Driver
 --driver=<jdbc.driver.ClassName>           Database driver class name
 --databaseClass=<database.ClassName>       custom liquibase.database.Database
                                            implementation to use
 --defaultSchemaName=<name>                 Default database schema to use
 --contexts=<value>                         ChangeSet contexts to execute
 --defaultsFile=</path/to/file.properties>  File with default option values
                                            (default: ./liquibase.properties)
 --driverPropertiesFile=</path/to/file.properties>  File with custom properties
                                            to be set on the JDBC connection
                                            to be created
 --includeSystemClasspath=<true|false>      Include the system classpath
                                            in the Liquibase classpath
                                            (default: true)
 --promptForNonLocalDatabase=<true|false>   Prompt if non-localhost
                                            databases (default: false)
 --logLevel=<level>                         Execution log level
                                            (debug, info, warning, severe, off
 --logFile=<file>                           Log file
 --currentDateTimeFunction=<value>          Overrides current date time function
                                            used in SQL.
                                            Useful for unsupported databases
 --help                                     Prints this message
 --version                                  Prints this version information

Required Diff Parameters:
 --referenceUsername=<value>                Reference Database username
 --referencePassword=<value>                Reference Database password
 --referenceUrl=<value>                     Reference Database URL

Optional Diff Parameters:
 --referenceDriver=<jdbc.driver.ClassName>  Reference Database driver class name
 --dataOutputDirectory=DIR                  Output data as CSV in the given 
                                            directory

Change Log Properties:
 -D<property.name>=<property.value>         Pass a name/value pair for
                                            substitution in the change log(s)

Default value for parameters can be stored in a file called
'liquibase.properties' that is read from the current working directory.

Full documentation is available at
http://www.liquibase.org/manual/command_line

