DROP TABLE IF EXISTS feature;

CREATE TABLE feature (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  email VARCHAR(250) NOT NULL,
  feature_name VARCHAR(250) NOT NULL,
  enable BOOLEAN NOT NULL
);
