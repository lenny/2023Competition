package com.team871;

import static org.junit.jupiter.api.Assertions.*;

import com.team871.config.SRXDistanceEncoder;
import com.team871.config.SRXPitchEncoder;
import org.junit.jupiter.api.Test;

class SRXPitchEncoderTest {

    @Test
    void returnValueBetween0And180() {
        assertEquals(0, SRXPitchEncoder.calculateDegrees(0));
        assertEquals(90, SRXPitchEncoder.calculateDegrees(90));
        assertEquals(180, SRXPitchEncoder.calculateDegrees(180));
        assertEquals(-179, SRXPitchEncoder.calculateDegrees(181));
        assertEquals(-90, SRXPitchEncoder.calculateDegrees(270));
        assertEquals(-1, SRXPitchEncoder.calculateDegrees(359));
        assertEquals(0, SRXPitchEncoder.calculateDegrees(360));
        assertEquals(10, SRXPitchEncoder.calculateDegrees(370));
    }
}
