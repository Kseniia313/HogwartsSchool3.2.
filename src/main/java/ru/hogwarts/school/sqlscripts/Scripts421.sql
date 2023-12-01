ALTER TABLE student ADD CONSTRAINT age_constraint CHECK(age>10);

ALTER TABLE student ADD PRIMARY KEY(name);

ALTER TABLE faculty ADD CONSTRAINT unique_name_and_color UNIQUE(name,color);

ALTER TABLE student ALTER age SET DEFAULT 20;

