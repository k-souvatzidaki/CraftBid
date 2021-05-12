CREATE TABLE Location (
	id INT IDENTITY(1,1) PRIMARY KEY,
	name VARCHAR(MAX) NOT NULL,
	longitude DECIMAL(10,7),
	latitude DECIMAL(10,7),
	UNIQUE(longitude, latitude)
);

CREATE TABLE Category (
	name VARCHAR(20) PRIMARY KEY
);

CREATE TABLE Listing (
	id INT IDENTITY(1,1) PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	description VARCHAR(130) NOT NULL,
	category VARCHAR(20) FOREIGN KEY REFERENCES Category(name) NOT NULL,
	min_price FLOAT DEFAULT(0),
	reward_points INT DEFAULT(0),
	quantity INT DEFAULT(1),
	is_located INT FOREIGN KEY REFERENCES Location(id) NOT NULL,
	published_by VARCHAR(20) FOREIGN KEY REFERENCES Creator(username) NOT NULL
);

CREATE TABLE Photo (
	path VARCHAR(40) PRIMARY KEY,
	listing INT FOREIGN KEY REFERENCES Listing(id) NOT NULL
);

-- issue related changes
ALTER TABLE Listing
ADD date_published DATE NOT NULL, 
    thumbnail VARCHAR(40) NOT NULL;

ALTER TABLE Listing DROP CONSTRAINT FK__Listing__is_loca__5AEE82B9;

ALTER TABLE Location DROP CONSTRAINT PK__Location__3213E83F8AFA903D;

ALTER TABLE Location ALTER COLUMN name VARCHAR(50) NOT NULL;
ALTER TABLE Location ADD PRIMARY KEY(name);

ALTER TABLE Location DROP COLUMN id;

ALTER TABLE Listing ALTER COLUMN is_located VARCHAR(50) NOT NULL;
ALTER TABLE Listing ADD FOREIGN KEY(is_located) REFERENCES Location(name);

ALTER TABLE Listing ADD delivery VARCHAR(25) NOT NULL;