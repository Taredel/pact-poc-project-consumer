# Project for demonstration Contract Testing with Pact.io

## Steps to run

1. Start Pact Broker with Podman (see [Pact Broker](#pact-broker-podman))
2. Run Consumer tests from the corresponding directory by 'mvn verify' command
3. Publish a contract to Pact Broker by 'mvn pact:publish' command
    (public broker: `mvn pact:publish -Dpact.broker.url=https://<ngrok domain>`)
    * Note! That each **new** Pact contract is to be uploaded a Consumer's version
      should be updated
4. Verify published contract by executing 'mvn verify' command from Provider's directory

## Pact Broker (Podman)

`docker-compose.yaml` is the same in the consumer and provider repos and uses the fixed project name
`pact-broker`, so starting it from either repo runs the same broker. It works with `podman compose`
(needs `brew install podman-compose`) as well as `docker compose`.

```shell
podman machine start                 # once per boot, if the machine is stopped
podman compose up -d                 # postgres + broker on http://localhost:9292
podman compose --profile public up -d   # same, plus the ngrok tunnel
podman compose --profile public down    # stop everything (contracts stay in the pact-broker-db volume)
```

Broker credentials: `pact_username` / `pact_password`.

### Public URL (ngrok)

GitHub Actions cannot reach `localhost`, so the broker is exposed through ngrok's free static domain.
Copy `.env.example` to `.env` and set `NGROK_AUTHTOKEN` (and `NGROK_DOMAIN` if it differs).
The tunnel inspector is at _localhost:4040_.

### can-i-deploy

```shell
podman run --rm --network pact-broker_default docker.io/pactfoundation/pact-cli:1.5.0.5 \
  pact-broker can-i-deploy --pacticipant ConsumerService --version 1.0.1 \
  --broker-base-url http://broker_app:9292 --broker-username pact_username --broker-password pact_password
```

## Provider

Is available at _localhost:8080_
