--liquibase formatted sql

--changeset eekovtun:1.0.0/grants/qrcode_handler context:!local
--rollback revoke all on schema qrcodes from qrcode_handler;
grant connect on database qrcheck to qrcode_handler;
grant usage on schema qrcodes to qrcode_handler;
grant select, insert, update, delete on all tables in schema qrcodes to qrcode_handler;
grant usage, select on all sequences in schema qrcodes to qrcode_handler;