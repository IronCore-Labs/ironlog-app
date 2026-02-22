-- 1. Renombramos la columna 'fullName' (o como se llamara la informativa) a 'first_name'
-- Nota: Si el nombre de la columna era 'full_name', ajusta el script
ALTER TABLE sec.app_user RENAME COLUMN full_name TO first_name;

-- 2. Agregamos la columna para el apellido
ALTER TABLE sec.app_user ADD COLUMN last_name VARCHAR(100);

-- 3. (Opcional pero recomendado) Si quieres que la tabla se llame 'users' para ser más estándar
-- ALTER TABLE app_user RENAME TO users;

-- 4. Aseguramos que las columnas de auditoría existan si no estaban
-- ALTER TABLE app_user ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
-- ALTER TABLE app_user ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizamos al Admin con un valor genérico o real
UPDATE sec.app_user SET last_name = 'Administrator' WHERE last_name IS NULL;

-- Ahora sí, la hacemos obligatoria para siempre
ALTER TABLE sec.app_user ALTER COLUMN last_name SET NOT NULL;

-- 1. Agregamos las columnas
ALTER TABLE sec.app_user ADD COLUMN created_at TIMESTAMP;
ALTER TABLE sec.app_user ADD COLUMN updated_at TIMESTAMP;
ALTER TABLE sec.app_user ADD COLUMN created_by BIGINT;
ALTER TABLE sec.app_user ADD COLUMN updated_by BIGINT;

-- 2. Solo obligamos a las fechas para los registros actuales
UPDATE sec.app_user
SET created_at = CURRENT_TIMESTAMP,
    updated_at = CURRENT_TIMESTAMP
WHERE created_at IS NULL;

-- 3. Ponemos el NOT NULL solo en las fechas
ALTER TABLE sec.app_user ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE sec.app_user ALTER COLUMN updated_at SET NOT NULL;

-- 4. Las Foreign Keys siguen siendo recomendables (aunque permitan nulos)
-- Esto asegura que si hay un ID, este sea válido.
ALTER TABLE sec.app_user
ADD CONSTRAINT fk_user_created_by FOREIGN KEY (created_by) REFERENCES sec.app_user(id);

ALTER TABLE sec.app_user
ADD CONSTRAINT fk_user_updated_by FOREIGN KEY (updated_by) REFERENCES sec.app_user(id);