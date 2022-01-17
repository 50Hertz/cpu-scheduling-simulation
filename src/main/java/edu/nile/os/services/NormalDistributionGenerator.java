package edu.nile.os.services;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.math.BigDecimal;

public class NormalDistributionGenerator {

    private final long lowerBound = 1;
    private final long upperBound = 20;

    private BigDecimal mean = new BigDecimal("10.5");
    private BigDecimal standardDeviation = new  BigDecimal("5.916079783");

    private final NormalDistribution distribution;

    public NormalDistributionGenerator() {
        this.distribution = new NormalDistribution(mean.doubleValue(), standardDeviation.doubleValue());
    }

    public long sample() {
        long sample = Math.abs(Math.round(distribution.sample()));
        if (sample >= lowerBound && sample <= upperBound) {
            return sample;
        }

        return sample();
    }

}
