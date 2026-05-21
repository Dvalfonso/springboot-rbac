ALTER TABLE accounts
    ADD COLUMN cbu   VARCHAR(22) NOT NULL,
    ADD CONSTRAINT uk_account_cbu   UNIQUE (cbu);