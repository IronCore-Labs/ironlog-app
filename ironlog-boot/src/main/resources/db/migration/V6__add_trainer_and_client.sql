-- 1. Tabla de Entrenadores (Reflejo de tu entidad Trainer)
CREATE TABLE training.trainer (
    id int8 NOT NULL,                     -- PK compartida con sec.app_user
    birthday date,
    location varchar(255),
    speciality varchar(255),              -- Tu nueva columna
    bio text,                             -- Tu nueva columna (Uso text para mayor flexibilidad)
    experience_years int4,                -- Tu nueva columna
    need_registration bool NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT trainer_pkey PRIMARY KEY (id),
    CONSTRAINT fk_trainer_user FOREIGN KEY (id) REFERENCES sec.app_user(id) ON DELETE CASCADE
);

-- 2. Tabla de Clientes (Reflejo de tu entidad Client)
CREATE TABLE training.client (
    id int8 NOT NULL,                     -- PK compartida con sec.app_user
    birthday date,
    location varchar(255),
    weight numeric(5, 2),                 -- BigDecimal(5,2)
    height numeric(3, 2),                 -- BigDecimal(3,2)
    need_registration bool NOT NULL,
    has_surgeries bool NOT NULL DEFAULT false,
    surgery_details text,
    heart_conditions text,
    allergies text,
    medications text,
    observations text,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT client_pkey PRIMARY KEY (id),
    CONSTRAINT fk_client_user FOREIGN KEY (id) REFERENCES sec.app_user(id) ON DELETE CASCADE
);

-- 3. Índices de rendimiento para el filtrado de registros pendientes
CREATE INDEX idx_trainer_need_reg ON training.trainer (need_registration);
CREATE INDEX idx_client_need_reg ON training.client (need_registration);