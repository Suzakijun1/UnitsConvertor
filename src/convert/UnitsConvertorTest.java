package convert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class UnitsConvertorTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
    	// Redirect System.out to capture output for verification
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
    	// Restore System.out and System.in after tests
        System.setOut(originalOut);
        System.setIn((InputStream) originalIn);
    }


    @Test
    public void testMilToMmSuccess() {
    	// Verifies conversion from mil to millimeters is accurate within a tolerance
        assertEquals(0.0254, UnitsConvertor.toMm(1, "mil"), 0.001, "1 mil to mm");
    }

    @Test
    public void testFeetToMmSuccess() {
    	// Tests conversion of feet to millimeters with a specific tolerance for accuracy
        assertEquals(304.8, UnitsConvertor.toMm(1, "ft"), 0.1, "1 ft to mm");
    }

    @Test
    public void testYardToMmSuccess() {
    	// Ensures that converting yards to millimeters yields the expected result, within a margin of error
        assertEquals(914.4, UnitsConvertor.toMm(1, "yd"), 0.1, "1 yd to mm");
    }

    @Test
    public void testMileToMmSuccess() {
    	// Checks that mile to millimeter conversion is correct, with a small tolerance for precision
        assertEquals(1609344.0, UnitsConvertor.toMm(1, "mi"), 0.1, "1 mi to mm");
    }

    @Test
    public void testMillimeterToMilSuccess() {
    	// Asserts that converting millimeters to mils is accurate, using a precise tolerance
        assertEquals(39.3701, UnitsConvertor.toMil(1, "mm"), 0.001, "1 mm to mil");
    }

  

    @Test
    public void testMeterToMilSuccess() {
    	// Verifies meter to mil conversion accuracy, allowing a tolerance for slight discrepancies
        assertEquals(39370.1, UnitsConvertor.toMil(1, "m"), 1, "1 m to mil");
    }

    @Test
    public void testKilometerToMilSuccess() {
    	// Confirms that converting 0 inches to millimeters results in 0, testing edge cases
        assertEquals(39370100, UnitsConvertor.toMil(1, "km"), 100, "1 km to mil");
    }

    @Test
    public void testLargeNumberConversionToMm() {
    	// Ensures that converting a large number of miles to millimeters is performed correctly
        assertEquals(1.609344E9, UnitsConvertor.toMm(1000, "mi"), 1000, "1000 mi to mm");
    }



    @Test
    public void testZeroConversionToMm() {
    	// Confirms that converting 0 inches to millimeters results in 0, testing edge cases
        assertEquals(0, UnitsConvertor.toMm(0, "in"), 0.001, "0 in to mm");
    }

    @Test
    public void testZeroConversionToMil() {
    	// Verifies zero conversion from kilometers to mils, ensuring no unexpected output
        assertEquals(0, UnitsConvertor.toMil(0, "km"), 0.001, "0 km to mil");
    }

  

    @Test
    public void testInvalidUnitConversionToMm() {
    	// Expects an IllegalArgumentException for unsupported unit conversions, ensuring robust error handling
        assertThrows(IllegalArgumentException.class, () -> UnitsConvertor.toMm(1, "lightyear"), "Invalid unit should throw an exception");
    }

    @Test
    public void testInvalidUnitConversionToMil() {
    	// Checks that attempting to convert an unsupported unit to mils throws an exception
        assertThrows(IllegalArgumentException.class, () -> UnitsConvertor.toMil(1, "parsecs"), "Invalid unit should throw an exception");
    }
    
    @Test
    public void testOneMeterToMil() {
        // Testing conversion of 1 meter to mil
        assertEquals(39370.1, UnitsConvertor.toMil(1, "m"), 0.1, "1 meter to mil");
    }

    @Test
    public void testTenCentimetersToMil() {
        // Adjusting test for 10 centimeters to mil using long input
        // 10 cm is 100 mm, so we convert 100 mm to mil
        assertEquals(3937.01, UnitsConvertor.toMil(100, "mm"), 0.01, "100 mm (10 cm) to mil");
    }

    @Test
    public void testOneInchToMm() {
        // Testing conversion of 1 inch to mm directly
        assertEquals(25.4, UnitsConvertor.toMm(1, "in"), 0.01, "1 inch to mm");
    }

    @Test
    public void testOneYardToMm() {
        // Testing conversion of 1 yard to mm
        assertEquals(914.4, UnitsConvertor.toMm(1, "yd"), 0.1, "1 yard to mm");
    }

    @Test
    public void testZeroValueConversionToMil() {
    	// Tests that converting zero kilometers to mil results in zero
        assertEquals(0, UnitsConvertor.toMil(0, "km"), "0 km to mil should be 0");
    }
    @Test
    public void testInchToMmPrecision() {
    	// Tests the precision of converting ten inches to millimeters
        double result = UnitsConvertor.toMm(10, "in");
        double expected = 254.0;
        assertEquals(expected, result, 0.01, "10 inches to mm with high precision");
    }
    @Test
    public void testInvalidConversionNotEquals() {
    	// Ensures that an invalid conversion does not result in zero
        double result = UnitsConvertor.toMil(100, "cm");
        assertNotEquals(0, result, "Conversion result should not be 0 for valid input");
    }
    @Test
    public void testComprehensiveMileConversion() {
    	// A comprehensive test that checks multiple conversions of one mile
        double mmConversion = UnitsConvertor.toMm(1, "mi");
        assertAll("1 mile comprehensive conversion",
            () -> assertEquals(1609344.0, mmConversion, 0.1, "Mile to mm"),
            () -> assertEquals(160934.4, mmConversion / 10, 0.1, "Mile to cm"),
            () -> assertEquals(1609.344, mmConversion / 1000, 0.1, "Mile to m"),
            () -> assertEquals(1.609344, mmConversion / 1000000, 0.1, "Mile to km")
        );
    }
    @Test
    public void testMainMethodWithMileToMetricConversion() {
        String input = "1 mile\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // Redirects System.in to simulate scanner input

        UnitsConvertor.main(new String[]{}); // Executes the main method

        // Verify that the output contains expected metric conversion results
        String expectedOutputMm = "1 mile is:";
        String expectedOutputKm = " km";
        assertTrue(outContent.toString().contains(expectedOutputMm), "Output should contain conversion results for 1 mile to mm");
        assertTrue(outContent.toString().contains(expectedOutputKm), "Output should contain conversion results for 1 mile to km");
    }
    @Test
    public void testMainMethodWithMeterToImperialConversion() {
        String input = "1 meter\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // Redirects System.in to simulate scanner input

        UnitsConvertor.main(new String[]{}); // Executes the main method

        // Verify that the output contains expected imperial conversion results
        String expectedOutputMil = "1 meter is:";
        String expectedOutputInch = " inch";
        String expectedOutputFt = " ft";
        String expectedOutputYard = " yard";
        String expectedOutputMile = " mile";
        String output = outContent.toString();
        
        assertTrue(output.contains(expectedOutputMil), "Output should contain conversion results for 1 meter to mil");
        assertTrue(output.contains(expectedOutputInch), "Output should contain conversion results for 1 meter to inch");
        assertTrue(output.contains(expectedOutputFt), "Output should contain conversion results for 1 meter to ft");
        assertTrue(output.contains(expectedOutputYard), "Output should contain conversion results for 1 meter to yard");
        assertTrue(output.contains(expectedOutputMile), "Output should contain conversion results for 1 meter to mile");
    }
    
   
}
