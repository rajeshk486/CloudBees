# CloudBees
contains assignement given by cloudBees
[Readme.md](Readme.md)# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Swagger Api Document](https://github.com/rajeshk486/CloudBees/blob/main/TicketReservation/src/main/resources/swagger.yaml)
* [Test cases are included](https://github.com/rajeshk486/CloudBees/tree/main/TicketReservation/src/test/java/com/ticket/reservation)
  * [ticketController](https://github.com/rajeshk486/CloudBees/blob/main/TicketReservation/src/test/java/com/ticket/reservation/Controller/TicketControllerTest.java)
  * [ticketServiceImplementation](https://github.com/rajeshk486/CloudBees/blob/main/TicketReservation/src/test/java/com/ticket/reservation/Service/Impl/TicketServiceImplTest.java)
### Guides
This Application designed in layered manner
1. swagger document for API specification
2. Controller to initate calls from external world
3. Models to represent the data to be stored/retrived/processed
4. Repository contains the query and functions to access the data
5. Service layer is a interface layer
6. Impl layer is the implementation of service interface
7. ![img.png]<img width="716" alt="Screenshot 2024-05-21 at 9 41 38 PM" src="https://github.com/rajeshk486/CloudBees/assets/2852510/f38ca9f4-bc56-41d6-901f-5aeb4ce914d1">
Unit tests also written and executed successfully
<img width="305" alt="image" src="https://github.com/rajeshk486/CloudBees/assets/2852510/42f1973b-673a-49a6-8583-5ae0b0d368e0">

9. Buy ticket:
     curl --location 'localhost:8083/tickets' \
      --header 'Content-Type: application/json' \
      --data-raw '{
      "user":{"firstName": "rajesh",
      "lastName": "kamal",
      "email": "rajeshk486@gmail.com"
      },
      "from":"London",
      "to":"paris",
      "price":23,
      "section":"A"}'
10. Get ticket by id:
   curl --location --request GET 'localhost:8083/tickets/2' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "user":{"firstName": "rajesh",
   "lastName": "kamal",
   "email": "rajeshk486@gmail.com"
   },
   "from":"London",
   "to":"paris",
   "price":23,
   "section":"A"}'
for others please check the Swagger documentation
