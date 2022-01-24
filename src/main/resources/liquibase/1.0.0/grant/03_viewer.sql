--liquibase formatted sql

--changeset eekovtun:1.0.0/grants/viewer context:!local
--rollback revoke all on schema qrcodes from viewer;
grant usage on schema qrcodes to viewer;
grant select, insert, update on all tables in schema qrcodes to viewer;
grant usage, select on all sequences in schema qrcodes to viewer;