-- V20260316_001__add_need_registration_to_app_user.sql

-- Añadir la columna con valor por defecto
ALTER TABLE sec.app_user
ADD COLUMN need_registration BOOLEAN DEFAULT TRUE NOT NULL;

-- Comentario para auditoría de base de datos
COMMENT ON COLUMN sec.app_user.need_registration IS 'Flag para obligar al usuario a completar su perfil base tras el registro inicial';

-- Si ya tienes un Admin creado manualmente, quizás quieras ponerlo en false para no bloquearte a ti mismo
 UPDATE sec.app_user SET need_registration = FALSE WHERE external_id = '0a78e3a2-c672-4208-9192-ec4afdd19296';