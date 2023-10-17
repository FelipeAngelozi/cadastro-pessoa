CREATE TABLE contacts(

   id INT8 GENERATED ALWAYS AS IDENTITY,
   person_id INT8,
   name VARCHAR(255) NOT NULL,
   phone VARCHAR(15) NOT NULL,
   email VARCHAR(100) NOT NULL,

   PRIMARY KEY(id),
   CONSTRAINT fk_contacts_references_person FOREIGN KEY(person_id) REFERENCES person(id)
);