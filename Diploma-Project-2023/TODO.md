### TODO

- [ ] Forward the auth request to auth-service instead of validating the token in the gateway-service (
  AuthenticationFactory.java)
- [ ] Improve the tables relationship in the main service
- [ ] Separate each service to use only its own database/tables and make other services to use REST/gRPC for the needed
  information
- [ ] Create rate limiter for Spring Cloud Gateway
- [ ] CircuitBreakers for Spring Cloud Gateway

--------------------- DONE -----------------------

- [x] Create user-service
- [x] Create mail-service(send and process)