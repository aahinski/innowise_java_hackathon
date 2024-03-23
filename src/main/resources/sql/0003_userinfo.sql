CREATE TABLE user_info (
                             id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                             chat_id VARCHAR(255) NOT NULL,
                             change_rate_trigger VARCHAR(100) NOT NULL,
                             CONSTRAINT fk_change_rate_trigger FOREIGN KEY (change_rate_trigger) REFERENCES change_rate_trigger (name)
);

CREATE TABLE user_currency (
                               user_id UUID NOT NULL,
                               currency_name VARCHAR(255) NOT NULL,
                               PRIMARY KEY (user_id, currency_name),
                               FOREIGN KEY (user_id) REFERENCES user_info (id) ON DELETE CASCADE,
                               FOREIGN KEY (currency_name) REFERENCES currency (name) ON DELETE CASCADE
);