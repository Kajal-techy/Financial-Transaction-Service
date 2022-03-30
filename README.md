# Financial-Transaction-Service

Standard spring-boot application to calculate aggregates of data by getting the list of transaction from : https://resttest.bench.co/transactions/
It is a multi-threaded application using executor framework where I send one request to get an idea of the total number of pages to be fetched from the dependent service. Once I have the idea, I send requests in parallel to get all the pages data in parallel.



Few of the things I wanted to add if given more time : 
1. Make my outgoing requests more resilient to network errors i.e. add Client side Timeouts, Retries etc
2. Instead of sending a single request first to get the total-pages, I would have send configurable "N" request in parallel (based on TPS requirement of RestEasy) and keep sending N requests till I get a 404
3. Have better error handling and throw appropriate errors with correct status signifying if its a dependency error or argument error etc.
4. Write much more tests to improve the code coverage.
