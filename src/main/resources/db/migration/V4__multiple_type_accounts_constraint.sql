ALTER TABLE accounts
ADD CONSTRAINT unique_user_account_type
UNIQUE (user_id, type);