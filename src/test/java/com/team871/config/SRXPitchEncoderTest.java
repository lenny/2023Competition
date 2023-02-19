package com.team871.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SRXPitchEncoderTest {

  @Test
  void totalDegreesLessValueThan180ReturnsRawDegrees() {
    assertAll(
        () -> assertEquals(0, SRXPitchEncoder.calculateDegrees(0)),
        () -> assertEquals(5, SRXPitchEncoder.calculateDegrees(5)),
        () -> assertEquals(180, SRXPitchEncoder.calculateDegrees(180)),
        () -> assertEquals(-179, SRXPitchEncoder.calculateDegrees(181)),
        () -> assertEquals(-1, SRXPitchEncoder.calculateDegrees(359)),
        () -> assertEquals(0, SRXPitchEncoder.calculateDegrees(360)));
  }
}
