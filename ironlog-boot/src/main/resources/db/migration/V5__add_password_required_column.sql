-- 1. Agregamos la columna con un valor por defecto para no romper registros existentes
ALTER TABLE sec.app_user
ADD COLUMN password_change_required BOOLEAN NOT NULL DEFAULT TRUE;

-- 2. (Opcional) Si quieres que los usuarios que ya existen NO tengan que cambiarla,
-- pero los nuevos SÍ, ejecuta esto justo después:
-- UPDATE sec.app_user SET password_change_required = FALSE;

-- 3. Quitamos el valor por defecto del sistema de archivos de la tabla para que
-- JPA gestione el valor explícitamente a partir de ahora, aunque se recomienda
-- dejarlo en la DB como salvaguarda.
COMMENT ON COLUMN sec.app_user.password_change_required IS 'Flag to force user to change password on next login';