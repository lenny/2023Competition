package com.team871.config;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PitchEncoderTest {

  @Test
  void testToSemiCircleDegrees() {
    assertAll(
        () -> assertEquals(0, PitchEncoder.toSemiCircleDegrees(0)),
        () -> assertEquals(5, PitchEncoder.toSemiCircleDegrees(5)),
        () -> assertEquals(180, PitchEncoder.toSemiCircleDegrees(180)),
        () -> assertEquals(-179, PitchEncoder.toSemiCircleDegrees(181)),
        () -> assertEquals(-1, PitchEncoder.toSemiCircleDegrees(359)),
        () -> assertEquals(0, PitchEncoder.toSemiCircleDegrees(360)));
  }
}
