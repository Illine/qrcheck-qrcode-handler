--liquibase formatted sql

--changeset eekovtun:1.0.0/grants/editor context:!local
--rollback revoke all on schema qrcodes from editor;
grant usage on schema qrcodes to editor;
grant select, insert, update, delete on all tables in schema qrcodes to editor;
grant usage, select on all sequences in schema qrcodes to editor;