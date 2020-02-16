package de.kuksin.passwordencoding.calculations;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Pbkdf2CalculatorTest {


    private Pbkdf2Calculator pbkdf2Calculator = new Pbkdf2Calculator();

    @Test
    void calculateIteration() {
        // given

        // when
        int iterationNumber = pbkdf2Calculator.calculateIteration();

        // then
        assertThat(iterationNumber).isGreaterThanOrEqualTo(150000);
    }
}