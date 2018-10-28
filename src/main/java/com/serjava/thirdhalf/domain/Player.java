package com.serjava.thirdhalf.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Player {

    private long id;
    private String name;
    private int speed;
    private int pass;
    private int kick;
    private int defense;
    private int heading;
    private int goalkeeping;
}
