-- TODO get table name from config.yml

PRAGMA journal_mode = MEMORY;
PRAGMA synchronous = OFF;
PRAGMA foreign_keys = OFF;
PRAGMA ignore_check_constraints = OFF;
PRAGMA auto_vacuum = NONE;
PRAGMA secure_delete = OFF;
BEGIN TRANSACTION;


CREATE TABLE IF NOT EXISTS `players` (
`id` INTEGER NOT NULL ,
`username` TEXT DEFAULT NULL,
PRIMARY KEY (`id`)
);

COMMIT;
PRAGMA ignore_check_constraints = ON;
PRAGMA foreign_keys = ON;
PRAGMA journal_mode = WAL;
PRAGMA synchronous = NORMAL;