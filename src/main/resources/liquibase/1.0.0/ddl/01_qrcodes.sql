--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/qrcode_seq
--rollback drop sequence qrcodes.qrcode_seq;
create sequence qrcodes.qrcode_seq;

--changeset eekovtun:1.0.0/ddl/qrcodes
--rollback drop table qrcodes.qrcodes;
create table qrcodes.qrcodes
(
    id                 bigint       default nextval('qrcodes.qrcode_seq') not null
        constraint qrcodes_pk primary key,
    external_user_id   bigint                                             not null,
    external_event_id  bigint,
    external_option_id bigint,
    external_order_id  bigint                                             not null,
    url                varchar(1000)                                      not null,
    type               varchar(255)                                       not null,
    content            varchar(1000),
    salt               varchar(40)                                        not null,
    applied            boolean      default false,
    date_applied       timestamp(0),
    created            timestamp(0) default now()                         not null,
    updated            timestamp(0) default now()                         not null
);

create unique index qrcodes_external_user_id_content_index
    on qrcodes.qrcodes (external_user_id, content);

comment on table qrcodes.qrcodes is 'Table stores qrcodes for events/options';
comment on column qrcodes.qrcodes.id is 'Primary key of the table';
comment on column qrcodes.qrcodes.external_user_id is 'Primary key of users table from user-handler service';
comment on column qrcodes.qrcodes.external_event_id is 'Primary key of events table from events service. Null if qrcodes.type is option';
comment on column qrcodes.qrcodes.external_option_id is 'Primary key of options table from events service. Null if qrcodes.type is event';
comment on column qrcodes.qrcodes.external_order_id is 'Primary key of orders table from orders service';
comment on column qrcodes.qrcodes.type is 'URL where a qrcode was saved';
comment on column qrcodes.qrcodes.type is 'Type of qrcode, can be event, option etc';
comment on column qrcodes.qrcodes.content is 'Contain a string kind of external_user_id;salt';
comment on column qrcodes.qrcodes.salt is 'Salt of a qrcode, look at https://en.wikipedia.org/wiki/Salt_(cryptography)';
comment on column qrcodes.qrcodes.applied is 'Use flag. If true then the qrcode was used';
comment on column qrcodes.qrcodes.date_applied is 'Date when the qrcode was used. Null if qrcode_events.applied = false';
comment on column qrcodes.qrcodes.created is 'Time of created';
comment on column qrcodes.qrcodes.updated is 'Time of the last updated';

--changeset eekovtun:1.0.0/ddl/qrcodes_audit context:!local
--rollback drop trigger qrcodes_audit on qrcodes.qrcodes;
create trigger qrcodes_audit
    after insert or update or delete
    on qrcodes.qrcodes
    for each row
execute procedure audit.audit_func();