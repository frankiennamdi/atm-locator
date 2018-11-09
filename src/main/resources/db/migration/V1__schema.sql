CREATE TABLE ATM (
  id INT(11) NOT NULL,
  address_street VARCHAR(250) NOT NULL,
  address_house_number VARCHAR(50) NOT NULL,
  address_postal_code VARCHAR(50) NOT NULL,
  address_city VARCHAR(50) NOT NULL,
  address_geolocation_lat VARCHAR(250) NOT NULL,
  address_geolocation_lng VARCHAR(250) NOT NULL,
  distance INT NOT NULL,
  type VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ATM_UK (address_street, address_house_number, address_postal_code, address_city,
    address_geolocation_lat, address_geolocation_lng, distance, type )
);

CREATE SEQUENCE HIBERNATE_SEQUENCE;
