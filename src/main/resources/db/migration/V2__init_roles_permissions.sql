-- Insertar permisos
INSERT INTO permissions (name)
VALUES ('USER_READ')
ON CONFLICT (name) DO NOTHING;

INSERT INTO permissions (name)
VALUES ('USER_WRITE')
ON CONFLICT (name) DO NOTHING;

INSERT INTO permissions (name)
VALUES ('USER_UPDATE')
ON CONFLICT (name) DO NOTHING;

-- Insertar roles
INSERT INTO roles (name)
VALUES ('ROLE_USER')
ON CONFLICT (name) DO NOTHING;

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

-- Asignar permisos a ROLE_USER
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ROLE_USER'
  AND p.name IN ('USER_READ', 'USER_UPDATE')
ON CONFLICT DO NOTHING;

-- Asignar permisos a ROLE_ADMIN
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ROLE_ADMIN'
  AND p.name IN ('USER_READ', 'USER_WRITE', 'USER_UPDATE')
ON CONFLICT DO NOTHING;