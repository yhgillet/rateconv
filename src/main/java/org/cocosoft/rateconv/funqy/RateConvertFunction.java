package org.cocosoft.rateconv.funqy;

import io.quarkus.funqy.Funq;
import org.cocosoft.rateconv.rate.service.ConvertParams;
import org.cocosoft.rateconv.rate.service.RateConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

public class RateConvertFunction {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private RateConvertService rateConvertService;

    @Funq
    public ConvertResult convert(ConvertParams params) {
        try {
            return new ConvertResult(rateConvertService.convert(params));
        } catch (ConstraintViolationException e) {
            return new ConvertResult(e);
        }


    }

}
