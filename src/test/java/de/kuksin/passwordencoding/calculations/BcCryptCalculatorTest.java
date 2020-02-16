package de.kuksin.passwordencoding.calculations;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BcCryptCalculatorTest {

    private BcCryptCalculator bcCryptCalculator = new BcCryptCalculator();

    @Test
    void calculateStrength() {
        // given

        // when
        int strength = bcCryptCalculator.calculateStrength();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength);

        // then
        assertThat(strength).isBetween(4, 31);
    }

    @Test
    void calculateRounds() {
        // given

        // when
        int strength = bcCryptCalculator.calculateStrengthClosestToTimeGoal();

        // then
        assertThat(strength).isBetween(4, 31);
    }

    @Test
    void findCloserToShouldReturnNumber1IfItCloserToGoalThanNumber2() {
        // given
        int number1 = 450;
        int number2 = 551;

        // when
        boolean actual = bcCryptCalculator.isPreviousDurationCloserToGoal(number1, number2);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void findCloserToShouldReturnNUmber2IfItCloserToGoalThanNumber1() {
        // given
        int number1 = 502;
        int number2 = 499;

        // when
        boolean actual = bcCryptCalculator.isPreviousDurationCloserToGoal(number1, number2);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void findCloserToShouldReturnGoalIfNumber2IsEqualGoal() {
        // given
        int number1 = 499;
        int number2 = 500;

        // when
        boolean actual = bcCryptCalculator.isPreviousDurationCloserToGoal(number1, number2);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void findCloserToShouldReturnGoalIfNumber1IsEqualGoal() {
        // given
        int number1 = 500;
        int number2 = 501;

        // when
        boolean actual = bcCryptCalculator.isPreviousDurationCloserToGoal(number1, number2);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void getStrengthShouldReturn4IfStrengthIs4() {
        // given
        int currentStrength = 4;

        // when
        int actual = bcCryptCalculator.getStrength(0, 0, currentStrength);

        // then
        assertThat(actual).isEqualTo(4);
    }

    @Test
    void getStrengthShouldReturnPreviousStrengthIfPreviousDurationCloserToGoal() {
        // given

        // when
        int actual = bcCryptCalculator.getStrength(480, 521, 5);

        // then
        assertThat(actual).isEqualTo(4);
    }

    @Test
    void getStrengthShouldReturnCurrentStrengthIfCurrentDurationCloserToGoal() {
        // given

        // when
        int actual = bcCryptCalculator.getStrength(460, 521, 5);

        // then
        assertThat(actual).isEqualTo(5);
    }
}