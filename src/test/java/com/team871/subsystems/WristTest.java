package com.team871.subsystems;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WristTest {
  @Test
  void testCalculateLevelSetpointOffsetsShoulderAndBotPitch() {
    assertAll(
        () -> assertEquals(-0.0, Wrist.calculateLevelSetpoint(0.0)),
        () -> assertEquals(-1.5, Wrist.calculateLevelSetpoint(1.5)),
        () -> assertEquals(-2.0, Wrist.calculateLevelSetpoint(2.0)),
        () -> assertEquals(30.0, Wrist.calculateLevelSetpoint(-30.0)),
        () -> assertEquals(-30.0, Wrist.calculateLevelSetpoint(30.0)),
        () -> assertEquals(-180.0, Wrist.calculateLevelSetpoint(180)));
  }
}
