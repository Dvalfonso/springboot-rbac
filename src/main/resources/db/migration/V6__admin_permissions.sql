INSERT INTO permissions (name)
VALUES ('USER_READ_ALL')
ON CONFLICT (name) DO NOTHING;

INSERT INTO permissions (name)
VALUES ('USER_DELETE')
ON CONFLICT (name) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ROLE_ADMIN'
  AND p.name IN ('USER_READ_ALL', 'USER_DELETE')
ON CONFLICT DO NOTHING;