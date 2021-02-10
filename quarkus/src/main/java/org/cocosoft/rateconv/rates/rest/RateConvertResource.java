package org.cocosoft.rateconv.rates.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocosoft.rateconv.rates.fitter.RateFitter;
import org.cocosoft.rateconv.rates.fitter.RateResult;
import org.cocosoft.rateconv.rates.BFRate;
import org.cocosoft.rateconv.rates.KissRate;
import org.cocosoft.rateconv.rates.RaceFlightRate;
import org.cocosoft.rateconv.rates.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/rateconv")
public class RateConvertResource {

  private Logger logger = LoggerFactory.getLogger(RateConvertResource.class);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<RateResult> convert(@QueryParam("type") String type, @QueryParam("p1") double param1, @QueryParam("p2") double param2, @QueryParam("p3") double param3) {

    logger.info("Convert for {} {} {} {}", type, param1, param2, param3);

    if (type == null) {
      throw new WebApplicationException("missing type", Response.Status.BAD_REQUEST);
    }

    if (param1 <= 0 || param2 <= 0 || param3 <= 0) {
      throw new WebApplicationException("bad params", Response.Status.BAD_REQUEST);
    }

    KissRate destKiss = new KissRate(1, 0.2, 0.1);
    BFRate destBf = new BFRate(1, 0.2, 0.1);
    RaceFlightRate destRF = new RaceFlightRate(100, 50, 100);

    List<RateResult> results = null;
    switch (type) {
      case "bf": {
        results = convert(new BFRate(param1, param2, param3), destKiss, destRF);
        break;
      }
      case "ki": {
        results = convert(new KissRate(param1, param2, param3), destBf, destRF);
        break;
      }
      case "rf": {
        results = convert(new RaceFlightRate(param1, param2, param3), destBf, destKiss);
        break;
      }
      default:
        throw new WebApplicationException("unknown type", Response.Status.BAD_REQUEST);
    }

    logger.info("End convert");
    return results;
  }

  private List<RateResult> convert(Rate originRate, Rate... destRates) {
    List<RateResult> results = new ArrayList<>(3);
    RateFitter fitter = new RateFitter();
    results.add(new RateResult(originRate, 100));
    for (Rate dest : destRates) {
      results.add(fitter.convert(originRate, dest));
    }
    return results;
  }
}