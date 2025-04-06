CREATE TYPE insurance_policy_status AS ENUM ('ACTIVE', 'INACTIVE');

CREATE TABLE insurance_policies (
    policy_id SERIAL PRIMARY KEY,
    policy_name VARCHAR(255),
    policy_status insurance_policy_status NOT NULL,
    coverage_start_date DATE NOT NULL,
    coverage_end_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
