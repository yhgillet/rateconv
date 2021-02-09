# rateconv
Convert rates from and to other. Supports  RaceFlight, BetaFlight, and KISS.

## usage
You will need java installed.

```
java rateconv-<VERSION>.jar <type> <param1> <param2> <param3>

Where: <type> in [bf,ki,rf] and <paramX> are the rate parameters
bf: <rc_rate> <super_rate> <rc_expo>
ki: <rc_rate> <rate> <rc_curve>
rf: <rate> <expo> <acro_plus>
```
