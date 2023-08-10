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

## 

docker exec -it 82062397398a81980cf9d7f70d0117b6cabe0e8c006a008fd28af718fdd5f334 sh

pact-broker can-i-deploy --pacticipant ConsumerService --version 2.0.0 --broker-base-url http://broker_app:9292 --broker-username pact_username --broker-password pact_password
