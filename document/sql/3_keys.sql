create unique index idx_login_id_unique on user_profile(login_id);

create index idx_cpn_time on consumption(cpn_time);

create index idx_incoming_date on incoming(incoming_date);

create unique index udx_incoming_batchnum on incoming(batch_num);

create unique index udx_consumption_batchnum on consumption(batch_num);

create index idx_account_audit_batchnum on account_audit(batch_num);
