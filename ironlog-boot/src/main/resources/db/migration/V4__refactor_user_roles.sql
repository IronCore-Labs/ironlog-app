-- V1.x.x__Refactor_User_Roles_To_Separate_Table.sql

-- 1. Crear la nueva tabla de roles vinculada al esquema sec
CREATE TABLE sec.app_user_role (
    user_id int8 NOT NULL,
    role_name varchar(255) NOT NULL,
    CONSTRAINT fk_app_user_role_user FOREIGN KEY (user_id) REFERENCES sec.app_user(id),
    CONSTRAINT app_user_role_check CHECK (role_name IN ('ROLE_ADMIN', 'ROLE_TRAINER', 'ROLE_CLIENT'))
);

-- 2. Migrar los roles existentes de la columna actual a la nueva tabla
-- Nota: Esto asume que el valor actual en 'role' es compatible
INSERT INTO sec.app_user_role (user_id, role_name)
SELECT id, role FROM sec.app_user WHERE role IS NOT NULL;

-- 3. Eliminar el constraint de chequeo antiguo en la tabla app_user
-- El nombre del constraint puede variar, asegúrate de que sea 'app_user_role_check'
ALTER TABLE sec.app_user DROP CONSTRAINT IF EXISTS app_user_role_check;

-- 4. Eliminar la columna 'role' de la tabla original
ALTER TABLE sec.app_user DROP COLUMN role;

-- 5. Opcional: Crear un índice para búsquedas rápidas por rol
CREATE INDEX idx_app_user_role_user_id ON sec.app_user_role (user_id);