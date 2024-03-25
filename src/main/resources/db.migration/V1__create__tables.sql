CREATE TABLE IF NOT EXISTS tb_person (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    birth_date DATE NOT NULL,
    type VARCHAR(30) NOT NULL,
    cellphone VARCHAR(13) NOT NULL,
    personal_document VARCHAR(14) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tb_account (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    agency_number VARCHAR(4) NOT NULL,
    email VARCHAR(70) NOT NULL,
    password VARCHAR(20) NOT NULL,
    person_id VARCHAR(36) NOT NULL,
    type VARCHAR(10) NOT NULL,
    status VARCHAR(30),
    personal_document VARCHAR(14) NOT NULL,
    number VARCHAR(8) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (person_id) REFERENCES tb_person(id)
);

CREATE TABLE IF NOT EXISTS tb_pix_key (
    id VARCHAR(36) PRIMARY KEY,
    key_value VARCHAR(255) NOT NULL,
    key_type VARCHAR(10) NOT NULL,
    status VARCHAR(30) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    inactivated_at TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES tb_account(id)
);


-- Create indexes
CREATE INDEX idx_person_id ON tb_account (person_id);
CREATE INDEX idx_account_personal_document ON tb_account (personal_document);
CREATE INDEX idx_number ON tb_account (number);
CREATE INDEX idx_person_personal_document ON tb_person (personal_document);
CREATE INDEX idx_key_type ON tb_pix_key (key_type);
CREATE INDEX idx_status ON tb_pix_key (status);
