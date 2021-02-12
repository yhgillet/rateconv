# Project

Convert typical flight contollers rates. Supports RaceFlight, BetaFlight, and KISS conversions.

Chek it here ! https://yhgillet.github.io/rateconv/

This tool was highly inspired by https://github.com/apocolipse/RotorPirates (thanks !)

## Usage

Use the online tool and if the conversion doesnt work, you can still use it as a routine, you will need java installed
for that.

Download the released jar `rateconv-<VERSION>.jar` then run

```
java -jar rateconv-<VERSION>.jar <type> <param1> <param2> <param3>

Where: <type> in [bf,ki,rf] and <paramX> are the rate parameters
bf: <rc_rate> <super_rate> <rc_expo>
ki: <rc_rate> <rate> <rc_curve>
rf: <rate> <expo> <acro_plus>
```

## Development

### Run dev mode

Run `./mvnw quarkus:dev`

Open browser to http://localhost:8080/

You can use the quarkus dev console here http://localhost:8080/q/dev/

### Build native

run `./mvnw package -Pnative -Dquarkus.native.container-build=true
`

### Test aws lambda in local

Install aws sam client.

run `sam local start-api --template target/sam.native.yaml`

Open browser to http://localhost:3000/rateconv