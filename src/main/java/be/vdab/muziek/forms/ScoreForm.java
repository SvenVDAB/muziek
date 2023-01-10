package be.vdab.muziek.forms;

import org.hibernate.validator.constraints.Range;

public record ScoreForm(@Range(min = 0, max = 10) int score) {
}

