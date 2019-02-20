alter table user_profile add 
    foreign key (user_type_oid)
    references user_type(user_type_oid)
;

alter table role_profile add 
    foreign key (user_type_oid)
    references user_type(user_type_oid)
;

alter table user_role add 
    foreign key (user_oid)
    references user_profile(user_oid)
;

alter table user_role add 
    foreign key (role_oid)
    references role_profile(role_oid)
;

alter table module add 
    foreign key (parent_oid)
    references module(module_oid)
;

alter table operation add 
    foreign key (module_oid)
    references module(module_oid)
;

alter table operation_url add 
    foreign key (opn_oid)
    references operation(opn_oid)
;

alter table role_operation add 
    foreign key (role_oid)
    references role_profile(role_oid)
;

alter table role_operation add 
    foreign key (opn_oid)
    references operation(opn_oid)
;

alter table user_type_operation add 
    foreign key (user_type_oid)
    references user_type(user_type_oid)
;

alter table user_type_operation add 
    foreign key (opn_oid)
    references operation(opn_oid)
;

alter table account add 
    foreign key (owner_oid)
    references user_profile(user_oid)
;

alter table incoming add 
    foreign key (owner_oid)
    references user_profile(user_oid)
;

alter table account_incoming add 
    foreign key (acnt_oid)
    references account(acnt_oid)
;

alter table account_incoming add 
    foreign key (incoming_oid)
    references incoming(incoming_oid)
;

alter table category add 
    foreign key (parent_oid)
    references category(category_oid)
;

alter table consumption_item add 
    foreign key (owner_oid)
    references user_profile(user_oid)
;

alter table consumption_item add 
    foreign key (cpn_oid)
    references consumption(cpn_oid)
;

alter table consumption_item add 
    foreign key (category_oid)
    references category(category_oid)
;

alter table account_consumption add 
    foreign key (cpn_oid)
    references consumption(cpn_oid)
;

alter table account_consumption add 
    foreign key (acnt_oid)
    references account(acnt_oid)
;

alter table account_audit add 
    foreign key (cpn_oid)
    references consumption(cpn_oid)
;

alter table account_audit add 
    foreign key (incoming_oid)
    references incoming(incoming_oid)
;

alter table account_audit add 
    foreign key (acnt_oid)
    references account(acnt_oid)
;

alter table account_audit add 
    foreign key (ref_acnt_oid)
    references account(acnt_oid)
;
