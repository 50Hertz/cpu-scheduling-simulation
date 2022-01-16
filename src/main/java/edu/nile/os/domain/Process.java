package edu.nile.os.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Process {
    Integer pid;
    Long burstTime;
    Long arrivalTime;
    Integer priority;
}
