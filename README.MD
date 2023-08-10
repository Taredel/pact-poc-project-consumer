# Project for demonstration Contract Testing with Pact.io

## Steps to run

1. Start Pact Broker in Docker using 'docker-compose.yaml' file
2. Run Consumer tests from the corresponding directory by 'mvn verify' command
3. Publish a contract to Pact Broker by 'mvn pact:publish' command
    * Note! That each **new** Pact contract is to be uploaded a Consumer's version
        should be updated
4. Verify published contract by executing 'mvn verify' command from Provider's directory

## Pact Broker

Is available at _localhost:9292_

## Provider

Is available at _localhost:8080_
