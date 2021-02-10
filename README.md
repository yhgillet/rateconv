# rateconv

Library for conversion

## Usage

You can use it as a routine, you will need java installed for that.

```
java -jar rateconv-<VERSION>.jar <type> <param1> <param2> <param3>

Where: <type> in [bf,ki,rf] and <paramX> are the rate parameters
bf: <rc_rate> <super_rate> <rc_expo>
ki: <rc_rate> <rate> <rc_curve>
rf: <rate> <expo> <acro_plus>
```

## Visualize rates

You can use this tool to play with rates: [https://apocolipse.github.io/RotorPirates/]

## Development

### Run dev mode

Run `./mvnw quarkus:dev`

Open browser to http://localhost:8080/

### Build native

run `./mvnw package -Pnative -Dquarkus.native.container-build=true
`

### Test aws lambda in local

Install aws sam client.

run `sam local start-api --template target/sam.native.yaml`

Open browser to http://localhost:3000/rateconv