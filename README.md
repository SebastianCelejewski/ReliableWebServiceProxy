ReliableWebServiceProxy
=======================

A web service client proxy that has the following characteristics:
- Can be instantiated when the target web service is down.
- Detects whether the target web service is up or down during initialization.
- Can handle requests to the target webservice if that web service was not working when proxy was instantiated, but is available later.
