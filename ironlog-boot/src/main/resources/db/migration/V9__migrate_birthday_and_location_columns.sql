-- 1. Añadimos las columnas a la tabla de identidad central
ALTER TABLE sec.app_user
ADD COLUMN birthday DATE,
ADD COLUMN location VARCHAR(255);

-- 2. Migramos los datos de los Entrenadores
UPDATE sec.app_user u
SET birthday = t.birthday,
    location = t.location
FROM training.trainer t
WHERE u.id = t.id;

-- 3. Migramos los datos de los Clientes
UPDATE sec.app_user u
SET birthday = c.birthday,
    location = c.location
FROM training.client c
WHERE u.id = c.id;

-- 4. Limpiamos las tablas de perfil en el esquema de entrenamiento
ALTER TABLE training.trainer DROP COLUMN birthday, DROP COLUMN location;
ALTER TABLE training.client DROP COLUMN birthday, DROP COLUMN location;