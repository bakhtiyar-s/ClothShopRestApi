INSERT INTO public.permission (id, name) VALUES (1, 'WRITE');
INSERT INTO public.permission (id, name) VALUES (2, 'READ');

INSERT INTO public.role (id, name) VALUES (1, 'ADMIN');
INSERT INTO public.role (id, name) VALUES (2, 'USER');

INSERT INTO public.role_permissions (role_id, permissions_id) VALUES (1, 1);
INSERT INTO public.role_permissions (role_id, permissions_id) VALUES (1, 2);
INSERT INTO public.role_permissions (role_id, permissions_id) VALUES (2, 2);

INSERT INTO public.users (id, email, first_name, last_name, password, phone, username, role_id) VALUES (1, 'admin@mail.com', 'admin', 'admin', 'admin', null, 'admin', 1);
INSERT INTO public.users (id, email, first_name, last_name, password, phone, username, role_id) VALUES (2, 'user@mail.com', 'user', 'user', 'user', null, 'user', 2);
