-- 1. Creación de la secuencia para el ID
CREATE SEQUENCE IF NOT EXISTS training.enrollment_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- 2. Creación de la tabla de inscripciones/vinculaciones
CREATE TABLE IF NOT EXISTS training.enrollment (
    id                BIGINT PRIMARY KEY DEFAULT nextval('training.enrollment_seq'),
    external_id       UUID NOT NULL UNIQUE, -- Heredado de BaseEntity
    trainer_id        BIGINT NOT NULL,
    client_id         BIGINT NOT NULL,
    assignment_date   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Auditoría (Heredados de BaseEntity si los usas)
    created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP,

    -- Constraints de integridad
    CONSTRAINT fk_enrollment_trainer FOREIGN KEY (trainer_id)
        REFERENCES training.trainer(id) ON DELETE CASCADE,
    CONSTRAINT fk_enrollment_client FOREIGN KEY (client_id)
        REFERENCES training.client(id) ON DELETE CASCADE,

    -- Evitar que un cliente se inscriba dos veces con el mismo entrenador
    CONSTRAINT uq_trainer_client UNIQUE (trainer_id, client_id)
);

-- 3. Índices para optimizar búsquedas de relaciones
CREATE INDEX IF NOT EXISTS idx_enrollment_trainer_id ON training.enrollment(trainer_id);
CREATE INDEX IF NOT EXISTS idx_enrollment_client_id ON training.enrollment(client_id);

-- 4. Comentarios para documentación de DB
COMMENT ON TABLE training.enrollment IS 'Relación de vinculación entre un entrenador y un cliente/atleta';
COMMENT ON COLUMN training.enrollment.assignment_date IS 'Fecha en la que se formalizó el entrenamiento';